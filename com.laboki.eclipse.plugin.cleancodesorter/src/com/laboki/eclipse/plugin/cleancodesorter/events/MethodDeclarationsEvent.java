package com.laboki.eclipse.plugin.cleancodesorter.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.ImmutableList;

public final class MethodDeclarationsEvent {

	private final ImmutableList<MethodDeclaration> declarations;

	public MethodDeclarationsEvent(
		final ImmutableList<MethodDeclaration> declarations) {
		this.declarations = declarations;
	}

	public ImmutableList<MethodDeclaration>
	getDeclarations() {
		return this.declarations;
	}
}
