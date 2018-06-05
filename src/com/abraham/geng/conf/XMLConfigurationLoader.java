package com.abraham.geng.conf;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class XMLConfigurationLoader extends ConfigurationLoader {
	
	public String getSetting(String key) {
		return settings.getProperty(key);
	}

	public void load(File file) {
		try {
			settings.loadFromXML(new FileInputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setDefaultValues(Properties defaultValues) {
		settings = new Properties(defaultValues);
	}
}
