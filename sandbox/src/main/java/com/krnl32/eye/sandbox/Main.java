package com.krnl32.eye.sandbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krnl32.eye.ast.Program;
import com.krnl32.eye.astserializer.serializers.JSONASTSerializer;
import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.common.diagnostic.DiagnosticReporter;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.parser.Parser;
import com.krnl32.eye.semantic.NameResolver;

import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		DiagnosticReporter diagnosticReporter = new DiagnosticReporter();

		Lexer lexer;
		lexer = new Lexer("uint32_t x = 12; uint32_t y = z + a;");

		List<Token> tokens = lexer.tokenize();
		if (tokens == null) {
			Logger.error("Tokenizer Failed");
			return;
		}

		Parser parser = new Parser(tokens);
		System.out.println("Parsing: \n");

		Program program = parser.parse();
		if (program == null) {
			Logger.error("Parser Failed");
			return;
		}

		JSONASTSerializer serializer = new JSONASTSerializer();
		ObjectNode data = serializer.serialize(program);
		System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data));

		NameResolver nameResolver = new NameResolver(program, diagnosticReporter);
		if (!nameResolver.validate()) {
			Logger.error("Name Resolver Failed");
			diagnosticReporter.printAll();
		}
	}
}
