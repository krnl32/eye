package com.krnl32.eye.semantic;

import com.krnl32.eye.ast.Program;
import com.krnl32.eye.ast.expression.*;
import com.krnl32.eye.ast.statement.*;
import com.krnl32.eye.common.diagnostic.DiagnosticCode;
import com.krnl32.eye.common.diagnostic.DiagnosticReporter;
import com.krnl32.eye.common.diagnostic.DiagnosticType;

public class NameResolver {
	private final Program ast;
	private final DiagnosticReporter diagnosticReporter;
	private Environment<DeclarationType> declarationEnvironment;

	public NameResolver(Program ast, DiagnosticReporter diagnosticReporter) {
		this.ast = ast;
		this.diagnosticReporter	= diagnosticReporter;
		this.declarationEnvironment = new Environment<>(null);
	}

	public boolean validate() {
		boolean valid = true;

		for (Statement stmt : ast.getTopLevelStatements()) {
			if (!validateStatement(stmt)) {
				valid = false;
			}
		}

		return valid;
	}

	private boolean validateStatement(Statement stmt) {
		return switch (stmt.getType()) {
			case ExpressionStatement -> validateExpressionStatement((ExpressionStatement) stmt);
			case BlockStatement -> validateBlockStatement((BlockStatement) stmt, true);
			case VariableStatement -> validateVariableStatement((VariableStatement) stmt);
			case ControlStatement -> validateControlStatement((ControlStatement) stmt);
			case WhileStatement -> validateWhileStatement((WhileStatement) stmt);
			case DoWhileStatement -> validateDoWhileStatement((DoWhileStatement) stmt);
			case ForStatement -> validateForStatement((ForStatement) stmt);
			case ContinueStatement -> validateContinueStatement((ContinueStatement) stmt);
			case BreakStatement -> validateBreakStatement((BreakStatement) stmt);
			case FunctionStatement -> validateFunctionStatement((FunctionStatement) stmt);
			case ReturnStatement -> validateReturnStatement((ReturnStatement) stmt);
			default -> {
				diagnosticReporter.reportError(DiagnosticType.NAME_RESOLVER, DiagnosticCode.UK000, "Unknown Statement Type: " + stmt.getClass().getSimpleName(), stmt.getSourceSpan());
				yield false;
			}
		};
	}

	private boolean validateExpressionStatement(ExpressionStatement stmt) {
		return validateExpression(stmt.getExpression());
	}

	private boolean validateBlockStatement(BlockStatement stmt, boolean newScope) {
		boolean valid = true;

		if (newScope) {
			beginBlockScope();
		}

		for (Statement statement : stmt.getStatements()) {
			if (!validateStatement(statement)) {
				valid = false;
			}
		}

		if (newScope) {
			endBlockScope();
		}

		return valid;
	}

	private boolean validateVariableStatement(VariableStatement stmt) {
		boolean valid = true;

		for (VariableDeclaration varDec : stmt.getVariableDeclarations()) {
			if (!validateVariableDeclaration(varDec)) {
				valid = false;
			}
		}

		return valid;
	}

	private boolean validateVariableDeclaration(VariableDeclaration varDec) {
		boolean valid = true;

		String identifier = varDec.getIdentifier();

		if (declarationEnvironment.has(identifier, false)) {
			diagnosticReporter.reportError(DiagnosticType.NAME_RESOLVER, DiagnosticCode.NR001, "Redeclaration of '" + identifier + "'", varDec.getSourceSpan());
			valid = false;
		}

		if (varDec.getInitializer() != null && !validateExpression(varDec.getInitializer())) {
			valid = false;
		}

		declarationEnvironment.define(identifier, DeclarationType.VARIABLE);

		return valid;
	}

	private boolean validateControlStatement(ControlStatement stmt) {
		boolean valid = validateExpression(stmt.getCondition());

		if (!validateStatement(stmt.getConsequent())) {
			valid = false;
		}

		if (stmt.getAlternate() != null && !validateStatement(stmt.getAlternate())) {
			valid = false;
		}

		return valid;
	}

	private boolean validateWhileStatement(WhileStatement stmt) {
		boolean valid = validateExpression(stmt.getCondition());

		if (stmt.getBody() != null && !validateStatement(stmt.getBody())) {
			valid = false;
		}

		return valid;
	}

	private boolean validateDoWhileStatement(DoWhileStatement stmt) {
		boolean valid = validateExpression(stmt.getCondition());

		if (stmt.getBody() != null && !validateStatement(stmt.getBody())) {
			valid = false;
		}

		return valid;
	}

	private boolean validateForStatement(ForStatement stmt) {
		boolean valid = true;

		if (stmt.getInitializerType() == ForStatement.ForInitializerType.VARIABLE_STATEMENT) {
			valid = validateStatement((VariableStatement) stmt.getInitializer());
		} else if (stmt.getInitializerType() == ForStatement.ForInitializerType.EXPRESSION) {
			valid = validateExpression((Expression) stmt.getInitializer());
		}

		if (stmt.getCondition() != null && !validateExpression(stmt.getCondition())) {
			valid = false;
		}

		if (stmt.getUpdate() != null && !validateExpression(stmt.getUpdate())) {
			valid = false;
		}

		if (stmt.getBody() != null && !validateStatement(stmt.getBody())) {
			valid = false;
		}

		return valid;
	}

