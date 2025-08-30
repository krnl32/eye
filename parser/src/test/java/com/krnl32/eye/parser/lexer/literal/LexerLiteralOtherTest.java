package com.krnl32.eye.parser.lexer.literal;

import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerLiteralOtherTest {
	@Test
	void testNull() throws IOException {
		String source = "null null\nnull";

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_NULL, tokens.get(0).getType());
		assertEquals(TokenType.LITERAL_NULL, tokens.get(1).getType());
		assertEquals(TokenType.LITERAL_NULL, tokens.get(3).getType());
	}
}
