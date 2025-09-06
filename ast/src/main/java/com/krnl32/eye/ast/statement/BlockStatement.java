package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.StatementType;

import java.util.Collections;
import java.util.List;

public class BlockStatement implements Statement {
	private final List<Statement> statements;

	public BlockStatement(List<Statement> statements) {
		this.statements = statements;
	}

	@Override
	public StatementType getType() {
		return StatementType.BlockStatement;
	}

	public List<Statement> getStatements() {
		return Collections.unmodifiableList(statements);
	}
}
