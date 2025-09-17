package com.krnl32.eye.semantic;

import com.krnl32.eye.ast.Program;
import com.krnl32.eye.common.diagnostic.DiagnosticCode;
import com.krnl32.eye.common.diagnostic.DiagnosticReporter;
import com.krnl32.eye.common.utility.FileIO;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NameResolverTest {
	@Test
	void testVariableNotDeclared() throws IOException {
		DiagnosticReporter diagnosticReporter = generateDiagnostic("variable_not_declared");
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR000));
	}

	@Test
	void testVariableRedeclaration() throws IOException {
		DiagnosticReporter diagnosticReporter = generateDiagnostic("variable_redeclaration");
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR001));
	}

	@Test
	void testFunctionNotDeclared() throws IOException {
		DiagnosticReporter diagnosticReporter = generateDiagnostic("function_not_declared");
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR000));
	}

	@Test
	void testFunctionRedeclaration() throws IOException {
		DiagnosticReporter diagnosticReporter = generateDiagnostic("function_redeclaration");
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR001));
	}

	@Test
	void testFunctionParameter() throws IOException {
		DiagnosticReporter diagnosticReporter = generateDiagnostic("function_parameter");
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR000));
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR001));
	}

	@Test
	void testBlockStatement() throws IOException {
		DiagnosticReporter diagnosticReporter = generateDiagnostic("block_statement");
		assertEquals(false, diagnosticReporter.hasError(DiagnosticCode.NR000));
		assertEquals(false, diagnosticReporter.hasError(DiagnosticCode.NR001));
	}

	@Test
	void testForStatement() throws IOException {
		DiagnosticReporter diagnosticReporter = generateDiagnostic("for_statement");
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR000));
		assertEquals(true, diagnosticReporter.hasError(DiagnosticCode.NR001));
		diagnosticReporter.printAll();
	}

	private DiagnosticReporter generateDiagnostic(String testName) throws IOException {
		DiagnosticReporter diagnosticReporter = new DiagnosticReporter();
		String source = FileIO.readResourceFileContent("nameresolver/" + testName + ".eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		Parser parser = new Parser(tokens);
		Program ast = parser.parse();

		NameResolver nameResolver = new NameResolver(ast, diagnosticReporter);
		nameResolver.validate();
		return diagnosticReporter;
	}
}
