package com.laboki.eclipse.plugin.cleancodesorter.contexts;

import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ChildListPropertyDescriptor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.StructuralPropertyDescriptor;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jdt.ui.cleanup.CleanUpContext;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import com.google.common.collect.ImmutableList;
import com.laboki.eclipse.plugin.cleancodesorter.main.TypeDeclarationsContext;

public enum CUContext {
	INSTANCE;

	private static final TypeDeclarationsContext TYPE_DECLARATIONS =
		new TypeDeclarationsContext();;

	public static ASTRewrite
	fixAst(	final CleanUpContext context,
					final ImmutableList<MethodDeclaration> declarations,
					final ImmutableList<MethodDeclaration> mDeclarations) {
		final ASTRewrite rewriter =
			CUContext.getAstRewriter(context.getAST().getAST());
		CUContext.sortMethodNodes(rewriter, mDeclarations);
		CUContext.sortTypeNodes(rewriter, context.getAST());
		return rewriter;
	}

	public static ASTRewrite
	getAstRewriter(final AST ast) {
		return ASTRewrite.create(ast);
	}

	private static void
	sortMethodNodes(final ASTRewrite rewriter,
									final ImmutableList<MethodDeclaration> sortedMethods) {
		for (final MethodDeclaration method : sortedMethods)
			CUContext.updateAst(method, rewriter);
	}

	private static void
	updateAst(final MethodDeclaration node, final ASTRewrite rewriter) {
		final ASTNode moveNode = rewriter.createMoveTarget(node);
		CUContext.getListRewriter(node, rewriter)
			.insertLast(moveNode, null);
	}

	private static ListRewrite
	getListRewriter(final MethodDeclaration node, final ASTRewrite rewriter) {
		final ASTNode parent = node.getParent();
		return rewriter.getListRewrite(parent,
			CUContext.getChildListProperties(parent.structuralPropertiesForType(),
				"bodyDeclarations"));
	}

	private static ChildListPropertyDescriptor
	getChildListProperties(	final List<StructuralPropertyDescriptor> properties,
													final String id) {
		for (final StructuralPropertyDescriptor descriptor : properties)
			if (CUContext.isValidDescriptor(descriptor, id)) return (ChildListPropertyDescriptor) descriptor;
		return null;
	}

	private static boolean
	isValidDescriptor(final StructuralPropertyDescriptor descriptor,
										final String id) {
		return descriptor.isChildListProperty() && descriptor.getId().equals(id);
	}

	private static void
	sortTypeNodes(final ASTRewrite rewriter, final CompilationUnit node) {
		for (final TypeDeclaration declaration : CUContext.TYPE_DECLARATIONS.get(node))
			CUContext.updateAst(declaration, rewriter);
	}

	private static void
	updateAst(final TypeDeclaration node, final ASTRewrite rewriter) {
		if (!node.isLocalTypeDeclaration()) return;
		final ASTNode moveNode = rewriter.createMoveTarget(node);
		CUContext.getListRewriter(node, rewriter).insertLast(moveNode, null);
	}

	private static ListRewrite
	getListRewriter(final TypeDeclaration node, final ASTRewrite rewriter) {
		final ASTNode parent = node.getParent();
		return rewriter.getListRewrite(parent,
			TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
	}

	public static TextEdit
	getTextEdit(final ASTRewrite rewriter, final ICompilationUnit cu)
		throws JavaModelException {
		return rewriter.rewriteAST(new Document(cu.getSource()),
			cu.getJavaProject().getOptions(true));
	}
}
