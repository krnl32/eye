package com.krnl32.eye.ast.literal;

import com.krnl32.eye.ast.types.LiteralType;

import java.util.Objects;

public class Str8Literal implements Literal {
	private final String value;

	public Str8Literal(String value) {
		this.value = value;
	}

	@Override
	public LiteralType getType() {
		return LiteralType.STR8;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Str8Literal that = (Str8Literal) o;
		return Objects.equals(value, that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return "Str8Literal{" +
			"value='" + value + '\'' +
			'}';
	}
}
