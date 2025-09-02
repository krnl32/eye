package com.krnl32.eye.astserializer;

import com.krnl32.eye.ast.Program;

public interface ASTSerializer<T> {
	T serialize(Program program);
}
