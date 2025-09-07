package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.StatementType;

public class ContinueStatement implements Statement {
	@Override
	public StatementType getType() {
		return StatementType.ContinueStatement;
	}
}
