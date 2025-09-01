package com.krnl32.eye.ast.literal;

public class Bool8Literal implements Literal {
	private final boolean value;

	public Bool8Literal(boolean value) {
		this.value = value;
	}

	public boolean isValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Bool8Literal{" +
			"value=" + value +
			'}';
	}
}
