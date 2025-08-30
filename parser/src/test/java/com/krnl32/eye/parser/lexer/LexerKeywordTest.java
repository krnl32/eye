package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.FileIO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerKeywordTest {
	@Test
	void testIdentifier() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/keyword/identifier.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.IDENTIFIER, tokens.get(0).getType());
		assertEquals("test2", tokens.get(0).<String>getValue());

		assertEquals(TokenType.IDENTIFIER, tokens.get(2).getType());
		assertEquals("num", tokens.get(2).<String>getValue());

		assertEquals(TokenType.IDENTIFIER, tokens.get(4).getType());
		assertEquals("hello", tokens.get(4).<String>getValue());

		assertEquals(TokenType.IDENTIFIER, tokens.get(5).getType());
		assertEquals("world", tokens.get(5).<String>getValue());

		assertEquals(TokenType.IDENTIFIER, tokens.get(7).getType());
		assertEquals("fake", tokens.get(7).<String>getValue());

		assertEquals(TokenType.IDENTIFIER, tokens.get(9).getType());
		assertEquals("mem", tokens.get(9).<String>getValue());

		assertEquals(TokenType.IDENTIFIER, tokens.get(11).getType());
		assertEquals("_helloVar1", tokens.get(11).<String>getValue());
	}

	@Test
	void testDatatype() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/keyword/datatype.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.KEYWORD_DATATYPE_INT32_T, tokens.get(0).getType());
		assertEquals(TokenType.KEYWORD_DATATYPE_UINT32_T, tokens.get(2).getType());
		assertEquals(TokenType.KEYWORD_DATATYPE_FLOAT32_T, tokens.get(4).getType());
		assertEquals(TokenType.KEYWORD_DATATYPE_FLOAT64_T, tokens.get(6).getType());
		assertEquals(TokenType.KEYWORD_DATATYPE_CHAR8_T, tokens.get(8).getType());
		assertEquals(TokenType.KEYWORD_DATATYPE_STR8_T, tokens.get(10).getType());
		assertEquals(TokenType.KEYWORD_DATATYPE_BOOL8_T, tokens.get(12).getType());
		assertEquals(TokenType.KEYWORD_DATATYPE_VOID, tokens.get(14).getType());
	}

	@Test
	void testTypeQualifier() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/keyword/type_qualifier.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.KEYWORD_TYPE_QUALIFIER_CONST, tokens.get(0).getType());
		assertEquals(TokenType.KEYWORD_TYPE_QUALIFIER_CONST, tokens.get(1).getType());
		assertEquals(TokenType.KEYWORD_TYPE_QUALIFIER_CONST, tokens.get(3).getType());
	}

	@Test
	void testControl() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/keyword/control.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.KEYWORD_CONTROL_IF, tokens.get(0).getType());
		assertEquals(TokenType.KEYWORD_CONTROL_ELSE, tokens.get(2).getType());
		assertEquals(TokenType.KEYWORD_CONTROL_IF, tokens.get(3).getType());
		assertEquals(TokenType.KEYWORD_CONTROL_ELSE, tokens.get(5).getType());
	}

	@Test
	void testIteration() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/keyword/iteration.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.KEYWORD_ITERATION_WHILE, tokens.get(0).getType());
		assertEquals(TokenType.KEYWORD_ITERATION_DO, tokens.get(2).getType());
		assertEquals(TokenType.KEYWORD_ITERATION_FOR, tokens.get(4).getType());
		assertEquals(TokenType.KEYWORD_ITERATION_CONTINUE, tokens.get(6).getType());
		assertEquals(TokenType.KEYWORD_ITERATION_BREAK, tokens.get(8).getType());
	}

	@Test
	void testFunction() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/keyword/function.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.KEYWORD_FUNCTION, tokens.get(0).getType());
		assertEquals(TokenType.KEYWORD_RETURN, tokens.get(2).getType());
	}
}
