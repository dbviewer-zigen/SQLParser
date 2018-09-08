/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;

public class ASTCaseCause extends ASTAlias {
	boolean isComplete;

	public ASTCaseCause(int offset, int length, int scope) {
		super("case", offset, length, scope);
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (getAliasName() != null) {
			sb.append(getAliasName());
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}
}
