package com.krnl32.eye.sandbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.krnl32.eye.ast.Program;
import com.krnl32.eye.astserializer.serializers.JSONASTSerializer;
import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.parser.Parser;

import java.io.IOException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		Lexer lexer;
		//lexer = new Lexer("1; 123.11f; 1559; 9958.155; null; true; false; 'A'; \"Hello World\";");
		lexer = new Lexer("x; { var1; var2; } var3;");
		lexer = new Lexer("if (x) {x;}");
		lexer = new Lexer("if (x) { x; } else { y; }");
		lexer = new Lexer("if (x) x;");
		lexer = new Lexer("x++;");
		lexer = new Lexer("while(true);");
		lexer = new Lexer("while(true) {}");
		lexer = new Lexer("while(true) {x = 12;}");
		lexer = new Lexer("do ; while (y);");
		lexer = new Lexer("for(;;);");
		lexer = new Lexer("for(int32_t x; x > 10; x++) y;");
		lexer = new Lexer("for(int32_t x; x > 10; x++) { Hello; }");
		lexer = new Lexer("for(int32_t x = 12, y = 12; x > 10; x++) { Hello; }");
		lexer = new Lexer("for(int32_t x = 12, y, x = 13; x > 10; x++) { Hello; }");
		lexer = new Lexer("while(x) { if (y) break; }");
		lexer = new Lexer("while(x) { if (y) continue; }");
		lexer = new Lexer("while(x) { if (y) return; }");
		lexer = new Lexer("while(x) { if (y) return 12; }");
		lexer = new Lexer("int32_t x = 12;");
		lexer = new Lexer("function void empty() { while(x) { if (y) return 12; } }");
		
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
