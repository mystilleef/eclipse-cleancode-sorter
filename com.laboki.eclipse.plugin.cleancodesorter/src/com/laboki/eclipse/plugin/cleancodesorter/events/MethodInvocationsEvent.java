package com.laboki.eclipse.plugin.cleancodesorter.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.ImmutableList;

public final class MethodInvocationsEvent {

	private final ImmutableList<MethodDeclaration> invocations;
	private final ImmutableList<MethodDeclaration> declarations;

	public MethodInvocationsEvent(
		final ImmutableList<MethodDeclaration> declarations,
		final ImmutableList<MethodDeclaration> invocations) {
		this.declarations = declarations;
		this.invocations = invocations;
	}

	public ImmutableList<MethodDeclaration>
	getInvocations() {
		return this.invocations;
	}

	public ImmutableList<MethodDeclaration>
	getDeclarations() {
		return this.declarations;
	}
}
