package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;

public class PostfixExpression implements Expression {
	private final OperatorType operator;
	private final Expression expression;

	public PostfixExpression(OperatorType operator, Expression expression) {
		this.operator = operator;
		this.expression = expression;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.PostfixExpression;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public Expression getExpression() {
		return expression;
	}
}
