package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;
import com.krnl32.eye.common.utility.SourceSpan;

public class PostfixExpression implements Expression {
	private final SourceSpan sourceSpan;
	private final OperatorType operator;
	private final Expression expression;

	public PostfixExpression(SourceSpan sourceSpan, OperatorType operator, Expression expression) {
		this.sourceSpan = sourceSpan;
		this.operator = operator;
		this.expression = expression;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.PostfixExpression;
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
