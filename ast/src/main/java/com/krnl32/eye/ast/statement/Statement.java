package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

public interface Statement {
	StatementType getType();
	SourceSpan getSourceSpan();
}
