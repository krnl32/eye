package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;

public class AssignmentExpression implements Expression {
	private final OperatorType operator;
	private final Expression left;
	private final Expression right;

	public AssignmentExpression(OperatorType operator, Expression left, Expression right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.AssignmentExpression;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}
}
