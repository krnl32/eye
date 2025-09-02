package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

public class Float64Literal implements Literal {
	private final double value;

	public Float64Literal(double value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.FLOAT64;
	}

	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "Float64Literal{" +
			"value=" + value +
			'}';
	}
}
