package com.krnl32.eye.common.exceptions;

import com.krnl32.eye.common.utility.SourceSpan;

public class RedeclarationException extends EyeException {
	public RedeclarationException(String message, SourceSpan span) {
		super("RedeclarationException: " + message, span);
	}
}
