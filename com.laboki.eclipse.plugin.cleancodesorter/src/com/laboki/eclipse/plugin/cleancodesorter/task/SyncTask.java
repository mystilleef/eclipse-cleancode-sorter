package com.laboki.eclipse.plugin.cleancodesorter.task;

import com.laboki.eclipse.plugin.cleancodesorter.contexts.EditorContext;

public abstract class SyncTask extends Task {

	@Override
	protected TaskJob
	newTaskJob() {
		return new TaskJob() {

			@Override
			protected void
			runTask() {
				EditorContext.syncExec(() -> SyncTask.this.execute());
			}
		};
	}
}
