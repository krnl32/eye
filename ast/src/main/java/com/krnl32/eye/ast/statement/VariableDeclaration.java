package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;

public class VariableDeclaration {
	private final String identifier;
	private final Expression initializer;

	public VariableDeclaration(String identifier, Expression initializer) {
		this.identifier = identifier;
		this.initializer = initializer;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Expression getInitializer() {
		return initializer;
	}
}
