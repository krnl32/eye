package com.krnl32.eye.ast.literal;

public class Float32Literal implements Literal {
	private final float value;

	public Float32Literal(float value) {
		this.value = value;
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
