package com.laboki.eclipse.plugin.cleancodesorter.events;

import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;

public final class UpdateBufferContentEvent {

	private final ASTRewrite rewriter;

	public UpdateBufferContentEvent(final ASTRewrite rewriter) {
		this.rewriter = rewriter;
	}

	public ASTRewrite
	getRewriter() {
		return this.rewriter;
	}
}
