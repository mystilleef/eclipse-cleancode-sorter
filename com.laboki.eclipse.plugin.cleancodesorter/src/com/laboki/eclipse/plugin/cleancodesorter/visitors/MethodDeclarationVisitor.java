package com.laboki.eclipse.plugin.cleancodesorter.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class MethodDeclarationVisitor extends ASTVisitor {

	private final List<MethodDeclaration> declarations = Lists.newArrayList();

	public MethodDeclarationVisitor() {}

	public MethodDeclarationVisitor(final boolean visitDocTags) {
		super(visitDocTags);
	}

	@Override
	public boolean
	visit(final MethodDeclaration node) {
		this.declarations.add(node);
		return true;
	}

	public ImmutableList<MethodDeclaration>
	getDeclarations() {
		return ImmutableList.copyOf(this.declarations);
	}

	public void
	clear() {
		this.declarations.clear();
	}
}
