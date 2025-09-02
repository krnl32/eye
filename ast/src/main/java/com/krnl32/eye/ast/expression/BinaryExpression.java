package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;

public class BinaryExpression implements Expression {
	private final OperatorType operatorType;
	private final Expression left;
	private final Expression right;

	public BinaryExpression(OperatorType operatorType, Expression left, Expression right) {
		this.operatorType = operatorType;
		this.left = left;
		this.right = right;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.BinaryExpression;
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}

	@Override
	public String toString() {
		return "BinaryExpression{" +
			"operatorType=" + operatorType +
			", left=" + left +
			", right=" + right +
			'}';
	}
}
