/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.exception;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 8003489464393496383L;

	String message = null;

	String target = null;

	int offset;

	int length;

	public ParserException(String message, String target, int offset, int length) {
		super(message);

		this.message = message;
		this.target = target;
		this.offset = offset;
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public int getOffset() {
		return offset;
	}

	public String getTarget() {
		return target;
	}

	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append(message);
		sb.append("\"");
		sb.append(target);
		sb.append("\"");
		
		return sb.toString();
	}

}
