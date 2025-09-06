package com.krnl32.eye.sandbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krnl32.eye.ast.Program;
import com.krnl32.eye.astserializer.serializers.JSONASTSerializer;
import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.parser.Parser;

import java.util.List;

public class Main {
	public static void main(String[] args) throws JsonProcessingException {
		Lexer lexer;

		//lexer = new Lexer("1; 123.11f; 1559; 9958.155; null; true; false; 'A'; \"Hello World\";");
		lexer = new Lexer("12; 55.14f; 99.995; 'C'; \"Hello World\";");
		lexer = new Lexer("1 + 2 + 3;");
		lexer = new Lexer("1,2,3;");
		lexer = new Lexer("x = y = 3;");
		lexer = new Lexer("true ? y = 5 : 1;");
		lexer = new Lexer("1 + 1; 2 - 2;");
		lexer = new Lexer("1 * 1; 2 / 2; 3 % 3;");
		lexer = new Lexer("x << 5; 10 >> 5;");
		lexer = new Lexer("1 < 2; x <= 10; 55 > 10; y >= 99;");
		lexer = new Lexer("x == 12; t != 9;");
		lexer = new Lexer("x | y;");
		lexer = new Lexer("x ^ y;");
		lexer = new Lexer("x && y;");
		lexer = new Lexer("x || y;");
		lexer = new Lexer("x & y;");
		lexer = new Lexer("+1;");
		lexer = new Lexer("-5;");
		lexer = new Lexer("!5;");
		lexer = new Lexer("~5;");
		lexer = new Lexer("++5;");
		lexer = new Lexer("--x;");
		lexer = new Lexer("1++;");
		lexer = new Lexer("x++;");
		lexer = new Lexer("+x;");
		lexer = new Lexer("func();");
		lexer = new Lexer("func(1);");
		lexer = new Lexer("func(1,x);");
		lexer = new Lexer("func(1,x=5, 'A');");

		List<Token> tokens = lexer.tokenize();
		if (tokens == null) {
			Logger.error("Tokenizer Failed");
			return;
		}

//		System.out.println("Tokens Start: ");
//		for (var token : tokens) {
//			System.out.println(token);
//		}
//		System.out.println("Tokens END: ");


		Parser parser = new Parser(tokens);
		System.out.println("Parsing: \n");

		Program program = parser.parse();
		if (program == null) {
			Logger.error("Parser Failed");
			return;
		}

//		System.out.println("AST Start: ");
//		System.out.println(program);
//		System.out.println("AST END: ");

		JSONASTSerializer serializer = new JSONASTSerializer();
		ObjectNode data = serializer.serialize(program);
		System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data));
	}
}
