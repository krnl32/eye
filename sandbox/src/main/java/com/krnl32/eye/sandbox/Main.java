package com.krnl32.eye.sandbox;

import com.krnl32.eye.common.core.Logger;
import com.krnl32.eye.parser.lexer.Lexer;
import com.krnl32.eye.parser.lexer.Token;
import com.krnl32.eye.parser.lexer.TokenType;

import java.util.List;

public class Main {
	public static void main(String[] args) {
		Lexer lexer;

//		Lexer lexer = new Lexer("123 456\n789");
		/*
			Token{type=LITERAL_INTEGER, value=123, span=SourceSpan{line=1, column=1, start=0, end=3}}
			Token{type=LITERAL_INTEGER, value=456, span=SourceSpan{line=1, column=5, start=4, end=7}}
			Token{type=NEWLINE, value=null, span=SourceSpan{line=1, column=8, start=7, end=8}}
			Token{type=LITERAL_INTEGER, value=789, span=SourceSpan{line=2, column=1, start=8, end=11}}
			Token{type=END_OF_FILE, value=null, span=SourceSpan{line=2, column=4, start=11, end=11}}
		 */

		//Lexer lexer = new Lexer("123.456 551 12.3 5.99");
		/*
			Token{type=LITERAL_FLOAT, value=123.456, span=SourceSpan{line=1, column=1, start=0, end=7}}
			Token{type=LITERAL_INTEGER, value=551, span=SourceSpan{line=1, column=9, start=8, end=11}}
			Token{type=LITERAL_FLOAT, value=12.3, span=SourceSpan{line=1, column=13, start=12, end=16}}
			Token{type=LITERAL_FLOAT, value=5.99, span=SourceSpan{line=1, column=18, start=17, end=21}}
			Token{type=END_OF_FILE, value=null, span=SourceSpan{line=1, column=22, start=21, end=21}}
		 */

		//Lexer lexer = new Lexer("123.12 56.12f 99.f 44f");

		//Lexer lexer = new Lexer("123.12 56.12f 99.f 44");

		// 0x1234 = 4660, 0b1000100111=551
		//lexer = new Lexer("0x1234\n0b1000100111");

		lexer = new Lexer("\"Hello World\"");

		List<Token> tokens = lexer.tokenize();
		if (tokens == null) {
			Logger.error("Tokenizer Failed");
			return;
		}

		for (var token : tokens) {
			System.out.println(token);
		}
	}
}
