package com.laboki.eclipse.plugin.cleancodesorter.cleanups;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.cleanup.CleanUpContext;
import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.CleanUpRequirements;
import org.eclipse.jdt.ui.cleanup.ICleanUp;
import org.eclipse.jdt.ui.cleanup.ICleanUpFix;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;

public final class SorterCleanUp implements ICleanUp {

	private CleanUpOptions options;

	public SorterCleanUp() {}

	@Override
	public String[]
	getStepDescriptions() {
		return null;
	}

	@Override
	public CleanUpRequirements
	getRequirements() {
		return new CleanUpRequirements(true, true, true, null);
	}

	@Override
	public ICleanUpFix
	createFix(final CleanUpContext context) throws CoreException {
		if (context.getAST() == null) return null;
		if (context.getCompilationUnit() == null) return null;
		// return new SorterCleanUpFix(context);
		return null;
	}

	@Override
	public RefactoringStatus
	checkPostConditions(final IProgressMonitor monitor) throws CoreException {
		return new RefactoringStatus();
	}

	@Override
	public RefactoringStatus
	checkPreConditions(	final IJavaProject project,
											final ICompilationUnit[] compilationUnits,
											final IProgressMonitor monitor) throws CoreException {
		return new RefactoringStatus();
	}

	@Override
	public void
	setOptions(final CleanUpOptions options) {
		Assert.isLegal(options != null);
		Assert.isTrue(this.options == null);
		this.options = options;
	}
}
