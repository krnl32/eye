package com.krnl32.eye.parser.lexer;

import java.util.Map;
import java.util.Set;

public class LexerUtility {
	private static final Map<String, TokenType> OPERATOR_TOKEN_TYPES = Map.ofEntries(
		Map.entry("+", TokenType.OPERATOR_BINARY_PLUS),
		Map.entry("-", TokenType.OPERATOR_BINARY_MINUS),
		Map.entry("*", TokenType.OPERATOR_BINARY_STAR),
		Map.entry("/", TokenType.OPERATOR_BINARY_SLASH),
		Map.entry("%", TokenType.OPERATOR_BINARY_MODULO),
		Map.entry("++", TokenType.OPERATOR_ARITHMETIC_INCREMENT),
		Map.entry("--", TokenType.OPERATOR_ARITHMETIC_DECREMENT),
		Map.entry("=", TokenType.OPERATOR_ASSIGNMENT),
		Map.entry("+=", TokenType.OPERATOR_ASSIGNMENT_PLUS),
		Map.entry("-=", TokenType.OPERATOR_ASSIGNMENT_MINUS),
		Map.entry("*=", TokenType.OPERATOR_ASSIGNMENT_STAR),
		Map.entry("/=", TokenType.OPERATOR_ASSIGNMENT_SLASH),
		Map.entry("%=", TokenType.OPERATOR_ASSIGNMENT_MODULO),
		Map.entry("&=", TokenType.OPERATOR_ASSIGNMENT_BITWISE_AND),
		Map.entry("|=", TokenType.OPERATOR_ASSIGNMENT_BITWISE_OR),
		Map.entry("^=", TokenType.OPERATOR_ASSIGNMENT_BITWISE_XOR),
		Map.entry("<<=", TokenType.OPERATOR_ASSIGNMENT_BITWISE_LEFT_SHIFT),
		Map.entry(">>=", TokenType.OPERATOR_ASSIGNMENT_BITWISE_RIGHT_SHIFT),
		Map.entry("==", TokenType.OPERATOR_RELATIONAL_EQUALS),
		Map.entry("!=", TokenType.OPERATOR_RELATIONAL_NOT_EQUALS),
		Map.entry("<", TokenType.OPERATOR_RELATIONAL_SMALLER),
		Map.entry(">", TokenType.OPERATOR_RELATIONAL_GREATER),
		Map.entry("<=", TokenType.OPERATOR_RELATIONAL_SMALLER_EQUALS),
		Map.entry(">=", TokenType.OPERATOR_RELATIONAL_GREATER_EQUALS),
		Map.entry("&&", TokenType.OPERATOR_LOGICAL_AND),
		Map.entry("||", TokenType.OPERATOR_LOGICAL_OR),
		Map.entry("!", TokenType.OPERATOR_LOGICAL_NOT),
		Map.entry("&", TokenType.OPERATOR_BITWISE_BINARY_AND),
		Map.entry("|", TokenType.OPERATOR_BITWISE_BINARY_OR),
		Map.entry("^", TokenType.OPERATOR_BITWISE_BINARY_XOR),
		Map.entry("<<", TokenType.OPERATOR_BITWISE_LEFT_SHIFT),
		Map.entry(">>", TokenType.OPERATOR_BITWISE_RIGHT_SHIFT),
		Map.entry("~", TokenType.OPERATOR_BITWISE_NOT),
		Map.entry("(", TokenType.OPERATOR_LEFT_PARENTHESIS),
		Map.entry("[", TokenType.OPERATOR_LEFT_BRACKET),
		Map.entry("?", TokenType.OPERATOR_QUESTION_MARK),
		Map.entry(".", TokenType.OPERATOR_DOT),
		Map.entry(",", TokenType.OPERATOR_COMMA)
	);

	private static final Set<TokenType> SINGLE_CHARACTER_OPERATORS = Set.of(
		TokenType.OPERATOR_LEFT_PARENTHESIS, TokenType.OPERATOR_LEFT_BRACKET, TokenType.OPERATOR_QUESTION_MARK,
		TokenType.OPERATOR_BINARY_STAR, TokenType.OPERATOR_DOT, TokenType.OPERATOR_COMMA
	);

	public static TokenType getOperatorTokenType(String op) {
		return OPERATOR_TOKEN_TYPES.get(op);
	}

	public static boolean isOperator(char op) {
		return OPERATOR_TOKEN_TYPES.get(Character.toString(op)) != null;
	}

	public static boolean isOperator(String op) {
		return OPERATOR_TOKEN_TYPES.get(op) != null;
	}

	// Unstackable Operators: ? -> ?? -> Makes no Sense
	public static boolean isSingleCharOperator(TokenType op) {
		return SINGLE_CHARACTER_OPERATORS.contains(op);
	}

	// Stackable Operators: + -> ++, - -> --, + -> +=
	public static boolean isMultiCharOperator(TokenType op) {
		return !SINGLE_CHARACTER_OPERATORS.contains(op);
	}
}
