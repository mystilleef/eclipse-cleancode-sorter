package com.laboki.eclipse.plugin.cleancodesorter;

import org.eclipse.core.runtime.jobs.Job;

import com.laboki.eclipse.plugin.cleancodesorter.instance.Instance;
import com.laboki.eclipse.plugin.cleancodesorter.main.Services;
import com.laboki.eclipse.plugin.cleancodesorter.task.AsyncTask;

public enum Plugin implements Instance {
	INSTANCE;

	protected static final Services SERVICES = new Services();

	@Override
	public Instance
	start() {
		new AsyncTask() {

			@Override
			public void
			execute() {
				Plugin.SERVICES.start();
			}
		}.setPriority(Job.INTERACTIVE).start();
		return this;
	}

	@Override
	public Instance
	stop() {
		Plugin.SERVICES.stop();
		return this;
	}
}
