package com.laboki.eclipse.plugin.cleancodesorter;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID =
		"com.laboki.eclipse.plugin.cleancodesorter";
	private static Activator plugin;

	public static Activator
	getDefault() {
		return Activator.plugin;
	}

	@Override
	public void
	start(final BundleContext context) throws Exception {
		super.start(context);
		Activator.plugin = this;
		Plugin.INSTANCE.start();
	}

	@Override
	public void
	stop(final BundleContext context) throws Exception {
		Plugin.INSTANCE.stop();
		super.stop(context);
		Activator.plugin = null;
	}
}
