package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.DatatypeType;
import com.krnl32.eye.ast.types.TypeQualifierType;
import com.krnl32.eye.common.utility.SourceSpan;

public class FunctionParameter {
	private final SourceSpan sourceSpan;
	private final TypeQualifierType typeQualifier;
	private final DatatypeType datatype;
	private final String identifier;
	private final Expression initializer;

	public FunctionParameter(SourceSpan sourceSpan, TypeQualifierType typeQualifier, DatatypeType datatype, String identifier, Expression initializer) {
		this.sourceSpan = sourceSpan;
		this.typeQualifier = typeQualifier;
		this.datatype = datatype;
		this.identifier = identifier;
		this.initializer = initializer;
	}

	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	public TypeQualifierType getTypeQualifier() {
		return typeQualifier;
	}

	public DatatypeType getDatatype() {
		return datatype;
	}

	public String getIdentifier() {
		return identifier;
	}

	public Expression getInitializer() {
		return initializer;
	}
}
