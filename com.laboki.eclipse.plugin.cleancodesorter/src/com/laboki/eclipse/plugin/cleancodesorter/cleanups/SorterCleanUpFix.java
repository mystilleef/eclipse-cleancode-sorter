package com.laboki.eclipse.plugin.cleancodesorter.cleanups;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.refactoring.CompilationUnitChange;
import org.eclipse.jdt.ui.cleanup.CleanUpContext;
import org.eclipse.jdt.ui.cleanup.ICleanUpFix;

import com.laboki.eclipse.plugin.cleancodesorter.contexts.CompilationUnitContext;
import com.laboki.eclipse.plugin.cleancodesorter.main.AstSorter;

public final class SorterCleanUpFix implements ICleanUpFix {

	private static final String NAME = "JavaSorterCleanUpFix";
	private final CompilationUnitContext context;

	public SorterCleanUpFix(final CleanUpContext context) {
		this.context =
			new CompilationUnitContext(context.getCompilationUnit(),
				context.getAST());
	}

	@Override
	public CompilationUnitChange
	createChange(final IProgressMonitor progressMonitor) throws CoreException {
		final CompilationUnitChange change = this.getCompilationChange();
		change.setEdit(new AstSorter(this.context).getTextEdit());
		return change;
	}

	private CompilationUnitChange
	getCompilationChange() {
		return new CompilationUnitChange(SorterCleanUpFix.NAME,
			this.context.getInterface());
	}
}
