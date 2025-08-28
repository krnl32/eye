package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.FileIO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerLiteralTest {

//
//	@Test
//	void testIntegerHex() throws IOException {
//		String source = FileIO.readResourceFileContent("lexer/literal/IntegerHex.eye");
//
//		Lexer lexer = new Lexer(source);
//		List<Token> tokens = lexer.tokenize();
//
//		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(0).getType());
//		assertEquals(4660, tokens.get(0).<Integer>getValue());
//
//		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(1).getType());
//		assertEquals(1245, tokens.get(1).<Integer>getValue());
//
//		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(3).getType());
//		assertEquals(5126788, tokens.get(3).<Integer>getValue());
//	}
//
//	@Test
//	void testIntegerBinary() throws IOException {
//		String source = FileIO.readResourceFileContent("lexer/literal/IntegerBinary.eye");
//
//		Lexer lexer = new Lexer(source);
//		List<Token> tokens = lexer.tokenize();
//
//		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(0).getType());
//		assertEquals(6, tokens.get(0).<Integer>getValue());
//
//		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(1).getType());
//		assertEquals(51256, tokens.get(1).<Integer>getValue());
//
//		assertEquals(TokenType.LITERAL_INTEGER, tokens.get(3).getType());
//		assertEquals(1256, tokens.get(3).<Integer>getValue());
//	}

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
