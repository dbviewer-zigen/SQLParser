/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.exception;

public class UnexpectedTokenException extends ParserException {

	private static final long serialVersionUID = -4737836506773489542L;
	public static final String message = "SQLParser found unexpected Token.";
	
	public UnexpectedTokenException(String target, int offfset, int length) {
		super(message, target, offfset, length);
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
