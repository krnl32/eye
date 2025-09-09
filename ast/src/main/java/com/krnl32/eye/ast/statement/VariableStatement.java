package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.types.DatatypeType;
import com.krnl32.eye.ast.types.StatementType;
import com.krnl32.eye.ast.types.TypeQualifierType;
import com.krnl32.eye.common.utility.SourceSpan;

import java.util.Collections;
import java.util.List;

public class VariableStatement implements Statement {
	private final SourceSpan sourceSpan;
	private final TypeQualifierType typeQualifier;
	private final DatatypeType datatype;
	private final List<VariableDeclaration> variableDeclarations;

	public VariableStatement(SourceSpan sourceSpan, TypeQualifierType typeQualifier, DatatypeType datatype, List<VariableDeclaration> variableDeclarations) {
		this.sourceSpan = sourceSpan;
		this.typeQualifier = typeQualifier;
		this.datatype = datatype;
		this.variableDeclarations = variableDeclarations;
	}

	@Override
	public StatementType getType() {
		return StatementType.VariableStatement;
	}

	@Override
	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public TypeQualifierType getTypeQualifier() {
		return typeQualifier;
	}

	public DatatypeType getDatatype() {
		return datatype;
	}

	public List<VariableDeclaration> getVariableDeclarations() {
		return Collections.unmodifiableList(variableDeclarations);
	}
}
