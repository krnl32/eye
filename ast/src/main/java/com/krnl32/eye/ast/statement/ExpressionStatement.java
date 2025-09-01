package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;

public class ExpressionStatement implements Statement {
	private final Expression expression;

	public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	@Override
	public String toString() {
		return "ExpressionStatement{" +
			"expression=" + expression +
			'}';
	}
}
