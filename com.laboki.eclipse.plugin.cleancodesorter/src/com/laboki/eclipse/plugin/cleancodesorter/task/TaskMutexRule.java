package com.laboki.eclipse.plugin.cleancodesorter.task;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

public class TaskMutexRule implements ISchedulingRule {

	@Override
	public boolean
	isConflicting(final ISchedulingRule rule) {
		return rule == this;
	}

	@Override
	public boolean
	contains(final ISchedulingRule rule) {
		return rule == this;
	}
}
