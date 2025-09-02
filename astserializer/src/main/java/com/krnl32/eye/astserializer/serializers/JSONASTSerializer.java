package com.krnl32.eye.astserializer.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krnl32.eye.ast.Program;
import com.krnl32.eye.ast.expression.BinaryExpression;
import com.krnl32.eye.ast.expression.Expression;
import com.krnl32.eye.ast.expression.LiteralExpression;
import com.krnl32.eye.ast.literal.*;
import com.krnl32.eye.ast.statement.ExpressionStatement;
import com.krnl32.eye.ast.statement.Statement;
import com.krnl32.eye.ast.statement.TopLevelStatement;
import com.krnl32.eye.astserializer.ASTSerializer;

import java.util.List;

public class JSONASTSerializer implements ASTSerializer<ObjectNode> {
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public ObjectNode serialize(Program program) {
		ObjectNode programNode = mapper.createObjectNode();
		programNode.put("type", "Program");
		programNode.put("topLevelStatementSize", program.getTopLevelStatements().size());
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
			default -> throw new UnsupportedOperationException("JSONASTSerialize Unknown Statement Type: " + stmt.getClass().getSimpleName());
		};
	}

	private ObjectNode serializeExpressionStatement(ExpressionStatement exprStmt) {
		ObjectNode exprStmtNode = mapper.createObjectNode();
		exprStmtNode.put("type", exprStmt.getType().name());
		exprStmtNode.set("expression", serializeExpression(exprStmt.getExpression()));
		return exprStmtNode;
	}

	private ObjectNode serializeExpression(Expression expr) {
		return 	switch (expr.getType()) {
			case LiteralExpression -> serializeLiteralExpression((LiteralExpression) expr);
			case BinaryExpression -> serializeBinaryExpression((BinaryExpression) expr);
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
			case CHAR8 -> literalNode.put("value", ((Char8Literal) literal).getValue());
			case STR8 -> literalNode.put("value", ((Str8Literal) literal).getValue());
			case BOOL8 -> literalNode.put("value", ((Bool8Literal) literal).getValue());
			case NULL -> literalNode.putNull("value");
		}

		return literalNode;
	}

	private ObjectNode serializeBinaryExpression(BinaryExpression expr) {
		ObjectNode binaryNode = mapper.createObjectNode();
		binaryNode.put("type", expr.getType().name());
		binaryNode.put("operator", expr.getOperatorType().name());
		binaryNode.set("left", serializeExpression(expr.getLeft()));
		binaryNode.set("right", serializeExpression(expr.getRight()));
		return binaryNode;
	}
}
