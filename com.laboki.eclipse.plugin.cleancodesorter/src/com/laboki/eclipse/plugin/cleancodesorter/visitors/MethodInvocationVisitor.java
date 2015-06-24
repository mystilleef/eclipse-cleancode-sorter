package com.laboki.eclipse.plugin.cleancodesorter.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class MethodInvocationVisitor extends ASTVisitor {

	private final List<MethodInvocation> invocations = Lists.newArrayList();

	public MethodInvocationVisitor() {}

	public MethodInvocationVisitor(final boolean visitDocTags) {
		super(visitDocTags);
	}

	@Override
	public boolean
	visit(final MethodInvocation node) {
		this.invocations.add(node);
		return super.visit(node);
	}

	public ImmutableList<MethodInvocation>
	getInvocations() {
		return ImmutableList.copyOf(this.invocations);
	}

	public void
	clear() {
		this.invocations.clear();
	}
}
