package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

public class ExpressionStatement implements Statement {
	private final SourceSpan sourceSpan;
	private final Expression expression;

	public ExpressionStatement(SourceSpan sourceSpan, Expression expression) {
		this.sourceSpan = sourceSpan;
		this.expression = expression;
	}

	@Override
	public StatementType getType() {
		return StatementType.ExpressionStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
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
