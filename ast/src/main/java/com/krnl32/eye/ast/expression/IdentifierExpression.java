package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;

public class IdentifierExpression implements Expression {
	private final String identifier;

	public IdentifierExpression(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.IdentifierExpression;
	}

	public String getIdentifier() {
		return identifier;
	}
}
