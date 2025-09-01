package com.krnl32.eye.parser.parser;

import com.krnl32.eye.ast.Program;
import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.expression.LiteralExpression;
import com.krnl32.eye.ast.expression.PrimaryExpression;
import com.krnl32.eye.ast.literal.*;
import com.krnl32.eye.ast.statement.ExpressionStatement;
import com.krnl32.eye.ast.statement.Statement;
import com.krnl32.eye.ast.statement.TopLevelStatement;
import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.common.exceptions.SyntaxErrorException;
import com.krnl32.eye.common.utility.SourceSpan;
import com.krnl32.eye.parser.lexer.LexerUtility;
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
								| <statement>
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
			eatToken(TokenType.SYMBOL_SEMI_COLON);
			return null;
		}

		ExpressionStatement expressionStatement = new ExpressionStatement(expression());
		eatToken(TokenType.SYMBOL_SEMI_COLON);
		return expressionStatement;
	}

	private Expression expression() {
		return primaryExpression();
	}

	/*
		<primary-expression> ::= <literal-expression>
								| <parenthesized-expression>
								| <identifier-expression>
	 */
	private PrimaryExpression primaryExpression() {
		if (LexerUtility.isLiteral(lookAheadToken.getType())) {
			return literalExpression();
		}

		return null;
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

		return tokens.get(currentTokenIndex++);
	}

	private Token peekToken() {
		if (!hasToken()) {
			return null;
		}

		return tokens.get(currentTokenIndex);
	}

	private Token eatToken(TokenType type) {
		Token token = lookAheadToken;

		if (token == null || token.getType() != type) {
			SourceSpan span = (token != null ? token.getSpan() : null);
			throw new SyntaxErrorException("Unexpected: " + type.name(), span);
		}

		lookAheadToken = nextToken();
		return token;
	}
}
