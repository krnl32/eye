package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;

public class MemberAccessExpression implements Expression {
	private final OperatorType operator;
	private final Expression object;
	private final Expression property;

	public MemberAccessExpression(OperatorType operator, Expression object, Expression property) {
		this.operator = operator;
		this.object = object;
		this.property = property;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.MemberAccessExpression;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public Expression getObject() {
		return object;
	}

	public Expression getProperty() {
		return property;
	}
}
