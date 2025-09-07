package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;

public class ForStatement implements Statement {
	private final ForInitializerType initializerType;
	private final Object initializer;
	private final Expression condition;
	private final Expression update;
	private final Statement body;

	public ForStatement(ForInitializerType initializerType, Object initializer, Expression condition, Expression update, Statement body) {
		this.initializerType = initializerType;
		this.initializer = initializer;
		this.condition = condition;
		this.update = update;
		this.body = body;
	}

	@Override
	public StatementType getType() {
		return StatementType.ForStatement;
	}

	public ForInitializerType getInitializerType() {
		return initializerType;
	}

	public Object getInitializer() {
		return initializer;
	}

	public Expression getCondition() {
		return condition;
	}

	public Expression getUpdate() {
		return update;
	}

	public Statement getBody() {
		return body;
	}

	public enum ForInitializerType {
		NULL,
		VARIABLE_STATEMENT,
		EXPRESSION
	}
}
