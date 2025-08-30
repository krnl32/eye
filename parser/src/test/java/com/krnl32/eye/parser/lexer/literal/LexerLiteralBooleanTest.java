package com.krnl32.eye.parser.lexer.literal;

import com.krnl32.eye.common.utility.FileIO;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerLiteralBooleanTest {
	@Test
	void testBool8() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/boolean.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_BOOL8, tokens.get(0).getType());
		assertEquals(true, tokens.get(0).<Boolean>getValue());

		assertEquals(TokenType.LITERAL_BOOL8, tokens.get(1).getType());
		assertEquals(false, tokens.get(1).<Boolean>getValue());

		assertEquals(TokenType.LITERAL_BOOL8, tokens.get(3).getType());
		assertEquals(false, tokens.get(3).<Boolean>getValue());
	}
}
