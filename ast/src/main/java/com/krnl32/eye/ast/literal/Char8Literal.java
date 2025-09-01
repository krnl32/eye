package com.krnl32.eye.ast.literal;

public class Char8Literal implements Literal {
	private final char value;

	public Char8Literal(char value) {
		this.value = value;
	}

	public char getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Char8Literal{" +
			"value=" + value +
			'}';
	}
}
