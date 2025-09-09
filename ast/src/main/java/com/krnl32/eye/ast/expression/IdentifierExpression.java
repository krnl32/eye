package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.common.utility.SourceSpan;

public class IdentifierExpression implements Expression {
	private final SourceSpan sourceSpan;
	private final String identifier;

	public IdentifierExpression(SourceSpan sourceSpan, String identifier) {
		this.sourceSpan = sourceSpan;
		this.identifier = identifier;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.IdentifierExpression;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public String getIdentifier() {
		return identifier;
	}
}
