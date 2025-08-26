package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.common.exceptions.UnexpectedTokenException;
import com.krnl32.eye.common.utility.SourceSpan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Lexer {
	private static final char EOF = '\0';

	private final String source;
	private final List<Token> tokens;

	// Language Specific
	private final Map<Character, TokenType> symbolTokens;

	// Track Source
	private int startLine, currentLine;
	private int startColumn, currentColumn;
	private int startIndex, currentIndex;

	public Lexer(String source) {
		this.source = source;
		this.tokens = new ArrayList<>();

		this.symbolTokens = Map.of(
			')', TokenType.SYMBOL_RIGHT_PARENTHESIS,
			']', TokenType.SYMBOL_RIGHT_BRACKET,
			'{', TokenType.SYMBOL_LEFT_BRACE,
			'}', TokenType.SYMBOL_RIGHT_BRACE,
			':', TokenType.SYMBOL_COLON,
			';', TokenType.SYMBOL_SEMI_COLON,
			'\\', TokenType.SYMBOL_BACKSLASH
		);

		this.startLine = 1;
		this.currentLine = 1;
		this.startColumn = 1;
		this.currentColumn = 1;
		this.startIndex = 0;
		this.currentIndex = 0;
	}

	public List<Token> tokenize() {
		try {
			Token token = nextToken();

			while (token != null) {
				tokens.add(token);
				token = nextToken();
			}
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return null;
		}

		tokens.add(makeEndOfFileToken());
		return Collections.unmodifiableList(tokens);
	}

	private Token nextToken() {
		startColumn = currentColumn;
		startLine = currentLine;
		startIndex = currentIndex;

		Token token = null;

		char ch = peekChar();

		switch (ch) {
				// Whitespace
			case ' ':
			case '\t':
				token = handleWhitespace();
				break;

				// Newline
			case '\n':
				token = handleNewline();
				break;

				// EOF
			case '\0':
				break;

				// Decimal: Integer, Float, Double Literals
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				token = makeNumberToken();
				break;

				// Hexadecimal: 0x12AB, Binary: 0b1111
			case 'x':
			case 'b':
				token = makeNumberBaseToken();
				break;

				// Symbols
			case ')':
			case ']':
			case '{':
			case '}':
			case ':':
			case ';':
			case '\\':
				token = makeSymbolToken();
				break;

				// Strings
			case '"':
				token = makeStringToken('"', '"');
				break;

			default:
				throw new UnexpectedTokenException(Character.toString(ch), makeSourceSpan());
		}

		return token;
	}

	// Make Tokens
	private Token handleWhitespace() {
		nextChar();
		return nextToken();
	}

	private Token handleNewline() {
		nextChar();
		SourceSpan span = makeSourceSpan();
		return new Token(TokenType.NEWLINE, null, span);
	}

	private Token makeNumberToken() {
		boolean decimalNumber = false;

		TokenType tokenType = TokenType.LITERAL_INTEGER;
		Object value = null;

		for (char ch = peekChar(); Character.isDigit(ch); ch = peekChar()) {
			nextChar();

			if (!decimalNumber && peekChar() == '.') {
				nextChar();
				decimalNumber = true;
				tokenType = TokenType.LITERAL_DOUBLE;
			}

			if (decimalNumber && (peekChar() == 'f' || peekChar() == 'F')) {
				nextChar();
				tokenType = TokenType.LITERAL_FLOAT;
			}
		}

		String numbers = source.substring(startIndex, currentIndex);

		if (tokenType == TokenType.LITERAL_INTEGER) {
			value = Integer.parseInt(numbers);
		} else if(tokenType == TokenType.LITERAL_FLOAT) {
			value = Float.parseFloat(numbers);
		} else if(tokenType == TokenType.LITERAL_DOUBLE) {
			value = Double.parseDouble(numbers);
		}

		SourceSpan span = makeSourceSpan();
		return new Token(tokenType, value, span);
	}

	private Token makeNumberBaseToken() {
		// If Last Token is not "0", Then Make Identifier Instead
		Token lastToken = tokens.getLast();

		boolean isLastTokenZero = lastToken != null &&
			lastToken.getType() == TokenType.LITERAL_INTEGER &&
			lastToken.<Integer>getValue() == 0;

		if (!isLastTokenZero) {
			// return makeIdentifierToken();
		}

		// Make Other Base Numbers (Hex->0x1234, Binary->0b1111)
		// Remove the '0' from Token List
		tokens.removeLast();

		char baseType = peekChar();

		return switch (baseType) {
			case 'x' -> makeNumberBaseToken(16);
			case 'b' -> makeNumberBaseToken(2);
			default -> null;
		};
	}

	private Token makeNumberBaseToken(int radix) {
		// Skip 'x' or 'b'
		nextChar();

		for (char ch = peekChar(); Character.digit(ch, radix) != -1; ch = peekChar()) {
			nextChar();
		}

		String numbers = source.substring(startIndex + 1, currentIndex);
		int value = Integer.parseInt(numbers, radix);

		SourceSpan span = makeSourceSpan();
		return new Token(TokenType.LITERAL_INTEGER, value, span);
	}

	private Token makeSymbolToken() {
		char symbol = nextChar();
		TokenType symbolType = symbolTokens.get(symbol);

		SourceSpan span = makeSourceSpan();
		return new Token(symbolType, null, span);
	}

	private Token makeStringToken(char sdelim, char edelim) {
		// Skip Initial '"'
		if (nextChar() != sdelim) {
			throw new UnexpectedTokenException(Character.toString(sdelim), makeSourceSpan());
		}

		for (char ch = nextChar(); ch != edelim && ch != EOF; ch = nextChar());

		// Exclude Delims
		String str = source.substring(startIndex + 1, currentIndex - 1);

		SourceSpan span = makeSourceSpan();
		return new Token(TokenType.LITERAL_STRING, str, span);
	}

	private Token makeEndOfFileToken() {
		SourceSpan span = makeSourceSpan();
		return new Token(TokenType.END_OF_FILE, null, span);
	}

	SourceSpan makeSourceSpan() {
		return new SourceSpan(startLine, startColumn, startIndex, currentIndex);
	}

	// Source Manipulation
	private char nextChar() {
		if (isAtEnd()) {
			return EOF;
		}

		char ch = source.charAt(currentIndex);

		currentIndex++;
		currentColumn++;

		if (ch == '\n') {
			currentLine++;
			currentColumn = 1;
		}

		return ch;
	}

	private char peekChar() {
		if (isAtEnd()) {
			return EOF;
		}

		return source.charAt(currentIndex);
	}

	private boolean isAtEnd() {
		return currentIndex >= source.length();
	}
}