	private boolean validateContinueStatement(ContinueStatement stmt) {
		return true;
	}

	private boolean validateBreakStatement(BreakStatement stmt) {
		return true;
	}

	private boolean validateFunctionStatement(FunctionStatement stmt) {
		boolean valid = true;

		String identifier = stmt.getIdentifier();

		if (declarationEnvironment.has(identifier, false)) {
			diagnosticReporter.reportError(DiagnosticType.NAME_RESOLVER, DiagnosticCode.NR001, "Redeclaration of '" + identifier + "'", stmt.getSourceSpan());
			valid = false;
		}

		declarationEnvironment.define(identifier, DeclarationType.FUNCTION);

		// Validate Function and its Parameters
		beginBlockScope();

		for (FunctionParameter param : stmt.getParameters()) {
			if (!validateFunctionParameter(param)) {
				valid = false;
			}
		}

		if (!validateBlockStatement((BlockStatement) stmt.getBody(), false)) {
			valid = false;
		}

		endBlockScope();

		return valid;
	}

	private boolean validateFunctionParameter(FunctionParameter param) {
		boolean valid = true;

		String identifier = param.getIdentifier();

		if (declarationEnvironment.has(identifier, false)) {
			diagnosticReporter.reportError(DiagnosticType.NAME_RESOLVER, DiagnosticCode.NR001, "Redeclaration of '" + identifier + "'", param.getSourceSpan());
			valid = false;
		}

		if (param.getInitializer() != null && !validateExpression(param.getInitializer())) {
			valid = false;
		}

		declarationEnvironment.define(identifier, DeclarationType.VARIABLE);

		return valid;
	}

	private boolean validateReturnStatement(ReturnStatement stmt) {
		return (stmt.getExpression() == null || validateExpression(stmt.getExpression()));
	}

	private boolean validateExpression(Expression expr) {
		return switch (expr.getType()) {
			case LiteralExpression -> validateLiteralExpression((LiteralExpression) expr);
			case IdentifierExpression -> validateIdentifierExpression((IdentifierExpression) expr);
			case UnaryExpression -> validateUnaryExpression((UnaryExpression) expr);
			case BinaryExpression -> validateBinaryExpression((BinaryExpression) expr);
			case TernaryExpression -> validateTernaryExpression((TernaryExpression) expr);
			case AssignmentExpression -> validateAssignmentExpression((AssignmentExpression) expr);
			case FunctionCallExpression -> validateFunctionCallExpression((FunctionCallExpression) expr);
			case PostfixExpression -> validatePostfixExpression((PostfixExpression) expr);
			default -> {
				diagnosticReporter.reportError(DiagnosticType.NAME_RESOLVER, DiagnosticCode.UK001, "Unknown Expression Type: " + expr.getClass().getSimpleName(), expr.getSourceSpan());
				yield false;
			}
		};
	}

	private boolean validateLiteralExpression(LiteralExpression expr) {
		return true;
	}

	private boolean validateIdentifierExpression(IdentifierExpression expr) {
		if (!declarationEnvironment.has(expr.getIdentifier(), true)) {
			diagnosticReporter.reportError(DiagnosticType.NAME_RESOLVER, DiagnosticCode.NR000, "Variable '" + expr.getIdentifier() + "' Was Not Declared in this Scope", expr.getSourceSpan());
			return false;
		}

		return true;
	}

	private boolean validateUnaryExpression(UnaryExpression expr) {
		return validateExpression(expr.getExpression());
	}

	private boolean validateBinaryExpression(BinaryExpression expr) {
		boolean valid = validateExpression(expr.getLeft());

		if (!validateExpression(expr.getRight())) {
			valid = false;
		}

		return valid;
	}

	private boolean validateTernaryExpression(TernaryExpression expr) {
		boolean valid = validateExpression(expr.getCondition());

		if (!validateExpression(expr.getConsequent())) {
			valid = false;
		}

		if (!validateExpression(expr.getAlternate())) {
			valid = false;
		}

		return valid;
	}

	private boolean validateAssignmentExpression(AssignmentExpression expr) {
		boolean valid = validateExpression(expr.getLeft());

		if (!validateExpression(expr.getRight())) {
			valid = false;
		}

		return valid;
	}

	private boolean validateFunctionCallExpression(FunctionCallExpression expr) {
		boolean valid = validateExpression(expr.getCallee());

		for (Expression argument : expr.getArguments()) {
			if (!validateExpression(argument)) {
				valid = false;
			}
		}

		return valid;
	}

	private boolean validatePostfixExpression(PostfixExpression expr) {
		return validateExpression(expr.getExpression());
	}

	private void beginBlockScope() {
		declarationEnvironment = new Environment<>(declarationEnvironment);
	}

	private void endBlockScope() {
		declarationEnvironment = declarationEnvironment.getParent();
	}
}
