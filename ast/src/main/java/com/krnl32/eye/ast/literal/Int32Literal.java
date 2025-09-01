package com.krnl32.eye.ast.literal;

public class Int32Literal implements Literal {
	private final int value;

	public Int32Literal(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Int32Literal{" +
			"value=" + value +
			'}';
	}
}
