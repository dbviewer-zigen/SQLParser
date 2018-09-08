/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;



abstract public class ASTStatement extends ASTAlias {

	public ASTStatement(String id,  int offset, int length, int scope) {
		super(id, offset, length, scope);
	}
}
