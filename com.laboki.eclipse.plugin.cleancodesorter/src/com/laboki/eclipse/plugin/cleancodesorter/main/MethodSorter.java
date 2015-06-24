package com.laboki.eclipse.plugin.cleancodesorter.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Modifier;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.CompilationUnitContext;
import com.laboki.eclipse.plugin.cleancodesorter.visitors.MethodInvocationVisitor;

public final class MethodSorter {

	private final CompilationUnitContext context;
	private final ImmutableList<MethodDeclaration> declarations;
	private static final MethodInvocationVisitor VISITOR =
		new MethodInvocationVisitor();

	public MethodSorter(final CompilationUnitContext context) {
		this.context = context;
		this.declarations = new MethodDeclarations(context).get();
	}

	public ImmutableList<MethodDeclaration>
	get() {
		final List<MethodDeclaration> nodes = Lists.newArrayList();
		this.buildSortedMethodNodes(this.getDefaultSortedMethodNodes(), nodes);
		// MethodSorter.printSortedMethods(nodes);
		return ImmutableList.copyOf(nodes);
	}

	private void
	buildSortedMethodNodes(	final List<MethodDeclaration> oldNodes,
													final List<MethodDeclaration> newNodes) {
		while (!oldNodes.isEmpty())
			this.sortNodes(oldNodes, newNodes);
	}

	private void
	sortNodes(final List<MethodDeclaration> oldNodes,
								final List<MethodDeclaration> newNodes) {
		final MethodDeclaration node = oldNodes.get(0);
		MethodSorter.updateNodes(oldNodes, newNodes, node);
		this.updateOldNodesWithNewMethodInvocations(oldNodes, node);
	}

	private static void
	updateNodes(final List<MethodDeclaration> oldNodes,
							final List<MethodDeclaration> newNodes,
							final MethodDeclaration node) {
		oldNodes.remove(node);
		newNodes.remove(node);
		newNodes.add(node);
	}

	private void
	updateOldNodesWithNewMethodInvocations(	final List<MethodDeclaration> oldNodes,
																					final MethodDeclaration declaration) {
		final List<MethodDeclaration> invocationDeclarations =
			this.getInvocationDeclarations(oldNodes, MethodSorter.getMethodInvocations(declaration));
		invocationDeclarations.remove(declaration);
		oldNodes.addAll(0, invocationDeclarations);
	}

	private List<MethodDeclaration>
	getInvocationDeclarations(final List<MethodDeclaration> oldNodes,
														final ImmutableList<MethodInvocation> methodInvocations) {
		final ArrayList<MethodDeclaration> invocationList = Lists.newArrayList();
		for (final MethodInvocation methodInvocation : methodInvocations)
			this.updateInvocationList(invocationList, methodInvocation);
		return invocationList;
	}

	private void
	updateInvocationList(	final ArrayList<MethodDeclaration> invocationList,
												final MethodInvocation methodInvocation) {
		for (final MethodDeclaration methodDeclaration : this.declarations)
			if (methodInvocation.resolveMethodBinding()
				.isEqualTo(methodDeclaration.resolveBinding())) invocationList.add(methodDeclaration);
	}

	private static ImmutableList<MethodInvocation>
	getMethodInvocations(final MethodDeclaration declaration) {
		MethodSorter.VISITOR.clear();
		declaration.accept(MethodSorter.VISITOR);
		return MethodSorter.VISITOR.getInvocations();
	}

	private List<MethodDeclaration>
	getDefaultSortedMethodNodes() {
		final List<MethodDeclaration> nodes = Lists.newArrayList();
		nodes.addAll(this.filterConstructors());
		nodes.addAll(this.filterMethods(Modifier.PUBLIC));
		nodes.addAll(this.filterPackageMethods());
		nodes.addAll(this.filterMethods(Modifier.PROTECTED));
		nodes.addAll(this.filterMethods(Modifier.PRIVATE));
		nodes.removeAll(new MethodInvocations(this.context).get());
		return nodes;
	}

	private List<MethodDeclaration>
	filterConstructors() {
		final List<MethodDeclaration> constructors = Lists.newArrayList();
		for (final MethodDeclaration declaration : this.declarations)
			if (declaration.isConstructor()) constructors.add(declaration);
		Collections.sort(constructors, MethodSorter.sortByParameter());
		return constructors;
	}

	private List<MethodDeclaration>
	filterPackageMethods() {
		final List<MethodDeclaration> methods = Lists.newArrayList();
		for (final MethodDeclaration declaration : this.declarations)
			MethodSorter.updateDeclarationMethods(methods, declaration);
		MethodSorter.defaultSortOrder(methods);
		return methods;
	}

	private static void
	updateDeclarationMethods(	final List<MethodDeclaration> methods,
						final MethodDeclaration declaration) {
		if (MethodSorter.isPackageMethod(declaration)) methods.add(declaration);
	}

	private static boolean
	isPackageMethod(final MethodDeclaration declaration) {
		final int modifiers = declaration.resolveBinding().getModifiers();
		return !Modifier.isPrivate(modifiers)
			&& !Modifier.isPublic(modifiers)
			&& !Modifier.isProtected(modifiers);
	}

	private List<MethodDeclaration>
	filterMethods(final int modifier) {
		final List<MethodDeclaration> methods = Lists.newArrayList();
		for (final MethodDeclaration declaration : this.declarations)
			MethodSorter.updateMethodList(modifier, methods, declaration);
		MethodSorter.defaultSortOrder(methods);
		return methods;
	}

	private static void
	updateMethodList(	final int modifier,
										final List<MethodDeclaration> methods,
										final MethodDeclaration declaration) {
		if (declaration.isConstructor()) return;
		if ((declaration.resolveBinding().getModifiers() & modifier) != 0) methods.add(declaration);
	}

	private static void
	defaultSortOrder(final List<MethodDeclaration> methods) {
		Collections.sort(methods, MethodSorter.sortByParameter());
		Collections.sort(methods, MethodSorter.sortByName());
		Collections.sort(methods, MethodSorter.sortByReturnType());
	}

	private static Comparator<MethodDeclaration>
	sortByParameter() {
		return (o1, o2) -> {
			final int no1 = o1.resolveBinding().getParameterTypes().length;
			final int no2 = o2.resolveBinding().getParameterTypes().length;
			return no1 - no2;
		};
	}

	private static Comparator<MethodDeclaration>
	sortByName() {
		return (o1, o2) -> {
			final String name1 = o1.getName().getFullyQualifiedName();
			final String name2 = o2.getName().getFullyQualifiedName();
			return name1.compareTo(name2);
		};
	}

	private static Comparator<MethodDeclaration>
	sortByReturnType() {
		return (o1, o2) -> {
			final String name1 =
				o1.resolveBinding().getReturnType().getQualifiedName();
			final String name2 =
				o2.resolveBinding().getReturnType().getQualifiedName();
			return name1.compareTo(name2);
		};
	}

	@SuppressWarnings("unused")
	private List<MethodDeclaration>
	sortMethods() {
		final List<MethodDeclaration> methods = Lists.newArrayList();
		for (final MethodDeclaration declaration : this.declarations)
			if (!declaration.isConstructor()) methods.add(declaration);
		return methods;
	}

	@SuppressWarnings("unused")
	private static void
	printSortedMethods(final List<MethodDeclaration> nodes) {
		System.out.println("=====");
		System.out.println("Sorted: ");
		System.out.println("---");
		for (final MethodDeclaration node : nodes)
			System.out.println(node.getName());
		System.out.println("=====");
	}
}
