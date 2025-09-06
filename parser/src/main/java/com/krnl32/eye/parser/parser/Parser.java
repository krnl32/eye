package com.krnl32.eye.parser.parser;

import com.krnl32.eye.ast.Program;
import com.krnl32.eye.ast.expression.*;
import com.krnl32.eye.ast.literal.*;
import com.krnl32.eye.ast.statement.ExpressionStatement;
import com.krnl32.eye.ast.statement.Statement;
import com.krnl32.eye.ast.statement.TopLevelStatement;
import com.krnl32.eye.ast.types.OperatorType;
import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.common.exceptions.SyntaxErrorException;
import com.krnl32.eye.common.utility.SourceSpan;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {
	private final List<Token> tokens;
	private Program program;
	private Token lookAheadToken;
	private int currentTokenIndex;

	public Parser(List<Token> tokens) {
		this.tokens = tokens;
	}

	public Program parse() {
		try {
			this.lookAheadToken = nextToken();
			this.program = program();
		} catch (Exception e) {
			Logger.error(e.getMessage());
			throw e;
		}

		return program;
	}

	/*
	 	<program> ::= <top-level-statement-list>
	 */
	private Program program() {
		return new Program(topLevelStatementList());
	}

	/*
		<top-level-statement-list> ::= <top-level-statement>
									| <top-level-statement-list> <top-level-statement>
	 */
	private List<TopLevelStatement> topLevelStatementList() {
		List<TopLevelStatement> topLevelStatementList = new ArrayList<>();

		while (lookAheadToken != null && lookAheadToken.getType() != TokenType.END_OF_FILE) {
			topLevelStatementList.add(topLevelStatement());
		}

		return topLevelStatementList;
	}

	/*
		<top-level-statement> ::= <function-statement>
              					| <variable-statement>
								| <statement> // TEMPORARY
	 */
	private TopLevelStatement topLevelStatement() {
		return switch (lookAheadToken.getType()) {
			default -> statement();
		};
	}

	/*
		<statement> ::= <expression-statement>
            		  | <block-statement>
            		  | <variable-statement>
            		  | <control-statement>
            		  | <iteration-statement>
            		  | <continue-statement>
            		  | <break-statement>
            		  | <return-statement>
	 */
	private Statement statement() {
		return switch (lookAheadToken.getType()) {
			default -> expressionStatement();
		};
	}

	/*
		<expression-statement> ::= <expression> ";"
	 */
	private ExpressionStatement expressionStatement() {
		if (isLookAheadToken(TokenType.SYMBOL_SEMI_COLON)) {
			throw new SyntaxErrorException("Expected Expression Before: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		ExpressionStatement exprStmt = new ExpressionStatement(expression());
		eatToken(TokenType.SYMBOL_SEMI_COLON);
		return exprStmt;
	}

	/*
		<expression> ::= <comma-expression>
	 */
	private Expression expression() {
		return commaExpression();
	}

	/*
		<comma-expression> ::= <comma-expression> "," <assignment-expression>
							 | <assignment-expression>
	 */
	private Expression commaExpression() {
		Expression left = assignmentExpression();

		while (isLookAheadToken(TokenType.OPERATOR_COMMA)) {
			eatToken(TokenType.OPERATOR_COMMA);
			OperatorType operatorType = ParserUtility.toOperatorType(TokenType.OPERATOR_COMMA);

			Expression right = assignmentExpression();
			left = new BinaryExpression(operatorType, left, right);
		}

		return left;
	}

	/*
		<assignment-expression> ::= <ternary-expression>
                          		  | <lvalue-expression> <assignment-operator> <assignment-expression>
	 */
	private Expression assignmentExpression() {
		Expression left = ternaryExpression();

		if (!ParserUtility.isAssignmentOperator(lookAheadToken.getType())) {
			return left;
		}

		if (!ParserUtility.isLValueExpression(left)) {
			throw new SyntaxErrorException("Unexpected LValue Expression '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		Token operatorToken = eatToken(lookAheadToken.getType());
		OperatorType operatorType = ParserUtility.toOperatorType(operatorToken.getType());

		return new AssignmentExpression(operatorType, left, assignmentExpression());
	}

	/*
		<ternary-expression> ::= <logical-or-expression>
							   | <logical-or-expression> "?" <expression> ":" <ternary-expression>
	 */
	private Expression ternaryExpression() {
		Expression condition = logicalOrExpression();

		if (!ParserUtility.isTernaryOperator(lookAheadToken.getType())) {
			return condition;
		}

		eatToken(lookAheadToken.getType());
		Expression consequent = expression();
		eatToken(TokenType.SYMBOL_COLON);

		return new TernaryExpression(condition, consequent, ternaryExpression());
	}

	/*
		<logical-or-expression> ::= <logical-or-expression> "||" <logical-and-expression>
                         		  | <logical-and-expression>
	 */
	private Expression logicalOrExpression() {
		Expression left = logicalAndExpression();

		while (isLookAheadToken(TokenType.OPERATOR_LOGICAL_OR)) {
			eatToken(TokenType.OPERATOR_LOGICAL_OR);

			Expression right = logicalAndExpression();
			left = new BinaryExpression(OperatorType.LOGICAL_OR, left, right);
		}

		return left;
	}

	/*
		<logical-and-expression> ::= <logical-and-expression> "&&" <bitwise-or-expression>
								   | <bitwise-or-expression>
	 */
	private Expression logicalAndExpression() {
		Expression left = bitwiseOrExpression();

		while (isLookAheadToken(TokenType.OPERATOR_LOGICAL_AND)) {
			eatToken(TokenType.OPERATOR_LOGICAL_AND);

			Expression right = bitwiseOrExpression();
			left = new BinaryExpression(OperatorType.LOGICAL_AND, left, right);
		}

		return left;
	}

	/*
		<bitwise-or-expression> ::= <bitwise-or-expression> "|" <bitwise-xor-expression>
								  | <bitwise-xor-expression>
	 */
	private Expression bitwiseOrExpression() {
		Expression left = bitwiseXorExpression();

		while (isLookAheadToken(TokenType.OPERATOR_BITWISE_BINARY_OR)) {
			eatToken(TokenType.OPERATOR_BITWISE_BINARY_OR);

			Expression right = bitwiseXorExpression();
			left = new BinaryExpression(OperatorType.BITWISE_BINARY_OR, left, right);
		}

		return left;
	}

	/*
		<bitwise-xor-expression> ::= <bitwise-xor-expression> "^" <bitwise-and-expression>
								   | <bitwise-and-expression>
	 */
	private Expression bitwiseXorExpression() {
		Expression left = bitwiseAndExpression();

		while (isLookAheadToken(TokenType.OPERATOR_BITWISE_BINARY_XOR)) {
			eatToken(TokenType.OPERATOR_BITWISE_BINARY_XOR);

			Expression right = bitwiseAndExpression();
			left = new BinaryExpression(OperatorType.BITWISE_BINARY_XOR, left, right);
		}

		return left;
	}

	/*
		<bitwise-and-expression> ::= <bitwise-and-expression> "&" <equality-expression>
								   | <equality-expression>
	 */
	private Expression bitwiseAndExpression() {
		Expression left = equalityExpression();

		while (isLookAheadToken(TokenType.OPERATOR_BITWISE_BINARY_AND)) {
			eatToken(TokenType.OPERATOR_BITWISE_BINARY_AND);

			Expression right = equalityExpression();
			left = new BinaryExpression(OperatorType.BITWISE_BINARY_AND, left, right);
		}

		return left;
	}

	/*
		<equality-expression> ::= <equality-expression> <equality-operator> <relational-expression>
								| <relational-expression>
	 */
	private Expression equalityExpression() {
		Expression left = relationalExpression();

		while (ParserUtility.isEqualityOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = relationalExpression();
			left = new BinaryExpression(operator, left, right);
		}

		return left;
	}

	/*
		<relational-expression> ::= <relational-expression> <relational-operator> <bitwise-shift-expression>
								  | <bitwise-shift-expression>
	 */
	private Expression relationalExpression() {
		Expression left = bitwiseShiftExpression();

		while (ParserUtility.isRelationalOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = bitwiseShiftExpression();
			left = new BinaryExpression(operator, left, right);
		}

		return left;
	}

	/*
		<bitwise-shift-expression> ::= <bitwise-shift-expression> <bitwise-shift-operator> <additive-expression>
									 | <additive-expression>
	 */
	private Expression bitwiseShiftExpression() {
		Expression left = additiveExpression();

		while (ParserUtility.isBitwiseShiftOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = additiveExpression();
			left = new BinaryExpression(operator, left, right);
		}

		return left;
	}

	/*
		<additive-expression> ::= <additive-expression> <additive-operator> <multiplicative-expression>
								| <multiplicative-expression>
	 */
	private Expression additiveExpression() {
		Expression left = multiplicativeExpression();

		while (ParserUtility.isAdditiveOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = multiplicativeExpression();
			left = new BinaryExpression(operator, left, right);
		}

		return left;
	}

	/*
		<multiplicative-expression> ::= <multiplicative-expression> <multiplicative-operator> <unary-expression>
									  | <unary-expression>
	 */
	private Expression multiplicativeExpression() {
		Expression left = unaryExpression();

		while (ParserUtility.isMultiplicativeOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = unaryExpression();
			left = new BinaryExpression(operator, left, right);
		}

		return left;
	}

	/*
		<unary-expression> ::= <unary-operator> <unary-expression>
							 | <literal-expression>
							 | <postfix-expression>
	 */
	private Expression unaryExpression() {
		if (ParserUtility.isUnaryOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			return new UnaryExpression(operator, unaryExpression());
		}

		if (ParserUtility.isLiteral(lookAheadToken.getType())) {
			return literalExpression();
		}

		return postfixExpression();
	}

	/*
		<postfix-expression> ::= <primary-member-expression>
							   | <postfix-expression> "(" <optional-argument-list> ")"
							   | <lvalue-expression> <postfix-operator>

		<primary-member-expression> ::= <identifier-expression>
									  | <parenthesized-expression>

		<optional-argument-list> ::= <argument-list>
								   |

		<argument-list> ::= <expression>
						  | <argument-list> "," <expression>
	 */
	private Expression postfixExpression() {
		// Only allow identifier or parenthesized expression
		Expression left = switch (lookAheadToken.getType()) {
			case IDENTIFIER -> identifierExpression();
			case OPERATOR_LEFT_PARENTHESIS -> parenthesizedExpression();
			default -> throw new SyntaxErrorException("Expected identifier or parenthesized expression", lookAheadToken.getSpan());
		};

		while (true) {
			TokenType type = lookAheadToken.getType();

			// Parse Function Calls
			if (type == TokenType.OPERATOR_LEFT_PARENTHESIS) {
				eatToken(TokenType.OPERATOR_LEFT_PARENTHESIS);

				List<Expression> arguments = new ArrayList<>();

				if (!isLookAheadToken(TokenType.SYMBOL_RIGHT_PARENTHESIS)) {
					do {
						arguments.add(assignmentExpression());
					} while (isLookAheadToken(TokenType.OPERATOR_COMMA) && eatToken(TokenType.OPERATOR_COMMA) != null);
				}

				eatToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);

				left = new FunctionCallExpression(left, arguments);

			}
			// Parse Postfix Expression
			else if (ParserUtility.isPostfixOperator(type)) {
				Token token = eatToken(lookAheadToken.getType());
				OperatorType operator = ParserUtility.toOperatorType(token.getType());

				if (!ParserUtility.isLValueExpression(left)) {
					throw new SyntaxErrorException("Unexpected LValue Expression '" + token.getType().name() + "'", token.getSpan());
				}

				return new PostfixExpression(operator, left);
			} else {
				break;
			}
		}

		return left;
	}

	/*
		<literal-expression> ::= <int32-literal>
								| <uint32-literal>
								| <float32-literal>
								| <float64-literal>
								| <char8-literal>
								| <str8-literal>
								| <bool8-literal>
								| <null-literal>
	 */
	private LiteralExpression literalExpression() {
		return switch (lookAheadToken.getType()) {
			case LITERAL_INT32 -> int32Literal();
			case LITERAL_UINT32 -> uint32Literal();
			case LITERAL_FLOAT32 -> float32Literal();
			case LITERAL_FLOAT64 -> float64Literal();
			case LITERAL_CHAR8 -> char8Literal();
			case LITERAL_STR8 -> str8Literal();
			case LITERAL_BOOL8 -> bool8Literal();
			case LITERAL_NULL -> nullLiteral();
			default -> throw new SyntaxErrorException("Unexpected LiteralExpression '" + lookAheadToken.getValue().toString() + '"', lookAheadToken.getSpan());
		};
	}

	/*
		<int32-literal> ::= LITERAL_INT32
	 */
	private LiteralExpression int32Literal() {
		Token token = eatToken(TokenType.LITERAL_INT32);
		return new LiteralExpression(new Int32Literal(token.<Integer>getValue()));
	}

	/*
		<uint32-literal> ::= LITERAL_UINT32
	 */
	private LiteralExpression uint32Literal() {
		Token token = eatToken(TokenType.LITERAL_UINT32);
		return new LiteralExpression(new Uint32Literal(token.<Integer>getValue()));
	}

	/*
		<float32-literal> ::= LITERAL_FLOAT32
	 */
	private LiteralExpression float32Literal() {
		Token token = eatToken(TokenType.LITERAL_FLOAT32);
		return new LiteralExpression(new Float32Literal(token.<Float>getValue()));
	}

	/*
		<float64-literal> ::= LITERAL_FLOAT64
	 */
	private LiteralExpression float64Literal() {
		Token token = eatToken(TokenType.LITERAL_FLOAT64);
		return new LiteralExpression(new Float64Literal(token.<Double>getValue()));
	}

	/*
		<char8-literal> ::= LITERAL_CHAR8
	 */
	private LiteralExpression char8Literal() {
		Token token = eatToken(TokenType.LITERAL_CHAR8);
		return new LiteralExpression(new Char8Literal(token.<Character>getValue()));
	}

	/*
		<str8-literal> ::= LITERAL_STR8
	 */
	private LiteralExpression str8Literal() {
		Token token = eatToken(TokenType.LITERAL_STR8);
		return new LiteralExpression(new Str8Literal(token.<String>getValue()));
	}

	/*
		<bool8-literal> ::= LITERAL_BOOL8
	 */
	private LiteralExpression bool8Literal() {
		Token token = eatToken(TokenType.LITERAL_BOOL8);
		return new LiteralExpression(new Bool8Literal(token.<Boolean>getValue()));
	}

	/*
		<null-literal> ::= LITERAL_NULL
	 */
	private LiteralExpression nullLiteral() {
		eatToken(TokenType.LITERAL_NULL);
		return new LiteralExpression(new NullLiteral());
	}

	/*
		<parenthesized-expression> ::= "(" <expression> ")"
	 */
	private Expression parenthesizedExpression() {
		eatToken(TokenType.OPERATOR_LEFT_PARENTHESIS);
		Expression expression = expression();
		eatToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);
		return expression;
	}

	/*
		<identifier-token> ::= IDENTIFIER
	 */
	private IdentifierExpression identifierExpression() {
		Token token = eatToken(TokenType.IDENTIFIER);
		return new IdentifierExpression(token.<String>getValue());
	}

	// Token Manipulation
	private boolean isLookAheadToken(TokenType type) {
		return (lookAheadToken != null && lookAheadToken.getType() == type);
	}

	private boolean hasToken() {
		return currentTokenIndex < tokens.size();
	}

	private Token nextToken() {
		if (!hasToken()) {
			return null;
		}

		Token token = tokens.get(currentTokenIndex++);

		// Ignore These Tokens
		while (token != null && (token.getType() == TokenType.NEWLINE || token.getType() == TokenType.COMMENT || token.getType() == TokenType.SYMBOL_BACKSLASH)) {
			if (!hasToken()) {
				return null;
			}

			token = tokens.get(currentTokenIndex++);
		}

		return token;
	}

	private Token peekToken() {
		if (!hasToken()) {
			return null;
		}

		Token token = tokens.get(currentTokenIndex);

		// Ignore These Tokens
		while (token != null && (token.getType() == TokenType.NEWLINE || token.getType() == TokenType.COMMENT || token.getType() == TokenType.SYMBOL_BACKSLASH)) {
			if (!hasToken()) {
				return null;
			}

			token = tokens.get(++currentTokenIndex);
		}

		return token;
	}

	private Token eatToken(TokenType type) {
		Token token = lookAheadToken;

		if (token == null || token.getType() != type) {
			String msg = (token != null ? token.getType().name() + ", Expected: " + type.name() : "");
			SourceSpan span = (token != null ? token.getSpan() : null);
			throw new SyntaxErrorException("Unexpected: " + msg, span);
		}

		lookAheadToken = nextToken();
		return token;
	}
}
