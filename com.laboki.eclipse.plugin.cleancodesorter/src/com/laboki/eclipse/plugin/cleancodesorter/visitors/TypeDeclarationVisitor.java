package com.laboki.eclipse.plugin.cleancodesorter.visitors;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class TypeDeclarationVisitor extends ASTVisitor {

	private final List<TypeDeclaration> declarations = Lists.newArrayList();

	public TypeDeclarationVisitor() {}

	public TypeDeclarationVisitor(final boolean visitDocTags) {
		super(visitDocTags);
	}

	@Override
	public boolean
	visit(final TypeDeclaration node) {
		this.declarations.add(node);
		return true;
	}

	public ImmutableList<TypeDeclaration>
	getDeclarations() {
		return ImmutableList.copyOf(this.declarations);
	}

	public void
	clear() {
		this.declarations.clear();
	}
}
