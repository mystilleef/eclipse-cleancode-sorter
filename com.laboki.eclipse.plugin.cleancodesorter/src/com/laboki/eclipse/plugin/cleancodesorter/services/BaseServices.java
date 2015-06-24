package com.laboki.eclipse.plugin.cleancodesorter.services;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.laboki.eclipse.plugin.cleancodesorter.contexts.EditorContext;
import com.laboki.eclipse.plugin.cleancodesorter.instance.Instance;

public abstract class BaseServices implements Instance {

	private final List<Instance> instances = Lists.newArrayList();

	@Override
	public Instance
	start() {
		this.startServices();
		return this;
	}

	protected abstract void
	startServices();

	@Override
	public Instance
	stop() {
		BaseServices.cancelTasks();
		this.stopServices();
		this.instances.clear();
		return this;
	}

	protected static void
	cancelTasks() {
		EditorContext.cancelEventTasks();
		EditorContext.cancelPluginTasks();
	}

	private void
	stopServices() {
		for (final Instance instance : ImmutableList.copyOf(this.instances))
			this.stopService(instance);
	}

	private void
	stopService(final Instance instance) {
		instance.stop();
		this.instances.remove(instance);
	}

	protected void
	startService(final Instance instance) {
		instance.start();
		this.instances.add(instance);
	}
}
