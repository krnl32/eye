package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

public class ControlStatement implements Statement {
	private final SourceSpan sourceSpan;
	private final Expression condition;
	private final Statement consequent;
	private final Statement alternate;

	public ControlStatement(SourceSpan sourceSpan, Expression condition, Statement consequent, Statement alternate) {
		this.sourceSpan = sourceSpan;
		this.condition = condition;
		this.consequent = consequent;
		this.alternate = alternate;
	}

	@Override
	public StatementType getType() {
		return StatementType.ControlStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
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
