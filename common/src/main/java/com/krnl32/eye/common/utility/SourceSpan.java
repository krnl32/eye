package com.krnl32.eye.common.utility;

/*
 * Source Span
 * Line Number (Start)
 * Column Number (Start)
 * Start (Start Index in Source, Inclusive)
 * End (End Index in Source, Exclusive) *
 */
public final class SourceSpan {
	private final int line;
	private final int column;
	private final int start;
	private final int end;

	public SourceSpan(int line, int column, int start, int end) {
		this.line = line;
		this.column = column;
		this.start = start;
		this.end = end;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}

	@Override
	public String toString() {
		return "SourceSpan{" +
			"line=" + line +
			", column=" + column +
			", start=" + start +
			", end=" + end +
			'}';
	}
}
