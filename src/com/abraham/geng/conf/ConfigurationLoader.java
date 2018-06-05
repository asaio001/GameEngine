package com.abraham.geng.conf;

import java.io.File;
import java.util.Properties;

public abstract class ConfigurationLoader {

	public abstract String getSetting(String key);
	public abstract void load(File file);
	public abstract void setDefaultValues(Properties defaultValues);

	protected Properties settings = new Properties();
}
