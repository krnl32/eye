package com.krnl32.eye.common.exceptions;

import com.krnl32.eye.common.utility.SourceSpan;

public class UnexpectedTokenException extends EyeException {
	public UnexpectedTokenException(String token, String message, SourceSpan span) {
		super("UnexpectedTokenException: '" + token + "' " + message, span);
	}

	public UnexpectedTokenException(char token, String message, SourceSpan span) {
		super("UnexpectedTokenException: '" + token + "' " + message, span);
	}
}
