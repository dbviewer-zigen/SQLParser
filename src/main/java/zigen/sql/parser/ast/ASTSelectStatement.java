/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;


public class ASTSelectStatement extends ASTStatement {

	public ASTSelectStatement(int offset, int length, int scope) {
		super("SelectStatement", offset, length, scope);
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);

		if (getAliasName() != null) {
			sb.append(" ");
			sb.append(getAliasName());
		} else {
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

}
