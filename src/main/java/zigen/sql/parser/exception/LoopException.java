/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.exception;

public class LoopException extends ParserException {

	private static final long serialVersionUID = 4665013740576449878L;

	public static final String message = "SQLParser Loop error.";
	
	int maxLoop = 0;
	
	public LoopException(int maxLoop) {
		super(message, "", 0, 0);
		this.maxLoop = maxLoop;
	}
	
	public String getMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append(message);	
		sb.append(" Max same word is ");
		sb.append(maxLoop);
		return sb.toString();
	}

}
