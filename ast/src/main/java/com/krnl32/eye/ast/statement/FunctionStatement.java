package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.DatatypeType;
import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.common.utility.SourceSpan;

import java.util.Collections;
import java.util.List;

public class FunctionStatement implements Statement {
	private final SourceSpan sourceSpan;
	private final DatatypeType returnType;
	private final String identifier;
	private final List<FunctionParameter> parameters;
	private final Statement body;

	public FunctionStatement(SourceSpan sourceSpan, DatatypeType returnType, String identifier, List<FunctionParameter> parameters, Statement body) {
		this.sourceSpan = sourceSpan;
		this.returnType = returnType;
		this.identifier = identifier;
		this.parameters = parameters;
		this.body = body;
	}

	@Override
	public StatementType getType() {
		return StatementType.FunctionStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public DatatypeType getReturnType() {
		return returnType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public List<FunctionParameter> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	public Statement getBody() {
		return body;
	}
}
