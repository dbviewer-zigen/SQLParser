/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.exception;

public class SyntaxError extends ParserException {

	private static final long serialVersionUID = 6239196411314992154L;

	public SyntaxError(String message, String target, int offset, int length) {
		super(message, target, offset, length);
	}

}
