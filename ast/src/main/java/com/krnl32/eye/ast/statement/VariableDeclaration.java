package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.common.utility.SourceSpan;

public class VariableDeclaration {
	private final SourceSpan sourceSpan;
	private final String identifier;
	private final Expression initializer;

	public VariableDeclaration(SourceSpan sourceSpan, String identifier, Expression initializer) {
		this.sourceSpan = sourceSpan;
		this.identifier = identifier;
		this.initializer = initializer;
	}

	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Expression getInitializer() {
		return initializer;
	}
}
