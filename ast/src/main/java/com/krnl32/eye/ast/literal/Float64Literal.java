package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

import java.util.Objects;

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
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Float64Literal that = (Float64Literal) o;
		return Double.compare(value, that.value) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return "Float64Literal{" +
			"value=" + value +
			'}';
	}
}
