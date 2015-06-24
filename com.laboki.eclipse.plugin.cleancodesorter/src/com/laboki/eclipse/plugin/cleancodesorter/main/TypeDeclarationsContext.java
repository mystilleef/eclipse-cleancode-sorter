package com.laboki.eclipse.plugin.cleancodesorter.main;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.google.common.collect.ImmutableList;
import com.laboki.eclipse.plugin.cleancodesorter.visitors.TypeDeclarationVisitor;

public class TypeDeclarationsContext {

	private final TypeDeclarationVisitor visitor =
		new TypeDeclarationVisitor();

	public ImmutableList<TypeDeclaration>
	get(final CompilationUnit node) {
		return this.getTypeDeclarations(node);
	}

	public ImmutableList<TypeDeclaration>
	getTypeDeclarations(final CompilationUnit node) {
		this.visitor.clear();
		node.accept(this.visitor);
		return this.visitor.getDeclarations();
	}
}
