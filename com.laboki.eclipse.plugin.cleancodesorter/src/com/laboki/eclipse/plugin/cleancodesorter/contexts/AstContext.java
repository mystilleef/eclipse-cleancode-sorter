package com.laboki.eclipse.plugin.cleancodesorter.contexts;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.ui.IEditorPart;

import com.google.common.base.Optional;

public enum AstContext {
	INSTANCE;

	public static Optional<CompilationUnit>
	getAstNode(final Optional<IEditorPart> editor) {
		final Optional<ICompilationUnit> unit =
			AstContext.getCompilationUnit(editor);
		if (!unit.isPresent()) return Optional.absent();
		final ASTParser parser = AstContext.createAstParser(unit);
		return Optional.fromNullable((CompilationUnit) parser.createAST(null));
	}

	public static Optional<ICompilationUnit>
	getCompilationUnit(final Optional<IEditorPart> editor) {
		final Optional<IFile> file = EditorContext.getFile(editor);
		if (!file.isPresent()) return Optional.absent();
		return Optional.fromNullable(JavaCore.createCompilationUnitFrom(file.get()));
	}

	private static ASTParser
	createAstParser(final Optional<ICompilationUnit> unit) {
		final ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(unit.get());
		parser.setResolveBindings(true);
		return parser;
	}
}
