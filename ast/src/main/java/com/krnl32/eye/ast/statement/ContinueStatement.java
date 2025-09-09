package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

public class ContinueStatement implements Statement {
	private final SourceSpan sourceSpan;

	public ContinueStatement(SourceSpan sourceSpan) {
		this.sourceSpan = sourceSpan;
	}

	@Override
	public StatementType getType() {
		return StatementType.ContinueStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}
}
