package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.literal.Literal;
import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.common.utility.SourceSpan;

public class LiteralExpression implements Expression {
	private final SourceSpan sourceSpan;
	private final Literal literal;

	public LiteralExpression(SourceSpan sourceSpan, Literal literal) {
		this.sourceSpan = sourceSpan;
		this.literal = literal;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.LiteralExpression;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
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
