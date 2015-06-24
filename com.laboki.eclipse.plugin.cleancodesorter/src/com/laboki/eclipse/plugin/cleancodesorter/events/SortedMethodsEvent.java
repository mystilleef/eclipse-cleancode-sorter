package com.laboki.eclipse.plugin.cleancodesorter.events;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.ImmutableList;

public final class SortedMethodsEvent {

	private final ImmutableList<MethodDeclaration> sortedMethods;

	public SortedMethodsEvent(
		final ImmutableList<MethodDeclaration> sortedMethods) {
		this.sortedMethods = sortedMethods;
	}

	public ImmutableList<MethodDeclaration>
	getSortedMethods() {
		return this.sortedMethods;
	}
}
