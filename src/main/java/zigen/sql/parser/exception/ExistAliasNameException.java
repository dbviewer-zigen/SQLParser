/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.exception;


public class ExistAliasNameException extends ParserException {

	private static final long serialVersionUID = -8576842465833298464L;
	public static final String message = "Alias is exist.";
	
	public ExistAliasNameException(String target, int offfset, int length) {
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
