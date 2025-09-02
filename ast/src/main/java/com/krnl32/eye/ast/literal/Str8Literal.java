package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class Str8Literal implements Literal {
	private final String value;

	public Str8Literal(String value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.STR8;
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
