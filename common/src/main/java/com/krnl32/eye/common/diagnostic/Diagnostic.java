package com.krnl32.eye.common.diagnostic;

import com.krnl32.eye.common.utility.SourceSpan;

public class Diagnostic {
	private final DiagnosticLevel level;
	private final DiagnosticType type;
	private final DiagnosticCode code;
	private final String message;
	private final SourceSpan sourceSpan;

	public Diagnostic(DiagnosticLevel level, DiagnosticType type, DiagnosticCode code, String message, SourceSpan sourceSpan) {
		this.level = level;
		this.type = type;
		this.code = code;
		this.message = message;
		this.sourceSpan = sourceSpan;
	}

	public DiagnosticLevel getLevel() {
		return level;
	}

	public DiagnosticType getType() {
		return type;
	}

	public DiagnosticCode getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public SourceSpan getSourceSpan() {
		return sourceSpan;
	}

	@Override
	public String toString() {
		return String.format("[%s%s][%s] %s at line %d, col %d",
			level,
			code != null ? " " + code.name() : "",
			type,
			message,
			sourceSpan.getLine(),
			sourceSpan.getColumn()
		);
	}
}
