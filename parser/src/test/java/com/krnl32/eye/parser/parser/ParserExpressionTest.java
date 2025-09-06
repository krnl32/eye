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

public class ParserExpressionTest {
	private final JSONASTSerializer jsonASTSerializer = new JSONASTSerializer();
	private final ObjectMapper mapper = new ObjectMapper();

	@Test
	void testLiteral() throws IOException {
		runTest("literal");
	}

	@Test
	void testIdentifier() throws IOException {
		runTest("identifier");
	}

	@Test
	void testParenthesized() throws IOException {
		runTest("parenthesized");
	}

	@Test
	void testUnary() throws IOException {
		runTest("unary");
	}

	@Test
	void testLogical() throws IOException {
		runTest("logical");
	}

	@Test
	void testBitwise() throws IOException {
		runTest("bitwise");
	}

	@Test
	void testEquality() throws IOException {
		runTest("equality");
	}

	@Test
	void testRelational() throws IOException {
		runTest("relational");
	}

	@Test
	void testBitwiseShift() throws IOException {
		runTest("bitwise_shift");
	}

	@Test
	void testAdditive() throws IOException {
		runTest("additive");
	}

	@Test
	void testMultiplicative() throws IOException {
		runTest("multiplicative");
	}

	@Test
	void testTernary() throws IOException {
		runTest("ternary");
	}

	@Test
	void testAssignment() throws IOException {
		runTest("assignment");
	}

	@Test
	void testFunctionCall() throws IOException {
		runTest("function_call");
	}

	@Test
	void testPostfix() throws IOException {
		runTest("postfix");
	}

	private void runTest(String testName) throws IOException {
		String source = FileIO.readResourceFileContent("parser/expression/" + testName + ".eye");
		String expected = FileIO.readResourceFileContent("parser/expression/" + testName + ".json");

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
