package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

import java.util.Objects;

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
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Float32Literal that = (Float32Literal) o;
		return Float.compare(value, that.value) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return "Float32Literal{" +
			"value=" + value +
			'}';
	}
}
