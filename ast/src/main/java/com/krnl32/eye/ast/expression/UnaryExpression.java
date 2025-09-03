package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;

public class UnaryExpression implements Expression {
	private final OperatorType operator;
	private final Expression expression;

	public UnaryExpression(OperatorType operator, Expression expression) {
		this.operator = operator;
		this.expression = expression;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.UnaryExpression;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public Expression getExpression() {
		return expression;
	}
}
