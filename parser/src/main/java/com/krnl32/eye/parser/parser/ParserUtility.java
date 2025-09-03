package com.krnl32.eye.parser.parser;

import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.types.ExpressionType;
import com.krnl32.eye.ast.types.OperatorType;
import com.krnl32.eye.parser.lexer.TokenType;

public class ParserUtility {
	public static boolean isLHSExpression(Expression expr) {
		return (expr.getType() == ExpressionType.IdentifierExpression || expr.getType() == ExpressionType.MemberExpression);
	}

	public static boolean isLiteral(TokenType type) {
		return (type == TokenType.LITERAL_INT32 || type == TokenType.LITERAL_UINT32 ||
			type == TokenType.LITERAL_FLOAT32 || type == TokenType.LITERAL_FLOAT64 ||
			type == TokenType.LITERAL_CHAR8 || type == TokenType.LITERAL_STR8 ||
			type == TokenType.LITERAL_BOOL8 || type == TokenType.LITERAL_NULL
		);
	}

	public static boolean isAssignmentOperator(TokenType type) {
		return (type == TokenType.OPERATOR_ASSIGNMENT || type == TokenType.OPERATOR_ASSIGNMENT_PLUS ||
			type == TokenType.OPERATOR_ASSIGNMENT_MINUS || type == TokenType.OPERATOR_ASSIGNMENT_STAR ||
			type == TokenType.OPERATOR_ASSIGNMENT_SLASH || type == TokenType.OPERATOR_ASSIGNMENT_MODULO ||
			type == TokenType.OPERATOR_ASSIGNMENT_BITWISE_AND || type == TokenType.OPERATOR_ASSIGNMENT_BITWISE_OR ||
			type == TokenType.OPERATOR_ASSIGNMENT_BITWISE_XOR || type == TokenType.OPERATOR_ASSIGNMENT_BITWISE_LEFT_SHIFT ||
			type == TokenType.OPERATOR_ASSIGNMENT_BITWISE_RIGHT_SHIFT);
	}

	public static boolean isTernaryOperator(TokenType type) {
		return (type == TokenType.OPERATOR_QUESTION_MARK);
	}

	public static boolean isEqualityOperator(TokenType type) {
		return (type == TokenType.OPERATOR_RELATIONAL_EQUALS || type == TokenType.OPERATOR_RELATIONAL_NOT_EQUALS);
	}

	public static boolean isRelationalOperator(TokenType type) {
		return (type == TokenType.OPERATOR_RELATIONAL_SMALLER || type == TokenType.OPERATOR_RELATIONAL_SMALLER_EQUALS ||
			type == TokenType.OPERATOR_RELATIONAL_GREATER || type == TokenType.OPERATOR_RELATIONAL_GREATER_EQUALS);
	}

	public static boolean isBitwiseShiftOperator(TokenType type) {
		return (type == TokenType.OPERATOR_BITWISE_LEFT_SHIFT || type == TokenType.OPERATOR_BITWISE_RIGHT_SHIFT);
	}

	public static boolean isAdditiveOperator(TokenType type) {
		return (type == TokenType.OPERATOR_BINARY_PLUS || type == TokenType.OPERATOR_BINARY_MINUS);
	}

	public static boolean isMultiplicativeOperator(TokenType type) {
		return (type == TokenType.OPERATOR_BINARY_STAR || type == TokenType.OPERATOR_BINARY_SLASH || type == TokenType.OPERATOR_BINARY_MODULO);
	}

