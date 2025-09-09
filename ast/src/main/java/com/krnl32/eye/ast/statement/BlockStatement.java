package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

import java.util.Collections;
import java.util.List;

public class BlockStatement implements Statement {
	private final SourceSpan sourceSpan;
	private final List<Statement> statements;

	public BlockStatement(SourceSpan sourceSpan, List<Statement> statements) {
		this.sourceSpan = sourceSpan;
		this.statements = statements;
	}

	@Override
	public StatementType getType() {
		return StatementType.BlockStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public List<Statement> getStatements() {
		return Collections.unmodifiableList(statements);
	}
}
