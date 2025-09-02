package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class Uint32Literal implements Literal {
	private final int value;

	public Uint32Literal(int value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.UINT32;
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
