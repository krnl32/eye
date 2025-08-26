package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.FileIO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LexerTest {

	@Test
	void testLiteralIntegerDecimal() throws IOException {
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
}
