package com.krnl32.eye.sandbox;

import com.krnl32.eye.ast.Program;
import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.parser.Parser;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Lexer lexer = new Lexer("1; 123.11f; 1559; 9958.155; null; true; false; 'A'; \"Hello World\";");

		List<Token> tokens = lexer.tokenize();
		if (tokens == null) {
			Logger.error("Tokenizer Failed");
			return;
		}

		System.out.println("Tokens Start: ");
		for (var token : tokens) {
			System.out.println(token);
		}
		System.out.println("Tokens END: ");


		Parser parser = new Parser(tokens);

		Program program = parser.parse();
		if (program == null) {
			Logger.error("Parser Failed");
			return;
		}

		System.out.println("AST Start: ");
		System.out.println(program);
		System.out.println("AST END: ");
	}
}
