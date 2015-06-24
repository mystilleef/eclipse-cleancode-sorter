package com.laboki.eclipse.plugin.cleancodesorter.main;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.ImmutableList;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.CompilationUnitContext;
import com.laboki.eclipse.plugin.cleancodesorter.visitors.MethodDeclarationVisitor;

public final class MethodDeclarations {

	private final CompilationUnitContext context;
	private final MethodDeclarationVisitor visitor =
		new MethodDeclarationVisitor();

	public MethodDeclarations(final CompilationUnitContext context) {
		this.context = context;
	}

	public ImmutableList<MethodDeclaration>
	get() {
		this.visitor.clear();
		this.context.getNode().accept(this.visitor);
		return this.visitor.getDeclarations();
	}
}
