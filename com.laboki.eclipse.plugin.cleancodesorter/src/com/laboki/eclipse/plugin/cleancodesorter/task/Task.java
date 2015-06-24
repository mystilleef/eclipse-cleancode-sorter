package com.laboki.eclipse.plugin.cleancodesorter.task;

public abstract class Task extends BaseTask {

	@Override
	protected TaskJob
	newTaskJob() {
		return new TaskJob() {

			@Override
			protected void
			runTask() {
				Task.this.execute();
			}
		};
	}

	@Override
	public abstract void
	execute();
}
