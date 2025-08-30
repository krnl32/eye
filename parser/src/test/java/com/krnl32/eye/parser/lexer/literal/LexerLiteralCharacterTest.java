package com.krnl32.eye.parser.lexer.literal;

import com.krnl32.eye.common.exceptions.UnexpectedTokenException;
import com.krnl32.eye.common.utility.FileIO;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LexerLiteralCharacterTest {
	@Test
	void testChar8() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/character.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(0).getType());
		assertEquals('H', tokens.get(0).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(1).getType());
		assertEquals('T', tokens.get(1).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(3).getType());
		assertEquals('w', tokens.get(3).<Character>getValue());
	}

	@Test
	void testEscapeCharacter() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/character_escapecharacter.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(0).getType());
		assertEquals('\\', tokens.get(0).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(2).getType());
		assertEquals('\'', tokens.get(2).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(4).getType());
		assertEquals('\"', tokens.get(4).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(6).getType());
		assertEquals('\n', tokens.get(6).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(8).getType());
		assertEquals('\r', tokens.get(8).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(10).getType());
		assertEquals('\t', tokens.get(10).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(12).getType());
		assertEquals('\b', tokens.get(12).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(14).getType());
		assertEquals('\f', tokens.get(14).<Character>getValue());

		assertEquals(TokenType.LITERAL_CHAR8, tokens.get(16).getType());
		assertEquals('\u000B', tokens.get(16).<Character>getValue());

		// No Closing Delimter
		assertThrows(UnexpectedTokenException.class, () -> {
			Lexer lex = new Lexer("\'");
			lex.tokenize();
		});

		// Empty Character
		assertThrows(UnexpectedTokenException.class, () -> {
			Lexer lex = new Lexer("\'\'");
			lex.tokenize();
		});
	}
}
