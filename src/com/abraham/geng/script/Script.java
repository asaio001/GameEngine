package com.abraham.geng.script;

import java.io.File;
import java.io.FileReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;

public class Script {
	
	public Script(String scriptName, String functionName) {
		this.scriptName = scriptName;
		this.functionName = functionName;
	}
	
	public boolean exists() {
		File scriptFile = getScriptFile();
		if (!scriptFile.exists()) {
			return false;
		}
		try {
			Context context = Context.enter();
			Scriptable scope = new ImporterTopLevel(context);
			context.evaluateReader(scope, new FileReader(scriptFile), scriptName, 1, null);
			Object o = scope.get(functionName, scope);
			if (o != null && o instanceof Function) {
				return true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public File getScriptFile() {
		return new File(scriptName);
	}
	
	public String getScriptName() {
		return scriptName;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	
	private String scriptName, functionName;
}
