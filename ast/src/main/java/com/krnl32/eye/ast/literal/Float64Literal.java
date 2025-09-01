package com.krnl32.eye.ast.literal;

public class Float64Literal implements Literal {
	private final double value;

	public Float64Literal(double value) {
		this.value = value;
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
