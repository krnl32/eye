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
	private final Map<Character, TokenType> symbolTokenTypes;

	// Track Source
	private int startLine, currentLine;
	private int startColumn, currentColumn;
	private int startIndex, currentIndex;

	public Lexer(String source) {
		this.source = source.replace("\r\n", "\n").replace("\r", "\n");
		this.tokens = new ArrayList<>();

		this.symbolTokenTypes = Map.of(
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
			case EOF:
				break;

				// Decimal: Integer, Float Literals
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

				// Strings
			case '"':
				token = makeStringToken('"', '"');
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

				// Operators
			case '+':
			case '-':
			case '*':
			case '%':
			case '=':
			case '<':
			case '>':
			case '!':
			case '&':
			case '|':
			case '^':
			case '~':
			case '[':
			case '(':
			case '?':
			case ',':
			case '.':
				token = makeOperatorToken();
				break;

				// Slash Operator
			case '/':
				token = handleSlashOperator();
				break;

			default:
				token = makeSpecialToken();

				if (token == null)
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

	private Token makeEndOfFileToken() {
		SourceSpan span = makeSourceSpan();
		return new Token(TokenType.END_OF_FILE, null, span);
	}

	private Token makeNumberToken() {
		boolean floatingPointNumber = false;
		TokenType tokenType = TokenType.LITERAL_INT32; // Default Integer Type
		Object tokenValue;

		// Parse digits and check for floating point
		for (char ch = peekChar(); Character.isDigit(ch); ch = peekChar()) {
			nextChar();

			// Catch Floating Point Numbers (e.g. 12.31)
			if (!floatingPointNumber && peekChar() == '.') {
				nextChar();
				floatingPointNumber = true;
				tokenType = TokenType.LITERAL_FLOAT64; // Default Floating-Point Type
			}
		}

		int sourceIndexBeforeSuffix = currentIndex;

		// Check for Number Suffix
		char charAfterNumber = peekChar();

		// If Number is FLOAT, Check for Float32 Suffix 'f/F' Suffix, (e.g. 1234.15f)
		if (floatingPointNumber && (charAfterNumber == 'f' || charAfterNumber == 'F')) {
			nextChar();
			tokenType = TokenType.LITERAL_FLOAT32;
		}

		// If Number is INTEGER, Check for Unsigned Suffix 'u/U', (e.g. 123u)
		if (!floatingPointNumber && (charAfterNumber == 'u' || charAfterNumber == 'U')) {
			nextChar();
			tokenType = TokenType.LITERAL_UINT32;
		}

		// Tokenize Numbers String
		String numbers = source.substring(startIndex, sourceIndexBeforeSuffix);

		tokenValue = switch (tokenType) {
			case LITERAL_INT32 -> Integer.parseInt(numbers);
			case LITERAL_UINT32 -> Integer.parseUnsignedInt(numbers);
			case LITERAL_FLOAT32 -> Float.parseFloat(numbers);
			case LITERAL_FLOAT64 -> Double.parseDouble(numbers);
			default -> null;
		};

		SourceSpan span = makeSourceSpan();
		return new Token(tokenType, tokenValue, span);
	}

	private Token makeNumberBaseToken() {
		// If Last Token is not "0", Then Make Identifier Instead
		Token lastToken = !tokens.isEmpty() ? tokens.getLast() : null;

		boolean isLastTokenZero = lastToken != null &&
			lastToken.getType() == TokenType.LITERAL_INT32 &&
			lastToken.<Integer>getValue() == 0;

		if (!isLastTokenZero) {
			return makeIdentifierToken();
		}

		// Make Other Base Numbers (Hex->0x1234, Binary->0b1111)
		// Remove the '0' from Token List
		tokens.removeLast();

		char baseType = peekChar();

		return switch (baseType) {
			case 'x' -> makeNumberBaseToken(16); // Hex
			case 'b' -> makeNumberBaseToken(2); // Binary
			default -> null;
		};
	}

	private Token makeNumberBaseToken(int radix) {
		TokenType tokenType = TokenType.LITERAL_INT32; // Default Integer Type
		Object tokenValue;

		// Skip 'x' or 'b'
		nextChar();

		// Parse digits
		for (char ch = peekChar(); Character.digit(ch, radix) != -1; ch = peekChar()) {
			nextChar();
		}

		int sourceIndexBeforeSuffix = currentIndex;

		// Check for Number Suffix
		char charAfterNumber = peekChar();

		// Check for Unsigned Suffix 'u/U', (e.g. 0x1234u)
		if (charAfterNumber == 'u' || charAfterNumber == 'U') {
			nextChar();
			tokenType = TokenType.LITERAL_UINT32;
		}

		String numbers = source.substring(startIndex + 1, sourceIndexBeforeSuffix);

		tokenValue = switch (tokenType) {
			case LITERAL_INT32 -> Integer.parseInt(numbers, radix);
			case LITERAL_UINT32 -> Integer.parseUnsignedInt(numbers, radix);
			default -> null;
		};

		// startIndex - 1 to include the '0'
		SourceSpan span = new SourceSpan(startLine, startColumn, startIndex - 1, currentIndex);
		return new Token(tokenType, tokenValue, span);
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
		return new Token(TokenType.LITERAL_STR8, str, span);
	}

	private Token makeSymbolToken() {
		char symbol = nextChar();
		TokenType symbolType = symbolTokenTypes.get(symbol);

		SourceSpan span = makeSourceSpan();
		return new Token(symbolType, null, span);
	}

	private Token makeOperatorToken() {
		boolean multiCharOperator = false;
		char ch = nextChar();

		String opStr = String.valueOf(ch);
		TokenType opType = LexerUtility.getOperatorTokenType(opStr);

		if (opType == null) {
			throw new UnexpectedTokenException(opStr, makeSourceSpan());
		}

		// if MultiCharOperator, LookAhead -> if Operator, then Add it to opStr.
		// A Special Case for '*' and '*=', Since '*' is not considered a Multi Char Operator by Specs
		if (opType == TokenType.OPERATOR_BINARY_STAR && peekChar() == '=') {
			char eq = nextChar();
			opStr += eq;
			opType = LexerUtility.getOperatorTokenType(opStr);
			multiCharOperator = true;
		}  else if (LexerUtility.isMultiCharOperator(opType)) {
			ch = peekChar();

			if (LexerUtility.isOperator(ch)) {
				opStr += ch;
				opType = LexerUtility.getOperatorTokenType(opStr);
				multiCharOperator = true;
				nextChar();
			}
		}

		// Another Special Case for Bitwise Shift Operators '<<', '>>' to handle '>>=' or '<<=', since by default Lexer is only capable of tokenizing 2 Operators at a time.
		boolean isBitwiseShiftOperator = (opType == TokenType.OPERATOR_BITWISE_LEFT_SHIFT ||
			opType == TokenType.OPERATOR_BITWISE_RIGHT_SHIFT);

		if (isBitwiseShiftOperator && peekChar() == '=') {
			char eq = nextChar();
			opStr += eq;
			opType = LexerUtility.getOperatorTokenType(opStr);
		}

		// If Multi Char Operator but not Valid: e.g. '+(' -> Put All Back Except first One "+"
		if (multiCharOperator && !LexerUtility.isOperator(opStr)) {
			for (int i = opStr.length() - 1; i >= 1; i--) {
				ungetChar();
			}

			opStr = opStr.substring(0, 1);
			opType = LexerUtility.getOperatorTokenType(opStr);
		}

		// If Some Weird Unsupported Operator
		if (!multiCharOperator && !LexerUtility.isOperator(opStr)) {
			throw new UnexpectedTokenException(opStr, makeSourceSpan());
		}

		SourceSpan span = makeSourceSpan();
		return new Token(opType, null, span);
	}

	private Token handleSlashOperator() {
		nextChar(); // Skip '/'

		char ch = peekChar();

		// Handle Comments
		if (ch == '/') {
			nextChar(); // Skip 2nd '/'
			return makeSingleLineCommentToken();
		} else if(ch == '*') {
			nextChar(); // Skip '*'
			return makeMultiLineCommentToken();
		}

		// If Not Comment, Math Operator '/'
		ungetChar();
		return makeOperatorToken();
	}

	private Token makeSingleLineCommentToken() {
		for (char ch = peekChar(); (ch != '\n' && ch != EOF); ch = peekChar()) {
			nextChar();
		}

		String comment = source.substring(startIndex + 2, currentIndex);
		SourceSpan span = makeSourceSpan();
		return new Token(TokenType.COMMENT, comment, span);
	}

	private Token makeMultiLineCommentToken() {
		while (true) {
			char ch;

			for (ch = peekChar(); (ch != '*' && ch != EOF); ch = peekChar()) {
				nextChar();
			}

			if (ch == EOF) {
				throw new UnexpectedTokenException(String.valueOf(ch), makeSourceSpan());
			}

			if (ch == '*') {
				nextChar();

				// if '/' then end of MultiLine Comment -> /* Comment */
				if (peekChar() == '/') {
					nextChar();
					break;
				}
			}
		}

		String comment = source.substring(startIndex + 2, currentIndex - 2);
		SourceSpan span = makeSourceSpan();
		return new Token(TokenType.COMMENT, comment, span);
	}

	private Token makeSpecialToken() {
		char ch = peekChar();

		if (Character.isLetter(ch) || ch == '_') {
			return makeIdentifierToken();
		}

		return null;
	}

	private Token makeIdentifierToken() {
		for (char ch = peekChar(); Character.isLetterOrDigit(ch) || ch == '_'; ch = peekChar()) {
			nextChar();
		}

		SourceSpan span = makeSourceSpan();
		String identifier = source.substring(startIndex, currentIndex);

		// Handle Keywords -> Add Special Case for Literals: true, false, null
		if (identifier.equals("true") || identifier.equals("false")) {
			return new Token(TokenType.LITERAL_BOOL8, identifier.equals("true"), span);
		} else if(identifier.equals("null")) {
			return new Token(TokenType.LITERAL_NULL, null, span);
		} else if (LexerUtility.isKeyword(identifier)) {
			TokenType type = LexerUtility.getKeywordTokenType(identifier);
			return new Token(type, null, span);
		}

		return new Token(TokenType.IDENTIFIER, identifier, span);
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

	private void ungetChar() {
		if (currentIndex == 0) {
			return;
		}

		currentIndex--;
		currentColumn--;

		char ch = source.charAt(currentIndex);

		if (ch == '\n') {
			currentLine--;

			int lineStart = source.lastIndexOf('\n', currentIndex - 1);

			if (lineStart == -1) {
				currentColumn = currentIndex + 1;
			} else {
				currentColumn = currentIndex - lineStart;
			}
		}
	}

	private char peekChar() {
		if (isAtEnd()) {
			return EOF;
		}

		return source.charAt(currentIndex);
	}

	private char lookahead(int idx) {
		if (isAtEnd() || (currentIndex + idx) >= source.length()) {
			return EOF;
		}

		return source.charAt(currentIndex + idx);
	}

	private boolean isAtEnd() {
		return currentIndex >= source.length();
	}
}
