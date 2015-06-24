package com.laboki.eclipse.plugin.cleancodesorter.main;

import com.laboki.eclipse.plugin.cleancodesorter.services.BaseServices;

public final class Services extends BaseServices {

	@Override
	protected void
	startServices() {
		this.startService(new PartMonitor());
	}
}
