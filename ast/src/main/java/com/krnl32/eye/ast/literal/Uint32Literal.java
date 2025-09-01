package com.krnl32.eye.ast.literal;

public class Uint32Literal implements Literal {
	private final int value;

	public Uint32Literal(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Uint32Literal{" +
			"value=" + value +
			'}';
	}
}
