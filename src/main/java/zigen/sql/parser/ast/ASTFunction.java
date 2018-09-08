/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;

public class ASTFunction extends ASTAlias {

	//private String functionName;

	public ASTFunction(String functionName, int offset, int length, int scope) {
		super(functionName, offset, length, scope);
		//parse(functionName);
	}


	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (getAliasName() != null) {
			sb.append(" AS ");
			sb.append(getAliasName());
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String getName() {
		StringBuffer sb = new StringBuffer();
		// TODO support Schema.Function
		sb.append(name);

//		if(hasAlias()){
//			sb.append(" AS ");
//			sb.append(getAliasName());
//		}
		return sb.toString();
	}

}
