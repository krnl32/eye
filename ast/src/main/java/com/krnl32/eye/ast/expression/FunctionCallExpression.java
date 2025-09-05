package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;

import java.util.List;

public class FunctionCallExpression implements Expression {
	private final Expression callee;
	private final List<Expression> arguments;

	public FunctionCallExpression(Expression callee, List<Expression> arguments) {
		this.callee = callee;
		this.arguments = arguments;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.FunctionCallExpression;
	}

	public Expression getCallee() {
		return callee;
	}

	public List<Expression> getArguments() {
		return arguments;
	}
}
