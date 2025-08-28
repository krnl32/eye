package com.krnl32.eye.parser.lexer.literal;

import com.krnl32.eye.common.utility.FileIO;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerLiteralNumberTest {
	@Test
	void testSignedInteger() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/integer.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_INT32, tokens.get(0).getType());
		assertEquals(123, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INT32, tokens.get(1).getType());
		assertEquals(5678, tokens.get(1).<Integer>getValue());

//		assertEquals(TokenType.LITERAL_INT64, tokens.get(4).getType());
//		assertEquals(1234, tokens.get(4).<Integer>getValue());
//
//		assertEquals(TokenType.LITERAL_INT64, tokens.get(5).getType());
//		assertEquals(12345, tokens.get(5).<Integer>getValue());
//
//		assertEquals(TokenType.LITERAL_INT128, tokens.get(7).getType());
//		assertEquals(123456, tokens.get(7).<Integer>getValue());
//
//		assertEquals(TokenType.LITERAL_INT128, tokens.get(8).getType());
//		assertEquals(1234567, tokens.get(8).<Integer>getValue());
	}
}
