package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.SourceSpan;

public class Token {
	private final TokenType type;
	private final Object value;
	private final SourceSpan sourceSpan;

	public Token(TokenType type, Object value, SourceSpan sourceSpan) {
		this.type = type;
		this.value = value;
		this.sourceSpan = sourceSpan;
	}

	public TokenType getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}

	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	@Override
	public String toString() {
		return "Token{" +
			"type=" + type +
			", value=" + value +
			", sourceSpan=" + sourceSpan +
			'}';
	}
}
