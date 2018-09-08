/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;


public class ASTUnion extends ASTKeyword {

	private boolean isAll;

	public ASTUnion(int offset, int length, int scope) {
		super("union", offset, length, scope);
	}
	public ASTUnion(int offset, int length, int scope, boolean isAll) {
		super("union", offset, length, scope);
		this.isAll =isAll;
	}
	
	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getNodeClassName());
		if (isAll) {
			sb.append(" All");
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

	public boolean isAll() {
		return isAll;
	}
}
