package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.common.utility.SourceSpan;

public class TernaryExpression implements Expression {
	private final SourceSpan sourceSpan;
	private final Expression condition;
	private final Expression consequent;
	private final Expression alternate;

	public TernaryExpression(SourceSpan sourceSpan, Expression condition, Expression consequent, Expression alternate) {
		this.sourceSpan = sourceSpan;
		this.condition = condition;
		this.consequent = consequent;
		this.alternate = alternate;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.TernaryExpression;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public Expression getCondition() {
		return condition;
	}

	public Expression getConsequent() {
		return consequent;
	}

	public Expression getAlternate() {
		return alternate;
	}
}
