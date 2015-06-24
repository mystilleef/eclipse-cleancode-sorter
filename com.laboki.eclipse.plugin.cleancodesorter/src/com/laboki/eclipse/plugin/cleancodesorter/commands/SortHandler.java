package com.laboki.eclipse.plugin.cleancodesorter.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.jobs.Job;

import com.laboki.eclipse.plugin.cleancodesorter.contexts.EditorContext;
import com.laboki.eclipse.plugin.cleancodesorter.task.Task;
import com.laboki.eclipse.plugin.cleancodesorter.task.TaskMutexRule;

public final class SortHandler extends AbstractHandler {

	private static final TaskMutexRule RULE = new TaskMutexRule();

	@Override
	public Object
	execute(final ExecutionEvent event) throws ExecutionException {
		new Task() {

			@Override
			public void
			execute() {
				EditorContext.sort();
			}
		}.setRule(SortHandler.RULE).setPriority(Job.INTERACTIVE).start();
		return null;
	}
}
