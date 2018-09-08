/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;

public class ASTExpression extends ASTAlias {

	public ASTExpression(int offset, int length, int scope) {
		super("expression", offset, length, scope);
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (hasAlias()) {
			sb.append(" ");
			sb.append(aliasNode.getName());
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

}
