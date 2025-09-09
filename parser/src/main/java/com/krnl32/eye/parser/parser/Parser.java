package com.krnl32.eye.parser.parser;

import com.krnl32.eye.ast.Program;
import com.krnl32.eye.ast.expression.*;
import com.krnl32.eye.ast.literal.*;
import com.krnl32.eye.ast.statement.*;
import com.krnl32.eye.ast.types.DatatypeType;
import com.krnl32.eye.ast.types.OperatorType;
import com.krnl32.eye.ast.types.TypeQualifierType;
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
		<top-level-statement> ::= <variable-statement>
              					| <function-statement>
	 */
	private TopLevelStatement topLevelStatement() {
		return switch (lookAheadToken.getType()) {
			case KEYWORD_TYPE_QUALIFIER_CONST,
				 KEYWORD_DATATYPE_INT32_T,
				 KEYWORD_DATATYPE_UINT32_T,
				 KEYWORD_DATATYPE_FLOAT32_T,
				 KEYWORD_DATATYPE_FLOAT64_T,
				 KEYWORD_DATATYPE_CHAR8_T,
				 KEYWORD_DATATYPE_STR8_T,
				 KEYWORD_DATATYPE_BOOL8_T,
				 KEYWORD_DATATYPE_VOID -> variableStatement();

			case KEYWORD_FUNCTION -> functionStatement();

			default -> throw new SyntaxErrorException("Unexpected Top Level Statement: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		};
	}

	/*
		<variable-statement> ::= <optional-type-qualifier> <datatype-keyword> <variable-declaration-list> ";"
	 */
	private VariableStatement variableStatement() {
		TypeQualifierType typeQualifier = null;

		if (ParserUtility.isTypeQualifier(lookAheadToken.getType())) {
			Token typeQualiferToken = eatToken(lookAheadToken.getType());
			typeQualifier = ParserUtility.toTypeQualifierType(typeQualiferToken.getType());
		}

		if (!ParserUtility.isDatatype(lookAheadToken.getType())) {
			throw new SyntaxErrorException("Unexpected Datatype: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		Token datatypeToken = eatToken(lookAheadToken.getType());
		DatatypeType datatype = ParserUtility.toDatatypeType(datatypeToken.getType());

		VariableStatement variableStmt = new VariableStatement(typeQualifier, datatype, variableDeclarationList());
		eatToken(TokenType.SYMBOL_SEMI_COLON);

		return variableStmt;
	}

	/*
		<variable-declaration-list> ::= <variable-declaration>
									  | <variable-declaration-list> "," <variable-declaration>
	 */
	List<VariableDeclaration> variableDeclarationList() {
		List<VariableDeclaration> variableDeclarationList = new ArrayList<>();

		do {
			variableDeclarationList.add(variableDeclaration());
		} while (isLookAheadToken(TokenType.OPERATOR_COMMA) && eatToken(TokenType.OPERATOR_COMMA) != null);

		return variableDeclarationList;
	}

	/*
		<variable-declaration> ::= <identifier-token> <optional-variable-initializer>

		<optional-variable-initializer> ::= <variable-initializer>
										  |
	 */
	private VariableDeclaration variableDeclaration() {
		Token identifierToken = eatToken(TokenType.IDENTIFIER);
		String identifier = identifierToken.getValue();

		Expression initializer = null;

		if (!isLookAheadToken(TokenType.SYMBOL_SEMI_COLON) && !isLookAheadToken(TokenType.OPERATOR_COMMA)) {
			initializer = variableInitializer();
		}

		return new VariableDeclaration(identifier, initializer);
	}

	/*
		<variable-initializer> ::= "=" <assignment-expression>
	 */
	private Expression variableInitializer() {
		eatToken(TokenType.OPERATOR_ASSIGNMENT);
		return assignmentExpression();
	}

	/*
		<function-statement> ::= "function" <datatype-keyword> <identifier-token> "(" <optional-function-parameter-list> ")" <block-statement>

		<optional-function-parameter-list> ::= <function-parameter-list>
											 |
	 */
	private FunctionStatement functionStatement() {
		eatToken(TokenType.KEYWORD_FUNCTION);

		if (!ParserUtility.isDatatype(lookAheadToken.getType())) {
			throw new SyntaxErrorException("Unexpected Datatype: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		Token datatypeToken = eatToken(lookAheadToken.getType());
		DatatypeType returnType = ParserUtility.toDatatypeType(datatypeToken.getType());

		Token identifierToken = eatToken(TokenType.IDENTIFIER);
		String identifier = identifierToken.<String>getValue();

		eatToken(TokenType.OPERATOR_LEFT_PARENTHESIS);

		boolean hasParameters = !isLookAheadToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);
		List<FunctionParameter> parameters = hasParameters ? functionParameterList() : new ArrayList<>();

		eatToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);

		Statement body = blockStatement();

		return new FunctionStatement(returnType, identifier, parameters, body);
	}

	/*
		<function-parameter-list> ::= <function-parameter>
									| <function-parameter-list> "," <function-parameter>
	 */
	private List<FunctionParameter> functionParameterList() {
		List<FunctionParameter> functionParameterList = new ArrayList<>();

		do {
			functionParameterList.add(functionParameter());
		} while (isLookAheadToken(TokenType.OPERATOR_COMMA) && eatToken(TokenType.OPERATOR_COMMA) != null);

		return functionParameterList;
	}

	/*
		<function-parameter> ::= <optional-type-qualifier> <datatype-keyword> <identifier-token> <optional-variable-initializer>
	 */
	private FunctionParameter functionParameter() {
		TypeQualifierType typeQualifier = null;

		if (ParserUtility.isTypeQualifier(lookAheadToken.getType())) {
			Token typeQualiferToken = eatToken(lookAheadToken.getType());
			typeQualifier = ParserUtility.toTypeQualifierType(typeQualiferToken.getType());
		}

		if (!ParserUtility.isDatatype(lookAheadToken.getType())) {
			throw new SyntaxErrorException("Unexpected Datatype: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		Token datatypeToken = eatToken(lookAheadToken.getType());
		DatatypeType datatype = ParserUtility.toDatatypeType(datatypeToken.getType());

		Token identifierToken = eatToken(TokenType.IDENTIFIER);
		String identifier = identifierToken.<String>getValue();

		Expression initializer = null;
		boolean hasInitializer = !isLookAheadToken(TokenType.SYMBOL_RIGHT_PARENTHESIS) && !isLookAheadToken(TokenType.OPERATOR_COMMA);

		if (hasInitializer) {
			initializer = variableInitializer();
		}

		return new FunctionParameter(typeQualifier, datatype, identifier, initializer);
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
			case SYMBOL_LEFT_BRACE -> blockStatement();

			case KEYWORD_TYPE_QUALIFIER_CONST,
				 KEYWORD_DATATYPE_INT32_T,
				 KEYWORD_DATATYPE_UINT32_T,
				 KEYWORD_DATATYPE_FLOAT32_T,
				 KEYWORD_DATATYPE_FLOAT64_T,
				 KEYWORD_DATATYPE_CHAR8_T,
				 KEYWORD_DATATYPE_STR8_T,
				 KEYWORD_DATATYPE_BOOL8_T,
				 KEYWORD_DATATYPE_VOID -> variableStatement();

			case KEYWORD_CONTROL_IF	-> controlStatement();

			case KEYWORD_ITERATION_DO,
				 KEYWORD_ITERATION_WHILE,
				 KEYWORD_ITERATION_FOR -> iterationStatement();

			case KEYWORD_ITERATION_CONTINUE -> continueStatement();
			case KEYWORD_ITERATION_BREAK -> breakStatement();
			case KEYWORD_RETURN -> returnStatement();

			default -> expressionStatement();
		};
	}

	/*
		<optional-statement-list> ::= <statement-list>
									|
	 */
	private List<Statement> statementList(TokenType stop) {
		List<Statement> statementList = new ArrayList<>();

		while (lookAheadToken != null && lookAheadToken.getType() != TokenType.END_OF_FILE && lookAheadToken.getType() != stop) {
			statementList.add(statement());
		}

		return statementList;
	}

	/*
		<expression-statement> ::= <expression> ";"
	 */
	private ExpressionStatement expressionStatement() {
		if (isLookAheadToken(TokenType.SYMBOL_SEMI_COLON)) {
			eatToken(TokenType.SYMBOL_SEMI_COLON);
			return null;
		}

		ExpressionStatement exprStmt = new ExpressionStatement(expression());
		eatToken(TokenType.SYMBOL_SEMI_COLON);
		return exprStmt;
	}

	/*
		<block-statement> ::= "{" <optional-statement-list> "}"
	 */
	private BlockStatement blockStatement() {
		eatToken(TokenType.SYMBOL_LEFT_BRACE);
		List<Statement> statementList = statementList(TokenType.SYMBOL_RIGHT_BRACE);
		eatToken(TokenType.SYMBOL_RIGHT_BRACE);

		return new BlockStatement(statementList);
	}

	/*
		<control-statement> ::= "if" "(" <expression> ")" <statement>
							  | "if" "(" <expression> ")" <statement> "else" <statement>
	 */
	private ControlStatement controlStatement() {
		eatToken(TokenType.KEYWORD_CONTROL_IF);
		eatToken(TokenType.OPERATOR_LEFT_PARENTHESIS);

		Expression condition = expression();

		if (condition == null) {
			throw new SyntaxErrorException("Expected Expression", lookAheadToken.getSpan());
		}

		eatToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);

		Statement consequent = statement();
		Statement alternate = null;

		if (isLookAheadToken(TokenType.KEYWORD_CONTROL_ELSE)) {
			eatToken(TokenType.KEYWORD_CONTROL_ELSE);
			alternate = statement();
		}

		return new ControlStatement(condition, consequent, alternate);
	}

	/*
		<iteration-statement> ::= <while-statement>
								| <do-while-statement>
								| <for-statement>
	 */
	private Statement iterationStatement() {
		return switch (lookAheadToken.getType()) {
			case KEYWORD_ITERATION_WHILE -> whileStatement();
			case KEYWORD_ITERATION_DO -> doWhileStatement();
			case KEYWORD_ITERATION_FOR -> forStatement();
			default -> null;
		};
	}

	/*
		<while-statement> ::= "while" "(" <expression> ")" <statement>
	 */
	private WhileStatement whileStatement() {
		eatToken(TokenType.KEYWORD_ITERATION_WHILE);
		eatToken(TokenType.OPERATOR_LEFT_PARENTHESIS);

		Expression condition = expression();

		if (condition == null) {
			throw new SyntaxErrorException("Expected Expression", lookAheadToken.getSpan());
		}

		eatToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);

		Statement body = statement();

		return new WhileStatement(condition, body);
	}

	/*
		<do-while-statement> ::= "do" <statement> "while" "(" <expression> ")" ";"
	 */
	private DoWhileStatement doWhileStatement() {
		eatToken(TokenType.KEYWORD_ITERATION_DO);
		Statement body = statement();
		eatToken(TokenType.KEYWORD_ITERATION_WHILE);
		eatToken(TokenType.OPERATOR_LEFT_PARENTHESIS);

		Expression condition = expression();

		if (condition == null) {
			throw new SyntaxErrorException("Expected Expression", lookAheadToken.getSpan());
		}

		eatToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);
		eatToken(TokenType.SYMBOL_SEMI_COLON);

		return new DoWhileStatement(condition, body);
	}

	/*
		<for-statement> ::= "for" "(" <optional-for-initializer> ";" <optional-expression> ";" <optional-expression> ")" <statement>

	 	<optional-for-initializer> ::= <for-initializer>
	 								 |

		<for-initializer> ::= <variable-statement>
							| <expression>
	 */
	private ForStatement forStatement() {
		eatToken(TokenType.KEYWORD_ITERATION_FOR);
		eatToken(TokenType.OPERATOR_LEFT_PARENTHESIS);

		// Parse For Initializer
		ForStatement.ForInitializerType initializerType = ForStatement.ForInitializerType.NULL;
		Object initializer = null;

		boolean hasInitializer = !isLookAheadToken(TokenType.SYMBOL_SEMI_COLON);
		boolean isVariableStatementInitializer = (hasInitializer && ParserUtility.isTypeQualifier(lookAheadToken.getType()) || ParserUtility.isDatatype(lookAheadToken.getType()));
		boolean isExpressionInitializer = (hasInitializer && !isVariableStatementInitializer);

		if (isVariableStatementInitializer) {
			TypeQualifierType typeQualifier = null;

			if (ParserUtility.isTypeQualifier(lookAheadToken.getType())) {
				Token typeQualiferToken = eatToken(lookAheadToken.getType());
				typeQualifier = ParserUtility.toTypeQualifierType(typeQualiferToken.getType());
			}

			if (!ParserUtility.isDatatype(lookAheadToken.getType())) {
				throw new SyntaxErrorException("Unexpected Datatype: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

			Token datatypeToken = eatToken(lookAheadToken.getType());
			DatatypeType datatype = ParserUtility.toDatatypeType(datatypeToken.getType());

			initializerType = ForStatement.ForInitializerType.VARIABLE_STATEMENT;
			initializer = new VariableStatement(typeQualifier, datatype, variableDeclarationList());
		} else if(isExpressionInitializer) {
			initializerType = ForStatement.ForInitializerType.EXPRESSION;
			initializer = expression();
		}

		eatToken(TokenType.SYMBOL_SEMI_COLON);

		boolean hasCondition = !isLookAheadToken(TokenType.SYMBOL_SEMI_COLON);
		Expression condition = hasCondition ? expression() : null;
		eatToken(TokenType.SYMBOL_SEMI_COLON);

		boolean hasUpdate = !isLookAheadToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);
		Expression update = hasUpdate ? expression() : null;
		eatToken(TokenType.SYMBOL_RIGHT_PARENTHESIS);

		Statement body = statement();

		return new ForStatement(initializerType, initializer, condition, update, body);
	}

	/*
		<continue-statement> ::= "continue" ";"
	 */
	private ContinueStatement continueStatement() {
		eatToken(TokenType.KEYWORD_ITERATION_CONTINUE);
		eatToken(TokenType.SYMBOL_SEMI_COLON);
		return new ContinueStatement();
	}

	/*
		<break-statement> ::= "break" ";"
	 */
	private BreakStatement breakStatement() {
		eatToken(TokenType.KEYWORD_ITERATION_BREAK);
		eatToken(TokenType.SYMBOL_SEMI_COLON);
		return new BreakStatement();
	}

	/*
		<return-statement> ::= "return" <optional-expression> ";"
	 */
	private ReturnStatement returnStatement() {
		eatToken(TokenType.KEYWORD_RETURN);
		Expression expression = isLookAheadToken(TokenType.SYMBOL_SEMI_COLON) ? null : expression();
		eatToken(TokenType.SYMBOL_SEMI_COLON);
		return new ReturnStatement(expression);
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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

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

		if (condition == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (isLookAheadToken(TokenType.OPERATOR_LOGICAL_OR)) {
			eatToken(TokenType.OPERATOR_LOGICAL_OR);

			Expression right = logicalAndExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (isLookAheadToken(TokenType.OPERATOR_LOGICAL_AND)) {
			eatToken(TokenType.OPERATOR_LOGICAL_AND);

			Expression right = bitwiseOrExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (isLookAheadToken(TokenType.OPERATOR_BITWISE_BINARY_OR)) {
			eatToken(TokenType.OPERATOR_BITWISE_BINARY_OR);

			Expression right = bitwiseXorExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (isLookAheadToken(TokenType.OPERATOR_BITWISE_BINARY_XOR)) {
			eatToken(TokenType.OPERATOR_BITWISE_BINARY_XOR);

			Expression right = bitwiseAndExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (isLookAheadToken(TokenType.OPERATOR_BITWISE_BINARY_AND)) {
			eatToken(TokenType.OPERATOR_BITWISE_BINARY_AND);

			Expression right = equalityExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (ParserUtility.isEqualityOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = relationalExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (ParserUtility.isRelationalOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = bitwiseShiftExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (ParserUtility.isBitwiseShiftOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = additiveExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (ParserUtility.isAdditiveOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = multiplicativeExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

		if (left == null) {
			throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
		}

		while (ParserUtility.isMultiplicativeOperator(lookAheadToken.getType())) {
			Token token = eatToken(lookAheadToken.getType());
			OperatorType operator = ParserUtility.toOperatorType(token.getType());

			Expression right = unaryExpression();

			if (right == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

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

			Expression unaryExpr = unaryExpression();

			if (unaryExpr == null) {
				throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
			}

			return new UnaryExpression(operator, unaryExpr);
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
			default -> throw new SyntaxErrorException("Unexpected Expression: '" + lookAheadToken.getType().name() + "'", lookAheadToken.getSpan());
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
