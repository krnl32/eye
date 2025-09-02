package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class Float32Literal implements Literal {
	private final float value;

	public Float32Literal(float value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.FLOAT32;
	}

	public float getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Float32Literal{" +
			"value=" + value +
			'}';
	}
}
