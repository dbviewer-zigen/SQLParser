/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.exception;

public class NotFoundParentNodeException extends RuntimeException {

	private static final long serialVersionUID = 4888317521203173278L;

	public static final String message = " doesn't exist in the parents node.";

	String target = null;

	public NotFoundParentNodeException(String target) {
		this.target = target;
	}

	public String getMessage() {
		return target + message;
	}

	public String getTarget() {
		return target;
	}

}
