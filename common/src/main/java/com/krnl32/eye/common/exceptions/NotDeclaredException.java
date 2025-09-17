package com.krnl32.eye.common.exceptions;

import com.krnl32.eye.common.utility.SourceSpan;

public class NotDeclaredException extends EyeException {
	public NotDeclaredException(String message, SourceSpan span) {
		super("NotDeclaredException: " + message, span);
	}
}
