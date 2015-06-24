package com.laboki.eclipse.plugin.cleancodesorter.main;

import java.util.Map;

import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

import com.google.common.collect.ImmutableList;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.CompilationUnitContext;

public final class AstSorter {

	private final CompilationUnitContext context;

	public AstSorter(final CompilationUnitContext context) {
		this.context = context;
	}

	public TextEdit
	getTextEdit() throws JavaModelException {
		return this.getAstRewrite().rewriteAST(this.getDocument(),
			this.getOptions());
	}

	public ASTRewrite
	getAstRewrite() {
		return new AstUpdater(this.context, this.getSortedAstNodes()).get();
	}

	private ImmutableList<MethodDeclaration>
	getSortedAstNodes() {
		return new MethodSorter(this.context).get();
	}

	private Document
	getDocument() throws JavaModelException {
		return new Document(this.context.getInterface().getSource());
	}

	private Map<String, String>
	getOptions() {
		return this.context.getInterface().getJavaProject().getOptions(true);
	}
}
