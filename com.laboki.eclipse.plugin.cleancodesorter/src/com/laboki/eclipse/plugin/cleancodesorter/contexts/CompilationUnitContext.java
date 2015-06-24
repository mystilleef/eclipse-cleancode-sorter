package com.laboki.eclipse.plugin.cleancodesorter.contexts;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.CompilationUnit;

public final class CompilationUnitContext {

	private final ICompilationUnit unit;
	private final CompilationUnit node;

	public CompilationUnitContext(
		final ICompilationUnit compilationUnit,
		final CompilationUnit ast) {
		this.unit = compilationUnit;
		this.node = ast;
	}

	public ICompilationUnit
	getInterface() {
		return this.unit;
	}

	public CompilationUnit
	getNode() {
		return this.node;
	}
}
