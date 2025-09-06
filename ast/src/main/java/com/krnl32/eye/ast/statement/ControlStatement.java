package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;

public class ControlStatement implements Statement {
	private final Expression condition;
	private final Statement consequent;
	private final Statement alternate;

	public ControlStatement(Expression condition, Statement consequent, Statement alternate) {
		this.condition = condition;
		this.consequent = consequent;
		this.alternate = alternate;
	}

	@Override
	public StatementType getType() {
		return StatementType.ControlStatement;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getConsequent() {
		return consequent;
	}

	public Statement getAlternate() {
		return alternate;
	}
}
