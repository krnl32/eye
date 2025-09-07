package com.krnl32.eye.astserializer.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krnl32.eye.ast.Program;
import com.krnl32.eye.ast.expression.*;
import com.krnl32.eye.ast.literal.*;
import com.krnl32.eye.ast.statement.*;
import com.krnl32.eye.astserializer.ASTSerializer;

import java.util.List;

public class JSONASTSerializer implements ASTSerializer<ObjectNode> {
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public ObjectNode serialize(Program program) {
		ObjectNode programNode = mapper.createObjectNode();
		programNode.put("type", "Program");
		programNode.put("topLevelStatementCount", program.getTopLevelStatements().size());
		programNode.set("topLevelStatements", serializeTopLevelStatements(program.getTopLevelStatements()));
		return programNode;
	}

	private ArrayNode serializeTopLevelStatements(List<TopLevelStatement> topLevelStatements) {
		ArrayNode arrayNode = mapper.createArrayNode();

		for (TopLevelStatement topLevelStatement : topLevelStatements) {
			if (topLevelStatement instanceof Statement) {
				ObjectNode statementNode = serializeStatement((Statement) topLevelStatement);
				arrayNode.add(statementNode);
			}
		}

		return arrayNode;
	}

	private ObjectNode serializeStatement(Statement stmt) {
		return switch (stmt.getType()) {
			case ExpressionStatement -> serializeExpressionStatement((ExpressionStatement) stmt);
			case BlockStatement -> serializeBlockStatement((BlockStatement) stmt);
			case VariableStatement -> serializeVariableStatement((VariableStatement) stmt);
			case ControlStatement -> serializeControlStatement((ControlStatement) stmt);
			case WhileStatement -> serializeWhileStatement((WhileStatement) stmt);
			case DoWhileStatement -> serializeDoWhileStatement((DoWhileStatement) stmt);
			case ForStatement -> serializeForStatement((ForStatement) stmt);
			case ContinueStatement -> serializeContinueStatement((ContinueStatement) stmt);
			case BreakStatement -> serializeBreakStatement((BreakStatement) stmt);
			case ReturnStatement -> serializeReturnStatement((ReturnStatement) stmt);
			default -> throw new UnsupportedOperationException("JSONASTSerialize Unknown Statement Type: " + stmt.getClass().getSimpleName());
		};
	}

	private ObjectNode serializeExpressionStatement(ExpressionStatement stmt) {
		ObjectNode exprStmtNode = mapper.createObjectNode();
		exprStmtNode.put("type", stmt.getType().name());
		exprStmtNode.set("expression", serializeExpression(stmt.getExpression()));
		return exprStmtNode;
	}

