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
		String source = FileIO.readResourceFileContent("lexer/literal/integer_signed.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_INT32, tokens.get(0).getType());
		assertEquals(123, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INT32, tokens.get(1).getType());
		assertEquals(5678, tokens.get(1).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INT32, tokens.get(3).getType());
		assertEquals(9851, tokens.get(3).<Integer>getValue());
	}

	@Test
	void testUnsignedInteger() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/integer_unsigned.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_UINT32, tokens.get(0).getType());
		assertEquals(123, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_UINT32, tokens.get(1).getType());
		assertEquals(5678, tokens.get(1).<Integer>getValue());

		assertEquals(TokenType.LITERAL_UINT32, tokens.get(3).getType());
		assertEquals(1234, tokens.get(3).<Integer>getValue());
	}

	@Test
	void testFloat() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/float.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_FLOAT64, tokens.get(0).getType());
		assertEquals(12.3356, tokens.get(0).<Double>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(1).getType());
		assertEquals(123.456f, tokens.get(1).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(3).getType());
		assertEquals(123.4567F, tokens.get(3).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT64, tokens.get(5).getType());
		assertEquals(8510., tokens.get(5).<Double>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(7).getType());
		assertEquals(55.f, tokens.get(7).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(8).getType());
		assertEquals(65.F, tokens.get(8).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(10).getType());
		assertEquals(12.0f, tokens.get(10).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT64, tokens.get(12).getType());
		assertEquals(12.13, tokens.get(12).<Double>getValue());
	}

	@Test
	void testIntegerHex() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/integer_hex.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_INT32, tokens.get(0).getType());
		assertEquals(4660, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_UINT32, tokens.get(1).getType());
		assertEquals(2805, tokens.get(1).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INT32, tokens.get(3).getType());
		assertEquals(1245, tokens.get(3).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INT32, tokens.get(5).getType());
		assertEquals(5126788, tokens.get(5).<Integer>getValue());

		assertEquals(TokenType.LITERAL_UINT32, tokens.get(7).getType());
		assertEquals(17768, tokens.get(7).<Integer>getValue());
	}

	@Test
	void testIntegerBinary() throws IOException {
		String source = FileIO.readResourceFileContent("lexer/literal/integer_binary.eye");

		Lexer lexer = new Lexer(source);
		List<Token> tokens = lexer.tokenize();

		assertEquals(TokenType.LITERAL_INT32, tokens.get(0).getType());
		assertEquals(6, tokens.get(0).<Integer>getValue());

		assertEquals(TokenType.LITERAL_UINT32, tokens.get(1).getType());
		assertEquals(51256, tokens.get(1).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INT32, tokens.get(3).getType());
		assertEquals(1592, tokens.get(3).<Integer>getValue());

		assertEquals(TokenType.LITERAL_UINT32, tokens.get(5).getType());
		assertEquals(1256, tokens.get(5).<Integer>getValue());

		assertEquals(TokenType.LITERAL_INT32, tokens.get(7).getType());
		assertEquals(49, tokens.get(7).<Integer>getValue());
	}
}
