package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.FileIO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerSymbolTest {
	@Test
	void testSymbols() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/symbol/Symbols.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.SYMBOL_RIGHT_PARENTHESIS, tokens.get(0).getType());
		assertEquals(TokenType.SYMBOL_RIGHT_BRACKET, tokens.get(2).getType());
		assertEquals(TokenType.SYMBOL_LEFT_BRACE, tokens.get(4).getType());
		assertEquals(TokenType.SYMBOL_RIGHT_BRACE, tokens.get(6).getType());
		assertEquals(TokenType.SYMBOL_COLON, tokens.get(8).getType());
		assertEquals(TokenType.SYMBOL_SEMI_COLON, tokens.get(10).getType());
		assertEquals(TokenType.SYMBOL_BACKSLASH, tokens.get(12).getType());
	}

	@Test
	void testSymbols2() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/symbol/Symbols2.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.SYMBOL_LEFT_BRACE, tokens.get(0).getType());
		assertEquals(TokenType.SYMBOL_SEMI_COLON, tokens.get(2).getType());
		assertEquals(TokenType.SYMBOL_RIGHT_PARENTHESIS, tokens.get(3).getType());
		assertEquals(TokenType.SYMBOL_COLON, tokens.get(5).getType());
		assertEquals(TokenType.SYMBOL_RIGHT_BRACKET, tokens.get(6).getType());
		assertEquals(TokenType.SYMBOL_SEMI_COLON, tokens.get(7).getType());
		assertEquals(TokenType.SYMBOL_RIGHT_BRACE, tokens.get(9).getType());
	}
}
