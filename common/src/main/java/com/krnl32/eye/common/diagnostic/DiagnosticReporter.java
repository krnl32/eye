package com.krnl32.eye.common.diagnostic;

import com.krnl32.eye.common.utility.SourceSpan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DiagnosticReporter {
	private final List<Diagnostic> diagnostics = new ArrayList<>();

	public void report(DiagnosticLevel level, DiagnosticType type, DiagnosticCode code, String message, SourceSpan sourceSpan) {
		diagnostics.add(new Diagnostic(level, type, code, message, sourceSpan));
	}

	public void reportInfo(DiagnosticType type, DiagnosticCode code, String message, SourceSpan sourceSpan) {
		diagnostics.add(new Diagnostic(DiagnosticLevel.INFO, type, code, message, sourceSpan));
	}

	public void reportWarning(DiagnosticType type, DiagnosticCode code, String message, SourceSpan sourceSpan) {
		diagnostics.add(new Diagnostic(DiagnosticLevel.WARNING, type, code, message, sourceSpan));
	}

	public void reportError(DiagnosticType type, DiagnosticCode code, String message, SourceSpan sourceSpan) {
		diagnostics.add(new Diagnostic(DiagnosticLevel.ERROR, type, code, message, sourceSpan));
	}

	public List<Diagnostic> getDiagnostics() {
		return Collections.unmodifiableList(diagnostics);
	}

	public List<Diagnostic> getDiagnostics(DiagnosticLevel level) {
		return diagnostics.stream()
			.filter(diagnostic -> diagnostic.getLevel() == level)
			.toList();
	}

	public List<Diagnostic> getDiagnostics(DiagnosticType type) {
		return diagnostics.stream()
			.filter(diagnostic -> diagnostic.getType() == type)
			.toList();
	}

	public List<Diagnostic> getDiagnostics(DiagnosticCode code) {
		return diagnostics.stream()
			.filter(diagnostic -> diagnostic.getCode() == code)
			.toList();
	}

	public boolean hasError() {
		return diagnostics.stream()
			.anyMatch(diagnostic -> diagnostic.getLevel() == DiagnosticLevel.ERROR);
	}

	public boolean hasError(DiagnosticCode code) {
		return diagnostics.stream()
			.anyMatch(diagnostic ->
				diagnostic.getLevel() == DiagnosticLevel.ERROR && diagnostic.getCode() == code
			);
	}

	public void printAll() {
		diagnostics.forEach(System.out::println);
	}
}
