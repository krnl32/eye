package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.FileIO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerLiteralTest {

	@Test
	void testIntegerDecimal() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/IntegerDecimal.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(0).getType());
		assertEquals(12345, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(1).getType());
		assertEquals(123, tokens.get(1).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(3).getType());
		assertEquals(456, tokens.get(3).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(5).getType());
		assertEquals(214912, tokens.get(5).<Integer>getValue());
	}

	@Test
	void testIntegerHex() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/IntegerHex.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(0).getType());
		assertEquals(4660, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(1).getType());
		assertEquals(1245, tokens.get(1).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(3).getType());
		assertEquals(5126788, tokens.get(3).<Integer>getValue());
	}

	@Test
	void testIntegerBinary() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/IntegerBinary.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(0).getType());
		assertEquals(6, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(1).getType());
		assertEquals(51256, tokens.get(1).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(3).getType());
		assertEquals(1256, tokens.get(3).<Integer>getValue());
	}

	@Test
	void testFloat() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/Float.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_FLOAT, tokens.get(0).getType());
		assertEquals(123.456f, tokens.get(0).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT, tokens.get(1).getType());
		assertEquals(12.3356f, tokens.get(1).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT, tokens.get(3).getType());
		assertEquals(8510.1457f, tokens.get(3).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT, tokens.get(5).getType());
		assertEquals(55.f, tokens.get(5).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT, tokens.get(7).getType());
		assertEquals(12.0f, tokens.get(7).<Float>getValue());
	}

	@Test
	void testDouble() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/Double.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_DOUBLE, tokens.get(0).getType());
		assertEquals(123.456, tokens.get(0).<Double>getValue());

		assertEquals(TokenType.LITERAL_DOUBLE, tokens.get(1).getType());
		assertEquals(12.3356, tokens.get(1).<Double>getValue());

		assertEquals(TokenType.LITERAL_DOUBLE, tokens.get(3).getType());
		assertEquals(8510.1457, tokens.get(3).<Double>getValue());

		assertEquals(TokenType.LITERAL_DOUBLE, tokens.get(5).getType());
		assertEquals(55., tokens.get(5).<Double>getValue());

		assertEquals(TokenType.LITERAL_DOUBLE, tokens.get(7).getType());
		assertEquals(12.0, tokens.get(7).<Double>getValue());
	}

	@Test
	void testString() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/String.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_STRING, tokens.get(0).getType());
		assertEquals("Hello World", tokens.get(0).<String>getValue());

		assertEquals(TokenType.LITERAL_STRING, tokens.get(1).getType());
		assertEquals("Test", tokens.get(1).<String>getValue());

		assertEquals(TokenType.LITERAL_STRING, tokens.get(3).getType());
		assertEquals("bye World", tokens.get(3).<String>getValue());
	}
}
