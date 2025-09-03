package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;

public class TernaryExpression implements Expression {
	private final Expression condition;
	private final Expression consequent;
	private final Expression alternate;

	public TernaryExpression(Expression condition, Expression consequent, Expression alternate) {
		this.condition = condition;
		this.consequent = consequent;
		this.alternate = alternate;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.TernaryExpression;
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
