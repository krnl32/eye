package com.krnl32.eye.parser.lexer.literal;

import com.krnl32.eye.common.utility.FileIO;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
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

		assertEquals(TokenType.LITERAL_INT64, tokens.get(3).getType());
		assertEquals(1234L, tokens.get(3).<Long>getValue());

		assertEquals(TokenType.LITERAL_INT64, tokens.get(4).getType());
		assertEquals(12345L, tokens.get(4).<Long>getValue());

		assertEquals(TokenType.LITERAL_INT128, tokens.get(6).getType());
		assertEquals(new BigInteger("123456"), tokens.get(6).<BigInteger>getValue());

		assertEquals(TokenType.LITERAL_INT128, tokens.get(7).getType());
		assertEquals(new BigInteger("1234567"), tokens.get(7).<BigInteger>getValue());
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

		assertEquals(TokenType.LITERAL_UINT64, tokens.get(3).getType());
		assertEquals(1234L, tokens.get(3).<Long>getValue());

		assertEquals(TokenType.LITERAL_UINT64, tokens.get(4).getType());
		assertEquals(12345L, tokens.get(4).<Long>getValue());

		assertEquals(TokenType.LITERAL_UINT128, tokens.get(6).getType());
		assertEquals(new BigInteger("123456"), tokens.get(6).<BigInteger>getValue());

		assertEquals(TokenType.LITERAL_UINT128, tokens.get(7).getType());
		assertEquals(new BigInteger("1234567"), tokens.get(7).<BigInteger>getValue());
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

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(2).getType());
		assertEquals(123.4567F, tokens.get(2).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT128, tokens.get(4).getType());
		assertEquals(new BigDecimal("8510.1457"), tokens.get(4).<BigDecimal>getValue());

		assertEquals(TokenType.LITERAL_FLOAT128, tokens.get(5).getType());
		assertEquals(new BigDecimal("8510.14579"), tokens.get(5).<BigDecimal>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(7).getType());
		assertEquals(55.f, tokens.get(7).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(8).getType());
		assertEquals(65.F, tokens.get(8).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT32, tokens.get(10).getType());
		assertEquals(12.0f, tokens.get(10).<Float>getValue());

		assertEquals(TokenType.LITERAL_FLOAT128, tokens.get(11).getType());
		assertEquals(new BigDecimal("12.0"), tokens.get(11).<BigDecimal>getValue());

		assertEquals(TokenType.LITERAL_FLOAT64, tokens.get(13).getType());
		assertEquals(12.13, tokens.get(13).<Double>getValue());

		assertEquals(TokenType.LITERAL_FLOAT64, tokens.get(14).getType());
		assertEquals(15., tokens.get(14).<Double>getValue());

		assertEquals(TokenType.LITERAL_FLOAT128, tokens.get(15).getType());
		assertEquals(new BigDecimal("11."), tokens.get(15).<BigDecimal>getValue());
	}
}
