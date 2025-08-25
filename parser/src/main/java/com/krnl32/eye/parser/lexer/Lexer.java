package com.krnl32.eye.parser.lexer;

import com.krnl32.eye.common.utility.SourceSpan;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
	private final String source;

	// Track Source
	private int startLine, currentLine;
	private int startColumn, currentColumn;
	private int startIndex, currentIndex;

	public Lexer(String source) {
		this.source = source;
		this.startLine = 1;
		this.currentLine = 1;
		this.startColumn = 1;
		this.currentColumn = 1;
		this.startIndex = 0;
		this.currentIndex = 0;
	}

	public List<Token> tokenize() {
		List<Token> tokens = new ArrayList<>();

		Token token = nextToken();

		while (token != null) {
			tokens.add(token);
			token = nextToken();
		}

		tokens.add(makeEndOfFileToken());
		return tokens;
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

				// New Line
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

			default:
				SourceSpan span = makeSourceSpan();
				String errorMessage = "SyntaxError: Unexpected token '" + ch + "'"
					+ "\n\tat line " + span.getLine()
					+ ", column " + span.getColumn();
				throw new RuntimeException(errorMessage);
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
			return '\0';
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
			return '\0';
		}

		return source.charAt(currentIndex);
	}

	private boolean isAtEnd() {
		return currentIndex >= source.length();
	}
}
