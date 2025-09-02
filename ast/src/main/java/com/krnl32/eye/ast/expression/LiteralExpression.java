package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.literal.Literal;
import com.krnl32.eye.ast.types.ExpressionType;

public class LiteralExpression implements Expression {
	private final Literal literal;

	public LiteralExpression(Literal literal) {
		this.literal = literal;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.LiteralExpression;
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
