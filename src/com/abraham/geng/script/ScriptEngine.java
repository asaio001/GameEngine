package com.abraham.geng.script;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Scriptable;

public class ScriptEngine {
	
	public ScriptEngine() {
		context = Context.enter();
		scope = new ImporterTopLevel(context);
	}
	
	public void clearCachedFunctions() {
		functions.clear();
	}
	
	private Object invokeFunction(File script, String scriptName, String funcName, Object... args)  {
		try {
			if (functions.containsKey(script + "." + funcName))  {
				return functions.get(script + "." + funcName).call(context, scope, scope, args);
			}
			try {
				context.evaluateReader(scope, new FileReader(script), scriptName, 1, null);
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
			Function func = (Function) scope.get(funcName, scope);
			functions.put(script + "." + funcName, func);
			return func.call(context, scope, scope, args);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object invokeScript(Script s, Object...args) {
		if (!s.exists()) {
			new RuntimeException("Script: " + s.getScriptName() + ", Function: " + s.getFunctionName() + " has a syntactical error or does not exist.").printStackTrace();
			return null;
		}
		return invokeFunction(s.getScriptFile(), s.getScriptName(), s.getFunctionName(), args);
	}
	
	private Context context;
	private Scriptable scope;
	private HashMap<String, Function> functions = new HashMap<String, Function>();
}
