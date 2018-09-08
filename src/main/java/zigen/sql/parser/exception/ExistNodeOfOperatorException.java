/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.exception;


public class ExistNodeOfOperatorException extends ParserException {

	private static final long serialVersionUID = 1544585954088623113L;
	public static final String message = "Node of Operator is exist.";
	
	public ExistNodeOfOperatorException(String target, int offfset, int length) {
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
