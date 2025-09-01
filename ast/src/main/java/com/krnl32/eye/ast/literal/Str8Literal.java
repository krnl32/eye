package com.krnl32.eye.ast.literal;

public class Str8Literal implements Literal {
	private final String value;

	public Str8Literal(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Str8Literal{" +
			"value='" + value + '\'' +
			'}';
	}
}
