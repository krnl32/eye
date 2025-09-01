package com.krnl32.eye.common.exceptions;

import com.krnl32.eye.common.utility.SourceSpan;

public class SyntaxErrorException extends EyeException {
	public SyntaxErrorException(String message, SourceSpan span) {
		super("SyntaxErrorException: " + message, span);
	}
}
