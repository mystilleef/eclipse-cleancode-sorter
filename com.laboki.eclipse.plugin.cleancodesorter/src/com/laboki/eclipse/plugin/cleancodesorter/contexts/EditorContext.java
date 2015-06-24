package com.laboki.eclipse.plugin.cleancodesorter.contexts;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.google.common.base.Optional;
import com.laboki.eclipse.plugin.cleancodesorter.Activator;
import com.laboki.eclipse.plugin.cleancodesorter.events.SortEvent;
import com.laboki.eclipse.plugin.cleancodesorter.main.EventBus;
import com.laboki.eclipse.plugin.cleancodesorter.main.Sorter;
import com.laboki.eclipse.plugin.cleancodesorter.task.BaseTask;

public enum EditorContext {
	INSTANCE;

	public static final String PLUGIN_ID = Activator.PLUGIN_ID;
	public static final IWorkbench WORKBENCH = PlatformUI.getWorkbench();
	public static final Display DISPLAY = EditorContext.WORKBENCH.getDisplay();
	public static final IJobManager JOB_MANAGER = Job.getJobManager();


	public static boolean
	isJdtPart(final IWorkbenchPart part) {
		if (!EditorContext.isEditorPart(part)) return false;
		final Optional<IFile> file =
			EditorContext.getFile(Optional.fromNullable((IEditorPart) part));
		if (!file.isPresent()) return false;
		return JavaCore.isJavaLikeFileName(file.get().getName());
	}

	public static boolean
	isEditorPart(final IWorkbenchPart part) {
		return part instanceof IEditorPart;
	}

	public static Optional<IFile>
	getFile(final Optional<IEditorPart> editor) {
		return FileContext.getFile(editor);
	}

	public static Optional<IEditorPart>
	getEditor() {
		final Optional<IWorkbenchWindow> window =
			EditorContext.getActiveWorkbenchWindow();
		if (!window.isPresent()) return Optional.absent();
		final Optional<IWorkbenchPage> page = EditorContext.getActivePage(window);
		if (!page.isPresent()) return Optional.absent();
		return Optional.fromNullable(page.get().getActiveEditor());
	}

	public static Optional<IWorkbenchPage>
	getActivePage(final Optional<IWorkbenchWindow> activeWorkbenchWindow) {
		return Optional.fromNullable(activeWorkbenchWindow.get().getActivePage());
	}

	public static Optional<IPartService>
	getPartService() {
		final Optional<IWorkbenchWindow> window =
			EditorContext.getActiveWorkbenchWindow();
		if (!window.isPresent()) return Optional.absent();
		return Optional.fromNullable((IPartService) window.get()
			.getService(IPartService.class));
	}

	public static Optional<IWorkbenchWindow>
	getActiveWorkbenchWindow() {
		return Optional.fromNullable(EditorContext.WORKBENCH.getActiveWorkbenchWindow());
	}

	public static void
	asyncExec(final Runnable runnable) {
		if (EditorContext.displayIsDisposed()) return;
		EditorContext.DISPLAY.asyncExec(runnable);
	}

	public static void
	cancelEventTasks() {
		EditorContext.JOB_MANAGER.cancel(EventBus.FAMILY);
	}

	public static void
	cancelPluginTasks() {
		EditorContext.JOB_MANAGER.cancel(BaseTask.FAMILY);
		EditorContext.JOB_MANAGER.cancel(Sorter.FAMILY);
	}

	public static void
	sort() {
		EventBus.post(new SortEvent());
	}

	public static void
	syncExec(final Runnable runnable) {
		if (EditorContext.displayIsDisposed()) return;
		EditorContext.DISPLAY.syncExec(runnable);
	}

	private static boolean
	displayIsDisposed() {
		if (EditorContext.DISPLAY == null) return true;
		return EditorContext.DISPLAY.isDisposed();
	}
}
