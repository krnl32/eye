package com.krnl32.eye.parser.lexer;

public class NumberUtility {
	public static byte parseUnsignedByte(String s) throws NumberFormatException {
		int value = Integer.parseInt(s);
		if (value < 0 || value > 255) {
			throw new NumberFormatException("For input string: " + value);
		}
		return (byte) value;
	}

	public static short parseUnsignedShort(String s) throws NumberFormatException {
		int value = Integer.parseInt(s);
		if (value < 0 || value > 65535) {
			throw new NumberFormatException("For input string: " + value);
		}
		return (short) value;
	}
}
