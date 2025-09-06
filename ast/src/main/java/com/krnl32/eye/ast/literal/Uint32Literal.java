package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

import java.util.Objects;

public class Uint32Literal implements Literal {
	private final int value;

	public Uint32Literal(int value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.UINT32;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Uint32Literal that = (Uint32Literal) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return "Uint32Literal{" +
			"value=" + value +
			'}';
	}
}
