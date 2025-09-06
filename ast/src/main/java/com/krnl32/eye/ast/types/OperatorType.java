package com.krnl32.eye.ast.types;

public enum OperatorType {
	BINARY_PLUS("+"),
	BINARY_MINUS("-"),
	BINARY_STAR("*"),
	BINARY_SLASH("/"),
	BINARY_MODULO("%"),

	ARITHMETIC_INCREMENT("++"),
	ARITHMETIC_DECREMENT("--"),

	ASSIGNMENT("="),
	ASSIGNMENT_PLUS("+="),
	ASSIGNMENT_MINUS("-="),
	ASSIGNMENT_STAR("*="),
	ASSIGNMENT_SLASH("/="),
	ASSIGNMENT_MODULO("%="),
	ASSIGNMENT_BITWISE_AND("&="),
	ASSIGNMENT_BITWISE_OR("|="),
	ASSIGNMENT_BITWISE_XOR("^="),
	ASSIGNMENT_BITWISE_LEFT_SHIFT("<<="),
	ASSIGNMENT_BITWISE_RIGHT_SHIFT(">>="),

	RELATIONAL_EQUALS("=="),
	RELATIONAL_NOT_EQUALS("!="),
	RELATIONAL_SMALLER("<"),
	RELATIONAL_GREATER(">"),
	RELATIONAL_SMALLER_EQUALS("<="),
	RELATIONAL_GREATER_EQUALS(">="),

	LOGICAL_AND("&&"),
	LOGICAL_OR("||"),
	LOGICAL_NOT("!"),

	BITWISE_BINARY_AND("&"),
	BITWISE_BINARY_OR("|"),
	BITWISE_BINARY_XOR("^"),
	BITWISE_LEFT_SHIFT("<<"),
	BITWISE_RIGHT_SHIFT(">>"),
	BITWISE_NOT("~"),

	LEFT_PARENTHESIS("("),
	LEFT_BRACKET("["),
	QUESTION_MARK("?"),
	DOT("."),
	COMMA(",");

	private final String symbol;

	OperatorType(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}

	public static OperatorType fromSymbol(String symbol) {
		for (OperatorType operator : values()) {
			if (operator.symbol.equals(symbol)) {
				return operator;
			}
		}

		throw new IllegalArgumentException("Unknown OperatorType Symbol: " + symbol);
	}
}
