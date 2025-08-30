package com.krnl32.eye.parser.lexer.literal;

import com.krnl32.eye.common.utility.FileIO;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerLiteralStringTest {
	@Test
	void testString8() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/string.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_STR8, tokens.get(0).getType());
		assertEquals("Hello World", tokens.get(0).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(1).getType());
		assertEquals("Test", tokens.get(1).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(3).getType());
		assertEquals("bye World", tokens.get(3).<String>getValue());
	}

	@Test
	void testEscapeCharacter() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/string_escapecharacter.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_STR8, tokens.get(0).getType());
		assertEquals("Hello\\World", tokens.get(0).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(2).getType());
		assertEquals("Hello\'World", tokens.get(2).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(4).getType());
		assertEquals("Hello\"World", tokens.get(4).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(6).getType());
		assertEquals("Hello\nWorld", tokens.get(6).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(8).getType());
		assertEquals("Hello\rWorld", tokens.get(8).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(10).getType());
		assertEquals("Hello\tWorld", tokens.get(10).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(12).getType());
		assertEquals("Hello\bWorld", tokens.get(12).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(14).getType());
		assertEquals("Hello\fWorld", tokens.get(14).<String>getValue());

		assertEquals(TokenType.LITERAL_STR8, tokens.get(16).getType());
		assertEquals("Hello\u000BWorld", tokens.get(16).<String>getValue());
	}
}
