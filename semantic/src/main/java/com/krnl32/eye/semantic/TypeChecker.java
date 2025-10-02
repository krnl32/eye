package com.krnl32.eye.semantic;

import com.krnl32.eye.ast.Program;
import com.krnl32.eye.common.diagnostic.DiagnosticReporter;

public class TypeChecker {
	private final Program ast;
	private final DiagnosticReporter diagnosticReporter;

	public TypeChecker(Program ast, DiagnosticReporter diagnosticReporter) {
		this.ast = ast;
		this.diagnosticReporter = diagnosticReporter;
	}
}
