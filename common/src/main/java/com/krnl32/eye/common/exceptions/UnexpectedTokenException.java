package com.krnl32.eye.common.exceptions;

import com.krnl32.eye.common.utility.SourceSpan;

public class UnexpectedTokenException extends EyeException {
	public UnexpectedTokenException(String message, SourceSpan span) {
		super("UnexpectedTokenException: '" + message + "'", span);
	}
}
