package com.laboki.eclipse.plugin.cleancodesorter.main;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;

import com.google.common.collect.ImmutableList;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.CompilationUnitContext;

public final class AstUpdater {

	private final ImmutableList<MethodDeclaration> sortedMethods;
	private final CompilationUnitContext context;
	private final TypeDeclarationsContext typeDeclarations =
		new TypeDeclarationsContext();

	public AstUpdater(
		final CompilationUnitContext context,
		final ImmutableList<MethodDeclaration> sortedMethods) {
		this.context = context;
		this.sortedMethods = sortedMethods;
	}

	public ASTRewrite
	get() {
		final ASTRewrite rewriter = this.getAstRewriter();
		this.sortMethodNodes(rewriter);
		this.sortTypeNodes(rewriter);
		return rewriter;
	}

	private ASTRewrite
	getAstRewriter() {
		return ASTRewrite.create(this.context.getNode().getAST());
	}

	private void
	sortMethodNodes(final ASTRewrite rewriter) {
		for (final MethodDeclaration method : this.sortedMethods)
			AstUpdater.updateAst(method, rewriter);
	}

	private static void
	updateAst(final MethodDeclaration node, final ASTRewrite rewriter) {
		final ASTNode moveNode = rewriter.createMoveTarget(node);
		AstUpdater.getListRewriter(node, rewriter).insertLast(moveNode, null);
	}

	private static ListRewrite
	getListRewriter(final MethodDeclaration node, final ASTRewrite rewriter) {
		final ASTNode parent = node.getParent();
		return rewriter.getListRewrite(parent,
			AstUpdater.getChildListProperties(parent.structuralPropertiesForType(),
				"bodyDeclarations"));
	}

	private static ChildListPropertyDescriptor
	getChildListProperties(	final List<StructuralPropertyDescriptor> properties,
													final String id) {
		for (final StructuralPropertyDescriptor descriptor : properties)
			if (AstUpdater.isValidDescriptor(descriptor, id)) return (ChildListPropertyDescriptor) descriptor;
		return null;
	}

	private static boolean
	isValidDescriptor(final StructuralPropertyDescriptor descriptor,
										final String id) {
		return descriptor.isChildListProperty() && descriptor.getId().equals(id);
	}

	private void
	sortTypeNodes(final ASTRewrite rewriter) {
		for (final TypeDeclaration node : this.typeDeclarations.get(this.context.getNode()))
			this.updateAst(node, rewriter);
	}

	private void
	updateAst(final TypeDeclaration node, final ASTRewrite rewriter) {
		if (this.typeDeclarations.get(this.context.getNode()).indexOf(node) == 0) return;
		final ASTNode moveNode = rewriter.createMoveTarget(node);
		AstUpdater.getListRewriter(node, rewriter).insertLast(moveNode, null);
	}

	private static ListRewrite
	getListRewriter(final TypeDeclaration node, final ASTRewrite rewriter) {
		final ASTNode parent = node.getParent();
		return rewriter.getListRewrite(parent,
			TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
	}
}
