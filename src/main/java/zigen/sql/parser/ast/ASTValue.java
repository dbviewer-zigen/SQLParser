/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;

public class ASTValue extends ASTColumn {

	public ASTValue(String value, int offset, int length, int scope) {
		super(value, offset, length, scope);
	}
	

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
