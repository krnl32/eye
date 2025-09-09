package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

public class BreakStatement implements Statement {
	private final SourceSpan sourceSpan;

	public BreakStatement(SourceSpan sourceSpan) {
		this.sourceSpan = sourceSpan;
	}

	@Override
	public StatementType getType() {
		return StatementType.BreakStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}
}
