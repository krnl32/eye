package com.krnl32.eye.ast;

import com.krnl32.eye.ast.statement.TopLevelStatement;

import java.util.Collections;
import java.util.List;

public class Program {
	private final List<TopLevelStatement> topLevelStatements;

	public Program(List<TopLevelStatement> topLevelStatements) {
		this.topLevelStatements = topLevelStatements;
	}

	public List<TopLevelStatement> getTopLevelStatements() {
		return Collections.unmodifiableList(topLevelStatements);
	}

	@Override
	public String toString() {
		return "Program{" +
			"topLevelStatements=" + topLevelStatements +
			'}';
	}
}
