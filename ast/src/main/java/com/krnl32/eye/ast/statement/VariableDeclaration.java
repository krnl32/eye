package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.expression.IdentifierExpression;

public class VariableDeclaration {
	private final IdentifierExpression identifier;
	private final Expression initializer;

	public VariableDeclaration(IdentifierExpression identifier, Expression initializer) {
		this.identifier = identifier;
		this.initializer = initializer;
	}

	public IdentifierExpression getIdentifier() {
		return identifier;
	}

	public Expression getInitializer() {
		return initializer;
	}
}
