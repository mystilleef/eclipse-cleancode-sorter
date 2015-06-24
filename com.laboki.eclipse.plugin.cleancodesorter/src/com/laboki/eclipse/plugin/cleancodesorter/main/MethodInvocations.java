package com.laboki.eclipse.plugin.cleancodesorter.main;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.CompilationUnitContext;
import com.laboki.eclipse.plugin.cleancodesorter.visitors.MethodInvocationVisitor;

public final class MethodInvocations {

	private final CompilationUnitContext context;
	private final MethodInvocationVisitor visitor =
		new MethodInvocationVisitor();

	public MethodInvocations(final CompilationUnitContext context) {
		this.context = context;
	}

	public ImmutableList<MethodDeclaration>
	get() {
		final List<MethodDeclaration> filteredInvocations = Lists.newArrayList();
		this.updateFilteredInvocations(filteredInvocations, this.getInvocationNodes());
		return ImmutableList.copyOf(filteredInvocations);
	}

	private void
	updateFilteredInvocations(final List<MethodDeclaration> filteredInvocations,
														final ImmutableList<MethodInvocation> invocations) {
		for (final MethodDeclaration declaration : new MethodDeclarations(this.context).get())
			MethodInvocations.addMatchedInvocations(invocations,
				filteredInvocations,
				declaration);
	}

	private static void
	addMatchedInvocations(final List<MethodInvocation> invocations,
												final List<MethodDeclaration> filteredInvocations,
												final MethodDeclaration declaration) {
		for (final MethodInvocation invocation : invocations)
			if (MethodInvocations.isSame(declaration, invocation)) filteredInvocations.add(declaration);
	}

	private static boolean
	isSame(final MethodDeclaration declaration, final MethodInvocation invocation) {
		return invocation.resolveMethodBinding()
			.isEqualTo(declaration.resolveBinding());
	}

	private ImmutableList<MethodInvocation>
	getInvocationNodes() {
		this.visitor.clear();
		this.context.getNode().accept(this.visitor);
		return this.visitor.getInvocations();
	}
}
