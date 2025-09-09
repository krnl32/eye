package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

public class DoWhileStatement implements Statement {
	private final SourceSpan sourceSpan;
	private final Expression condition;
	private final Statement body;

	public DoWhileStatement(SourceSpan sourceSpan, Expression condition, Statement body) {
		this.sourceSpan = sourceSpan;
		this.condition = condition;
		this.body = body;
	}

	@Override
	public StatementType getType() {
		return StatementType.DoWhileStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public Expression getCondition() {
		return condition;
	}

	public Statement getBody() {
		return body;
	}
}
