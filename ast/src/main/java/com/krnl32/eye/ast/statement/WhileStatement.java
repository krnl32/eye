package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;

public class WhileStatement implements Statement {
	private final Expression condition;
	private final Statement body;

	public WhileStatement(Expression condition, Statement body) {
		this.condition = condition;
		this.body = body;
	}

	@Override
	public StatementType getType() {
		return StatementType.WhileStatement;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getBody() {
		return body;
	}
}
