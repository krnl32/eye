package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class Char8Literal implements Literal {
	private final char value;

	public Char8Literal(char value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.CHAR8;
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
