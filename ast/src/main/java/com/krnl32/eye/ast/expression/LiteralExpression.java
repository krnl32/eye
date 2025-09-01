package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.literal.Literal;

public class LiteralExpression implements PrimaryExpression {
	private final Literal literal;

	public LiteralExpression(Literal literal) {
		this.literal = literal;
	}

	public Literal getLiteral() {
		return literal;
	}

	@Override
	public String toString() {
		return "LiteralExpression{" +
			"literal=" + literal +
			'}';
	}
}
