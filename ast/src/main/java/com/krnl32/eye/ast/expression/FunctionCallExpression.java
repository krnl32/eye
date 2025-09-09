package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.common.utility.SourceSpan;

import java.util.List;

public class FunctionCallExpression implements Expression {
	private final SourceSpan sourceSpan;
	private final Expression callee;
	private final List<Expression> arguments;

	public FunctionCallExpression(SourceSpan sourceSpan, Expression callee, List<Expression> arguments) {
		this.sourceSpan = sourceSpan;
		this.callee = callee;
		this.arguments = arguments;
	}

	@Override
	public ExpressionType getType() {
		return ExpressionType.FunctionCallExpression;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public Expression getCallee() {
		return callee;
	}

	public List<Expression> getArguments() {
		return arguments;
	}
}
