package com.krnl32.eye.ast;

import com.krnl32.eye.ast.statement.Statement;

import java.util.Collections;
import java.util.List;

public class Program {
	private final List<Statement> topLevelStatements;

	public Program(List<Statement> topLevelStatements) {
		this.topLevelStatements = topLevelStatements;
	}

	public List<Statement> getTopLevelStatements() {
		return Collections.unmodifiableList(topLevelStatements);
	}

	@Override
	public String toString() {
		return "Program{" +
			"topLevelStatements=" + topLevelStatements +
			'}';
	}
}
