package com.laboki.eclipse.plugin.cleancodesorter.instance;

import com.laboki.eclipse.plugin.cleancodesorter.main.EventBus;

public abstract class EventBusInstance extends BaseInstance {

	@Override
	public Instance
	start() {
		EventBus.register(this);
		return super.start();
	}

	@Override
	public Instance
	stop() {
		EventBus.unregister(this);
		return super.stop();
	}
}
