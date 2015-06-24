package com.laboki.eclipse.plugin.cleancodesorter.main;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;

import com.google.common.base.Optional;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.EditorContext;
import com.laboki.eclipse.plugin.cleancodesorter.instance.Instance;

public final class PartMonitor implements Instance, IPartListener {

	private final Instance partServices = new PartServices();
	private static final Optional<IPartService> SERVICE =
		EditorContext.getPartService();

	public PartMonitor() {
		this.initPartServices();
	}

	private void
	initPartServices() {
		final Optional<IEditorPart> editor = EditorContext.getEditor();
		if (editor.isPresent()) this.startPartServices(editor.get());
	}

	@Override
	public Instance
	start() {
		if (!PartMonitor.SERVICE.isPresent()) return this;
		PartMonitor.SERVICE.get().addPartListener(this);
		return this;
	}

	@Override
	public Instance
	stop() {
		if (!PartMonitor.SERVICE.isPresent()) return this;
		PartMonitor.SERVICE.get().removePartListener(this);
		return this;
	}

	@Override
	public void
	partActivated(final IWorkbenchPart part) {
		this.startPartServices(part);
	}

	private void
	startPartServices(final IWorkbenchPart part) {
		if (EditorContext.isJdtPart(part)) this.partServices.start();
	}

	@Override
	public void
	partBroughtToTop(final IWorkbenchPart part) {}

	@Override
	public void
	partClosed(final IWorkbenchPart part) {}

	@Override
	public void
	partDeactivated(final IWorkbenchPart part) {
		this.stopPartServices(part);
	}

	private void
	stopPartServices(final IWorkbenchPart part) {
		if (EditorContext.isJdtPart(part)) this.partServices.stop();
	}

	@Override
	public void
	partOpened(final IWorkbenchPart part) {}
}