	public static OperatorType toOperatorType(TokenType type) {
		return switch (type) {
			case OPERATOR_BINARY_PLUS -> OperatorType.BINARY_PLUS;
			case OPERATOR_BINARY_MINUS -> OperatorType.BINARY_MINUS;
			case OPERATOR_BINARY_STAR -> OperatorType.BINARY_STAR;
			case OPERATOR_BINARY_SLASH -> OperatorType.BINARY_SLASH;
			case OPERATOR_BINARY_MODULO -> OperatorType.BINARY_MODULO;

			case OPERATOR_ARITHMETIC_INCREMENT -> OperatorType.ARITHMETIC_INCREMENT;
			case OPERATOR_ARITHMETIC_DECREMENT -> OperatorType.ARITHMETIC_DECREMENT;

			case OPERATOR_ASSIGNMENT -> OperatorType.ASSIGNMENT;
			case OPERATOR_ASSIGNMENT_PLUS -> OperatorType.ASSIGNMENT_PLUS;
			case OPERATOR_ASSIGNMENT_MINUS -> OperatorType.ASSIGNMENT_MINUS;
			case OPERATOR_ASSIGNMENT_STAR -> OperatorType.ASSIGNMENT_STAR;
			case OPERATOR_ASSIGNMENT_SLASH -> OperatorType.ASSIGNMENT_SLASH;
			case OPERATOR_ASSIGNMENT_MODULO -> OperatorType.ASSIGNMENT_MODULO;
			case OPERATOR_ASSIGNMENT_BITWISE_AND -> OperatorType.ASSIGNMENT_BITWISE_AND;
			case OPERATOR_ASSIGNMENT_BITWISE_OR -> OperatorType.ASSIGNMENT_BITWISE_OR;
			case OPERATOR_ASSIGNMENT_BITWISE_XOR -> OperatorType.ASSIGNMENT_BITWISE_XOR;
			case OPERATOR_ASSIGNMENT_BITWISE_LEFT_SHIFT -> OperatorType.ASSIGNMENT_BITWISE_LEFT_SHIFT;
			case OPERATOR_ASSIGNMENT_BITWISE_RIGHT_SHIFT -> OperatorType.ASSIGNMENT_BITWISE_RIGHT_SHIFT;

			case OPERATOR_RELATIONAL_EQUALS -> OperatorType.RELATIONAL_EQUALS;
			case OPERATOR_RELATIONAL_NOT_EQUALS -> OperatorType.RELATIONAL_NOT_EQUALS;
			case OPERATOR_RELATIONAL_SMALLER -> OperatorType.RELATIONAL_SMALLER;
			case OPERATOR_RELATIONAL_GREATER -> OperatorType.RELATIONAL_GREATER;
			case OPERATOR_RELATIONAL_SMALLER_EQUALS -> OperatorType.RELATIONAL_SMALLER_EQUALS;
			case OPERATOR_RELATIONAL_GREATER_EQUALS -> OperatorType.RELATIONAL_GREATER_EQUALS;

			case OPERATOR_LOGICAL_AND -> OperatorType.LOGICAL_AND;
			case OPERATOR_LOGICAL_OR -> OperatorType.LOGICAL_OR;
			case OPERATOR_LOGICAL_NOT -> OperatorType.LOGICAL_NOT;

			case OPERATOR_BITWISE_BINARY_AND -> OperatorType.BITWISE_BINARY_AND;
			case OPERATOR_BITWISE_BINARY_OR -> OperatorType.BITWISE_BINARY_OR;
			case OPERATOR_BITWISE_BINARY_XOR -> OperatorType.BITWISE_BINARY_XOR;
			case OPERATOR_BITWISE_LEFT_SHIFT -> OperatorType.BITWISE_LEFT_SHIFT;
			case OPERATOR_BITWISE_RIGHT_SHIFT -> OperatorType.BITWISE_RIGHT_SHIFT;
			case OPERATOR_BITWISE_NOT -> OperatorType.BITWISE_NOT;

			case OPERATOR_LEFT_PARENTHESIS -> OperatorType.LEFT_PARENTHESIS;
			case OPERATOR_LEFT_BRACKET -> OperatorType.LEFT_BRACKET;
			case OPERATOR_QUESTION_MARK -> OperatorType.QUESTION_MARK;
			case OPERATOR_DOT -> OperatorType.DOT;
			case OPERATOR_COMMA -> OperatorType.COMMA;
			default -> null;
		};
	}
}
