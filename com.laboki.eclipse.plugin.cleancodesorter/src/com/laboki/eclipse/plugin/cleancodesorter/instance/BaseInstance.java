package com.laboki.eclipse.plugin.cleancodesorter.instance;

public abstract class BaseInstance implements Instance {

	@Override
	public Instance
	start() {
		return this;
	}

	@Override
	public Instance
	stop() {
		return this;
	}
}
