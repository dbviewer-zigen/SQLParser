/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;
import zigen.sql.parser.Node;

public class ASTTarget extends Node {

	private String schemaName;

	private String createName;

	public ASTTarget(String plsqlName,int offset, int length, int scope) {
		super(plsqlName, offset, length, scope);
		parse(plsqlName);
	}

	private void parse(String tableName) {
		String[] strs = tableName.split("[.]");
		if (strs.length == 2) {
			this.schemaName = strs[0];
			this.createName = strs[1];

		} else if (strs.length == 1) {
			this.createName = strs[0];
		}


	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (schemaName != null) {
			sb.append(schemaName);
			sb.append(".");
		}
		if (createName != null) {
			sb.append(createName);
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String getSchemaName() {
		return schemaName;
	}

	public String getCreateName() {
		return createName;
	}

}
