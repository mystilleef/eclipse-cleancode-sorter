package com.laboki.eclipse.plugin.cleancodesorter.events;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

public final class TypeDeclarationsEvent {

	private final ImmutableList<TypeDeclaration> declarations;
	private final Optional<CompilationUnit> astNode;

	public TypeDeclarationsEvent(
		final ImmutableList<TypeDeclaration> declarations,
		final Optional<CompilationUnit> astNode) {
		this.declarations = declarations;
		this.astNode = astNode;
	}

	public ImmutableList<TypeDeclaration>
	getDeclarations() {
		return this.declarations;
	}

	public Optional<CompilationUnit>
	getAstNode() {
		return this.astNode;
	}
}
