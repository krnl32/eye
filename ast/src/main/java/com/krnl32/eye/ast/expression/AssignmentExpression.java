package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;
import com.krnl32.eye.common.utility.SourceSpan;

public class AssignmentExpression implements Expression {
	private final SourceSpan sourceSpan;
	private final OperatorType operator;
	private final Expression left;
	private final Expression right;

	public AssignmentExpression(SourceSpan sourceSpan, OperatorType operator, Expression left, Expression right) {
		this.sourceSpan = sourceSpan;
		this.operator = operator;
		this.left = left;
		this.right = right;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.AssignmentExpression;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public OperatorType getOperator() {
		return operator;
	}

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}
}
