package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;

public class ArrayAccessExpression implements Expression {
	private final Expression array;
	private final Expression index;

	public ArrayAccessExpression(Expression array, Expression index) {
		this.array = array;
		this.index = index;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.ArrayAccessExpression;
	}

	public Expression getArray() {
		return array;
	}

	public Expression getIndex() {
		return index;
	}
}
