package com.krnl32.eye.parser.lexer;

import java.util.Map;
import java.util.Set;

public class LexerUtility {
	private static final Map<String, TokenType> KEYWORD_TYPES = Map.ofEntries(
		// TEMP REMOVE
		Map.entry("float", TokenType.KEYWORD_DATATYPE_FLOAT),
		Map.entry("double", TokenType.KEYWORD_DATATYPE_DOUBLE),
		Map.entry("char", TokenType.KEYWORD_DATATYPE_CHAR),
		Map.entry("str", TokenType.KEYWORD_DATATYPE_STR),
		Map.entry("bool", TokenType.KEYWORD_DATATYPE_BOOL),


		Map.entry("int8_t", TokenType.KEYWORD_DATATYPE_INT8_T),
		Map.entry("int16_t", TokenType.KEYWORD_DATATYPE_INT16_T),
		Map.entry("int32_t", TokenType.KEYWORD_DATATYPE_INT32_T),
		Map.entry("int64_t", TokenType.KEYWORD_DATATYPE_INT64_T),
		Map.entry("int128_t", TokenType.KEYWORD_DATATYPE_INT128_T),

		Map.entry("uint8_t", TokenType.KEYWORD_DATATYPE_UINT8_T),
		Map.entry("uint16_t", TokenType.KEYWORD_DATATYPE_UINT16_T),
		Map.entry("uint32_t", TokenType.KEYWORD_DATATYPE_UINT32_T),
		Map.entry("uint64_t", TokenType.KEYWORD_DATATYPE_UINT64_T),
		Map.entry("uint128_t", TokenType.KEYWORD_DATATYPE_UINT128_T),

		Map.entry("float32_t", TokenType.KEYWORD_DATATYPE_FLOAT32_T),
		Map.entry("float64_t", TokenType.KEYWORD_DATATYPE_FLOAT64_T),

		Map.entry("char8_t", TokenType.KEYWORD_DATATYPE_CHAR8_T),
		Map.entry("char16_t", TokenType.KEYWORD_DATATYPE_CHAR16_T),
		Map.entry("char32_t", TokenType.KEYWORD_DATATYPE_CHAR32_T),

		Map.entry("bool8_t", TokenType.KEYWORD_DATATYPE_BOOL8_T),

		Map.entry("str8_t", TokenType.KEYWORD_DATATYPE_STR8_T),
		Map.entry("str16_t", TokenType.KEYWORD_DATATYPE_STR16_T),
		Map.entry("str32_t", TokenType.KEYWORD_DATATYPE_STR32_T),

		Map.entry("intptr_t", TokenType.KEYWORD_DATATYPE_INTPTR_T),
		Map.entry("uintptr_t", TokenType.KEYWORD_DATATYPE_UINTPTR_T),

		Map.entry("void", TokenType.KEYWORD_DATATYPE_VOID),

		Map.entry("const", TokenType.KEYWORD_TYPE_QUALIFIER_CONST),

		Map.entry("if", TokenType.KEYWORD_CONTROL_IF),
		Map.entry("else", TokenType.KEYWORD_CONTROL_ELSE),

		Map.entry("while", TokenType.KEYWORD_ITERATION_WHILE),
		Map.entry("do", TokenType.KEYWORD_ITERATION_DO),
		Map.entry("for", TokenType.KEYWORD_ITERATION_FOR),
		Map.entry("continue", TokenType.KEYWORD_ITERATION_CONTINUE),
		Map.entry("break", TokenType.KEYWORD_ITERATION_BREAK),

		Map.entry("function", TokenType.KEYWORD_FUNCTION),
		Map.entry("return", TokenType.KEYWORD_RETURN)
	);

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

	public static TokenType getKeywordTokenType(String keyword) {
		return KEYWORD_TYPES.get(keyword);
	}

	public static boolean isKeyword(String keyword) {
		return KEYWORD_TYPES.get(keyword) != null;
	}

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
