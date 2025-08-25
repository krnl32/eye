package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.SourceSpan;

public class Token {
	private final TokenType type;
	private final Object value;
	private final SourceSpan span;

	public Token(TokenType type, Object value, SourceSpan span) {
		this.type = type;
		this.value = value;
		this.span = span;
	}

	public TokenType getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue() {
		return (T) value;
	}

	public SourceSpan getSpan() {
		return span;
	}

	@Override
	public String toString() {
		return "Token{" +
			"type=" + type +
			", value=" + value +
			", span=" + span +
			'}';
	}
}
