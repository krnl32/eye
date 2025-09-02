package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;

public class ExpressionStatement implements Statement {
	private final Expression expression;

	public ExpressionStatement(Expression expression) {
		this.expression = expression;
	}

	@Override
	public StatementType getType() {
		return StatementType.ExpressionStatement;
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
