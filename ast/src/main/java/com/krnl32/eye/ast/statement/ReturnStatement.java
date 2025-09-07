package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;

public class ReturnStatement implements Statement {
	private final Expression expression;

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}

	@Override
	public StatementType getType() {
		return StatementType.ReturnStatement;
	}

	public Expression getExpression() {
		return expression;
	}
}
