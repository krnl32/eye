package com.krnl32.eye.ast.expression;

import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.common.utility.SourceSpan;

public interface Expression {
	ExpressionType getType();
	SourceSpan getSourceSpan();
}
