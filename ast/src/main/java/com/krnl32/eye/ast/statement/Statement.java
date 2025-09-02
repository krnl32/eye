package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.StatementType;

public interface Statement extends TopLevelStatement {
	StatementType getType();
}
