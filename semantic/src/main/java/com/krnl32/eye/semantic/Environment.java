package com.krnl32.eye.semantic;

import com.krnl32.eye.common.exceptions.EyeException;
import com.krnl32.eye.common.utility.SourceSpan;

import java.util.HashMap;
import java.util.Map;

public class Environment<T> {
	private final Environment<T> parent;
	private final Map<String, T> map;

	public Environment(Environment<T> parent) {
		this.parent = parent;
		this.map = new HashMap<>();
	}

	public Environment<T> getParent() {
		return parent;
	}

	public void define(String identifier, T value) {
		map.put(identifier, value);
	}

	public void assign(String identifier, T value) {
		if (map.containsKey(identifier)) {
			map.put(identifier, value);
		} else if(parent != null) {
			parent.assign(identifier, value);
		} else {
			throw new EyeException("Environment Failed to Assign ({}), Variable not Defined", new SourceSpan(-1, -1, -1, -1));
		}
	}

	public T get(String identifier) {
		if (map.containsKey(identifier)) {
			return map.get(identifier);
		} else if(parent != null) {
			return (T) parent.get(identifier);
		}

		return null;
	}

	public boolean has(String identifier, boolean checkParent) {
		if (map.containsKey(identifier)) {
			return true;
		} else if(checkParent && parent != null) {
			return parent.has(identifier, true);
		}

		return false;
	}

	@Override
	public String toString() {
		return "Environment{" +
			"parent=" + parent +
			", map=" + map +
			'}';
	}
}
