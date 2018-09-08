/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;

public class ASTTable extends ASTAlias {

	private String schemaName;

	private String tableName;

	public ASTTable(String tableName,int offset, int length, int scope) {
		super(tableName, offset, length, scope);
		parse(tableName);
	}

	private void parse(String tableName) {
		String[] strs = tableName.split("[.]");
		if (strs.length == 2) {
			this.schemaName = strs[0];
			this.tableName = strs[1];

		} else if (strs.length == 1) {
			this.tableName = strs[0];
		}
	}

	public String getName() {
		StringBuffer sb = new StringBuffer();
		if (schemaName != null) {
			sb.append(schemaName);
			sb.append(".");
		}
		if (tableName != null) {
			sb.append(tableName);
		}

		return sb.toString();
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getName());
		if(hasAlias()){
			sb.append(" AS ");
			sb.append(getAliasName());
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public String getSchemaName() {
		return schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	public String getAliasName() {
		if (hasAlias()) {
			return super.getAliasName();
		} else {
			return tableName;
		}
	}
}
