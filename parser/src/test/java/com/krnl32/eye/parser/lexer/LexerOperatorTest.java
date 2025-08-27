package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.FileIO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerOperatorTest {
	@Test
	void testArithmetic() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Arithmetic.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_ARITHMETIC_INCREMENT, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_ARITHMETIC_DECREMENT, tokens.get(2).getType());
	}

	@Test
	void testBinary() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Binary.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_BINARY_PLUS, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_BINARY_MINUS, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_BINARY_STAR, tokens.get(4).getType());
		assertEquals(TokenType.OPERATOR_BINARY_SLASH, tokens.get(6).getType());
		assertEquals(TokenType.OPERATOR_BINARY_MODULO, tokens.get(8).getType());
	}

	@Test
	void testAssignment() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Assignment.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_ASSIGNMENT, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_PLUS, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_MINUS, tokens.get(4).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_STAR, tokens.get(6).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_SLASH, tokens.get(8).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_MODULO, tokens.get(10).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_BITWISE_AND, tokens.get(12).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_BITWISE_OR, tokens.get(14).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_BITWISE_XOR, tokens.get(16).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_BITWISE_LEFT_SHIFT, tokens.get(18).getType());
		assertEquals(TokenType.OPERATOR_ASSIGNMENT_BITWISE_RIGHT_SHIFT, tokens.get(20).getType());
	}

	@Test
	void testRelational() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Relational.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_RELATIONAL_EQUALS, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_RELATIONAL_NOT_EQUALS, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_RELATIONAL_SMALLER, tokens.get(4).getType());
		assertEquals(TokenType.OPERATOR_RELATIONAL_GREATER, tokens.get(6).getType());
		assertEquals(TokenType.OPERATOR_RELATIONAL_SMALLER_EQUALS, tokens.get(8).getType());
		assertEquals(TokenType.OPERATOR_RELATIONAL_GREATER_EQUALS, tokens.get(10).getType());
	}

	@Test
	void testLogical() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Logical.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_LOGICAL_AND, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_LOGICAL_OR, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_LOGICAL_NOT, tokens.get(4).getType());
	}

	@Test
	void testBitwise() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Bitwise.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_BITWISE_BINARY_AND, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_BITWISE_BINARY_OR, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_BITWISE_BINARY_XOR, tokens.get(4).getType());
		assertEquals(TokenType.OPERATOR_BITWISE_LEFT_SHIFT, tokens.get(6).getType());
		assertEquals(TokenType.OPERATOR_BITWISE_RIGHT_SHIFT, tokens.get(8).getType());
		assertEquals(TokenType.OPERATOR_BITWISE_NOT, tokens.get(10).getType());
	}

	@Test
	void testOther() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Other.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_LEFT_PARENTHESIS, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_LEFT_BRACKET, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_QUESTION_MARK, tokens.get(4).getType());
		assertEquals(TokenType.OPERATOR_DOT, tokens.get(6).getType());
		assertEquals(TokenType.OPERATOR_COMMA, tokens.get(8).getType());
	}

	@Test
	void testChained() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/operator/Chained.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.OPERATOR_ASSIGNMENT_BITWISE_LEFT_SHIFT, tokens.get(0).getType());
		assertEquals(TokenType.OPERATOR_ARITHMETIC_INCREMENT, tokens.get(1).getType());
		assertEquals(TokenType.OPERATOR_BINARY_PLUS, tokens.get(2).getType());
		assertEquals(TokenType.OPERATOR_BINARY_MINUS, tokens.get(3).getType());
		assertEquals(TokenType.OPERATOR_BINARY_PLUS, tokens.get(4).getType());
		assertEquals(TokenType.OPERATOR_RELATIONAL_SMALLER, tokens.get(5).getType());
		assertEquals(TokenType.OPERATOR_BINARY_SLASH, tokens.get(6).getType());
	}
}
