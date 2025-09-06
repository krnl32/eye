package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

import java.util.Objects;

public class Char8Literal implements Literal {
	private final char value;

	public Char8Literal(char value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.CHAR8;
	}

	public char getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Char8Literal that = (Char8Literal) o;
		return value == that.value;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return "Char8Literal{" +
			"value=" + value +
			'}';
	}
}
