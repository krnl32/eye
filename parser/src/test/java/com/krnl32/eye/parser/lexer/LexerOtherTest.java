package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.FileIO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerOtherTest {
	@Test
	void testNewline() {
		Lexer lexer = new Lexer("\n \n\n \n");
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.NEWLINE, tokens.get(0).getType());
		assertEquals(TokenType.NEWLINE, tokens.get(1).getType());
		assertEquals(TokenType.NEWLINE, tokens.get(2).getType());
		assertEquals(TokenType.NEWLINE, tokens.get(3).getType());
	}

	@Test
	void testEndOfFile() {
		Lexer lexer = new Lexer("\n \n\n \n");
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.END_OF_FILE, tokens.get(4).getType());
	}
}
