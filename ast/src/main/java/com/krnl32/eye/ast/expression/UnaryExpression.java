package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;
import com.krnl32.eye.common.utility.SourceSpan;

public class UnaryExpression implements Expression {
	private final SourceSpan sourceSpan;
	private final OperatorType operator;
	private final Expression expression;

	public UnaryExpression(SourceSpan sourceSpan, OperatorType operator, Expression expression) {
		this.sourceSpan = sourceSpan;
		this.operator = operator;
		this.expression = expression;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.UnaryExpression;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public Expression getExpression() {
		return expression;
	}
}
