package com.laboki.eclipse.plugin.cleancodesorter.events;

import org.eclipse.jdt.core.dom.CompilationUnit;

import com.google.common.base.Optional;

public final class AstNodeEvent {

	private final Optional<CompilationUnit> node;

	public AstNodeEvent(final Optional<CompilationUnit> node) {
		this.node = node;
	}

	public Optional<CompilationUnit>
	getNode() {
		return this.node;
	}
}