	private ObjectNode serializeBlockStatement(BlockStatement stmt) {
		ArrayNode arrayNode = mapper.createArrayNode();

		for (Statement statement : stmt.getStatements()) {
			arrayNode.add(serializeStatement(statement));
		}

		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());
		node.put("statementCount", arrayNode.size());
		node.set("statements", arrayNode);
		return node;
	}

	private ObjectNode serializeVariableStatement(VariableStatement stmt) {
		// Variable Declarations
		ArrayNode varDecArrayNode = mapper.createArrayNode();

		for (VariableDeclaration variableDec : stmt.getVariableDeclarations()) {
			varDecArrayNode.add(serializeVariableDeclaration(variableDec));
		}

		// Variable Statement
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());

		if (stmt.getTypeQualifier() != null) {
			node.put("typeQualifier", stmt.getTypeQualifier().name());
		} else {
			node.putNull("typeQualifier");
		}

		node.put("datatype", stmt.getDatatype().name());
		node.set("variableDeclarations", varDecArrayNode);

		return node;
	}

	private ObjectNode serializeVariableDeclaration(VariableDeclaration variableDec) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", "VariableDeclaration");
		node.set("identifier", serializeIdentifierExpression(variableDec.getIdentifier()));

		if (variableDec.getInitializer() != null) {
			node.set("initializer", serializeExpression(variableDec.getInitializer()));
		} else {
			node.putNull("initializer");
		}

		return node;
	}

	private ObjectNode serializeControlStatement(ControlStatement stmt) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());
		node.set("condition", serializeExpression(stmt.getCondition()));
		node.set("consequent", serializeStatement(stmt.getConsequent()));

		if (stmt.getAlternate() != null) {
			node.set("alternate", serializeStatement(stmt.getAlternate()));
		} else {
			node.putNull("alternate");
		}

		return node;
	}

	private ObjectNode serializeWhileStatement(WhileStatement stmt) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());
		node.set("condition", serializeExpression(stmt.getCondition()));

		if (stmt.getBody() != null) {
			node.set("body", serializeStatement(stmt.getBody()));
		} else {
			node.putNull("body");
		}

		return node;
	}

	private ObjectNode serializeDoWhileStatement(DoWhileStatement stmt) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());
		node.set("condition", serializeExpression(stmt.getCondition()));

		if (stmt.getBody() != null) {
			node.set("body", serializeStatement(stmt.getBody()));
		} else {
			node.putNull("body");
		}

		return node;
	}

	private ObjectNode serializeForStatement(ForStatement stmt) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());

		if (stmt.getInitializerType() == ForStatement.ForInitializerType.VARIABLE_STATEMENT) {
			node.set("initializer", serializeVariableStatement((VariableStatement) stmt.getInitializer()));
		} else if (stmt.getInitializerType() == ForStatement.ForInitializerType.EXPRESSION) {
			node.set("initializer", serializeExpression((Expression) stmt.getInitializer()));
		} else {
			node.putNull("initializer");
		}

		if (stmt.getCondition() != null) {
			node.set("condition", serializeExpression(stmt.getCondition()));
		} else {
			node.putNull("condition");
		}

		if (stmt.getUpdate() != null) {
			node.set("update", serializeExpression(stmt.getUpdate()));
		} else {
			node.putNull("update");
		}

		if (stmt.getBody() != null) {
			node.set("body", serializeStatement(stmt.getBody()));
		} else {
			node.putNull("body");
		}

		return node;
	}

	private ObjectNode serializeContinueStatement(ContinueStatement stmt) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());
		return node;
	}

	private ObjectNode serializeBreakStatement(BreakStatement stmt) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());
		return node;
	}

	private ObjectNode serializeReturnStatement(ReturnStatement stmt) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", stmt.getType().name());

		if (stmt.getExpression() != null) {
			node.set("expression", serializeExpression(stmt.getExpression()));
		} else {
			node.putNull("expression");
		}

		return node;
	}

	private ObjectNode serializeExpression(Expression expr) {
		return 	switch (expr.getType()) {
			case LiteralExpression -> serializeLiteralExpression((LiteralExpression) expr);
			case IdentifierExpression -> serializeIdentifierExpression((IdentifierExpression) expr);
			case AssignmentExpression -> serializeAssignmentExpression((AssignmentExpression) expr);
			case UnaryExpression -> serializeUnaryExpression((UnaryExpression) expr);
			case BinaryExpression -> serializeBinaryExpression((BinaryExpression) expr);
			case TernaryExpression -> serializeTernaryExpression((TernaryExpression) expr);
			case FunctionCallExpression -> serializeFunctionCallExpression((FunctionCallExpression) expr);
			case PostfixExpression -> serializePostfixExpression((PostfixExpression) expr);
			default -> throw new UnsupportedOperationException("JSONASTSerialize Unknown Expression Type: " + expr.getClass().getSimpleName());
		};
	}

	private ObjectNode serializeLiteralExpression(LiteralExpression expr) {
		ObjectNode literalNode = mapper.createObjectNode();
		literalNode.put("type", expr.getType().name());

		Literal literal = expr.getLiteral();
		literalNode.put("literalType", literal.getType().name());

		switch (literal.getType()) {
			case INT32 -> literalNode.put("value", ((Int32Literal) literal).getValue());
			case UINT32 -> literalNode.put("value", ((Uint32Literal) literal).getValue());
			case FLOAT32 -> literalNode.put("value", ((Float32Literal) literal).getValue());
			case FLOAT64 -> literalNode.put("value", ((Float64Literal) literal).getValue());
			case CHAR8 -> literalNode.put("value", String.valueOf(((Char8Literal) literal).getValue()));
			case STR8 -> literalNode.put("value", ((Str8Literal) literal).getValue());
			case BOOL8 -> literalNode.put("value", ((Bool8Literal) literal).getValue());
			case NULL -> literalNode.putNull("value");
		}

		return literalNode;
	}

	private ObjectNode serializeIdentifierExpression(IdentifierExpression expr) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", expr.getType().name());
		node.put("value", expr.getIdentifier());
		return node;
	}

	private ObjectNode serializeAssignmentExpression(AssignmentExpression expr) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", expr.getType().name());
		node.put("operator", expr.getOperator().getSymbol());
		node.set("left", serializeExpression(expr.getLeft()));
		node.set("right", serializeExpression(expr.getRight()));
		return node;
	}

	private ObjectNode serializeUnaryExpression(UnaryExpression expr) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", expr.getType().name());
		node.put("operator", expr.getOperator().getSymbol());
		node.set("expression", serializeExpression(expr.getExpression()));
		return node;
	}

	private ObjectNode serializeBinaryExpression(BinaryExpression expr) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", expr.getType().name());
		node.put("operator", expr.getOperator().getSymbol());
		node.set("left", serializeExpression(expr.getLeft()));
		node.set("right", serializeExpression(expr.getRight()));
		return node;
	}

	private ObjectNode serializeTernaryExpression(TernaryExpression expr) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", expr.getType().name());
		node.set("condition", serializeExpression(expr.getCondition()));
		node.set("consequent", serializeExpression(expr.getConsequent()));
		node.set("alternate", serializeExpression(expr.getAlternate()));
		return node;
	}

	private ObjectNode serializeFunctionCallExpression(FunctionCallExpression expr) {
		ArrayNode arrayNode = mapper.createArrayNode();

		for (Expression arg : expr.getArguments()) {
			arrayNode.add(serializeExpression(arg));
		}

		ObjectNode node = mapper.createObjectNode();
		node.put("type", expr.getType().name());
		node.set("callee", serializeExpression(expr.getCallee()));
		node.set("arguments", arrayNode);
		return node;
	}

	private ObjectNode serializePostfixExpression(PostfixExpression expr) {
		ObjectNode node = mapper.createObjectNode();
		node.put("type", expr.getType().name());
		node.put("operator", expr.getOperator().getSymbol());
		node.set("expression", serializeExpression(expr.getExpression()));
		return node;
	}
}
