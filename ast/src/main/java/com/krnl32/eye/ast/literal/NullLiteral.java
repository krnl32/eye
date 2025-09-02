package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class NullLiteral implements Literal {
	@Override
	public LiteralType getType() {
		return null;
	}

	@Override
	public String toString() {
		return "NullLiteral{}";
	}
}
