package com.krnl32.eye.parser.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krnl32.eye.ast.Program;
import com.krnl32.eye.astserializer.serializers.JSONASTSerializer;
import com.krnl32.eye.common.utility.FileIO;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserStatementTest {
	private final JSONASTSerializer jsonASTSerializer = new JSONASTSerializer();
	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	void testVariable() throws IOException {
		runTest("variable");
	}

	@Test
	void testFunction() throws IOException {
		runTest("function");
	}

	private void runTest(String testName) throws IOException {
		String source = FileIO.readResourceFileContent("parser/statement/" + testName + ".eye");
		String expected = FileIO.readResourceFileContent("parser/statement/" + testName + ".json");

		ObjectNode sourceNode = generateASTJson(source);
		ObjectNode expectedNode = mapper.readValue(expected, ObjectNode.class);

		assertEquals(expectedNode.toString(), sourceNode.toString());
	}

	private ObjectNode generateASTJson(String eyeSource) {
		Lexer lexer = new Lexer(eyeSource);
		List<Token> tokens = lexer.tokenize();

		Parser parser = new Parser(tokens);
		Program ast = parser.parse();

		return jsonASTSerializer.serialize(ast);
	}
}
