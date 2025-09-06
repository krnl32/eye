package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

import java.util.Objects;

public class Int32Literal implements Literal {
	private final int value;

	public Int32Literal(int value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.INT32;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Int32Literal that = (Int32Literal) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return "Int32Literal{" +
			"value=" + value +
			'}';
	}
}
