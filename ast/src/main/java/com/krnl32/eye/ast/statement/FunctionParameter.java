package com.krnl32.eye.ast.statement;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.DatatypeType;
import com.krnl32.eye.ast.types.TypeQualifierType;

public class FunctionParameter {
	private final TypeQualifierType typeQualifier;
	private final DatatypeType datatype;
	private final String identifier;
	private final Expression initializer;

	public FunctionParameter(TypeQualifierType typeQualifier, DatatypeType datatype, String identifier, Expression initializer) {
		this.typeQualifier = typeQualifier;
		this.datatype = datatype;
		this.identifier = identifier;
		this.initializer = initializer;
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
