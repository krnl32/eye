package com.krnl32.eye.common.exceptions;

import com.krnl32.eye.common.utility.SourceSpan;

public class EyeException extends RuntimeException {
	public EyeException(String message, SourceSpan span) {
		super(message + "\t on line " + span.getLine() + ", col " + span.getColumn());
	}
}
