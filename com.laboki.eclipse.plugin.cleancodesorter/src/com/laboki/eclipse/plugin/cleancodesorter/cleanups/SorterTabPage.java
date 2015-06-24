package com.laboki.eclipse.plugin.cleancodesorter.cleanups;

import org.eclipse.jdt.ui.cleanup.CleanUpOptions;
import org.eclipse.jdt.ui.cleanup.ICleanUpConfigurationUI;
import org.eclipse.swt.widgets.Composite;

public final class SorterTabPage implements ICleanUpConfigurationUI {

	public SorterTabPage() {}

	@Override
	public int
	getCleanUpCount() {
		return 0;
	}

	@Override
	public int
	getSelectedCleanUpCount() {
		return 0;
	}

	@Override
	public String
	getPreview() {
		return null;
	}

	@Override
	public Composite
	createContents(final Composite parent) {
		return null;
	}

	@Override
	public void
	setOptions(final CleanUpOptions options) {}
}
