package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class Bool8Literal implements Literal {
	private final boolean value;

	public Bool8Literal(boolean value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.BOOL8;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Bool8Literal{" +
			"value=" + value +
			'}';
	}
}
