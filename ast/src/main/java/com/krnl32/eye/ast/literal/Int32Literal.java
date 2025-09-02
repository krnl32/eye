package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class Int32Literal implements Literal {
	private final int value;

	public Int32Literal(int value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.INT32;
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
