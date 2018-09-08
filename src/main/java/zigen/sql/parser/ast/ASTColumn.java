/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;

public class ASTColumn extends ASTAlias {

	private String schemaName;

	private String tableName;

	private String columnName;

	private boolean isConcating;

	private boolean isOuterJoin; // for Oracle

	public ASTColumn(String columnName, int offset, int length, int scope) {
		super(columnName, offset, length, scope);
		parse(columnName);
	}

	public void addConcat(String token, int _offset, int _length){
		StringBuffer sb = new StringBuffer(columnName).append(token);
		parse(sb.toString());

		int lastOffset = this.offset + this.length;
	    int space = _offset - lastOffset;
	    this.length = this.length + space + _length;

	    this.isConcating = true;
	}

	// for Oracale "(+)"
	public void addOuterJoin(String token, int _offset, int _length){
		StringBuffer sb = new StringBuffer(columnName).append(token);
		parse(sb.toString());

		int lastOffset = this.offset + this.length;
	    int space = _offset - lastOffset;
	    this.length = this.length + space + _length;

	    this.isOuterJoin = true;
	}

	public void addColumn(String token, int _offset, int _length){
		StringBuffer sb = new StringBuffer(columnName).append(token);
		parse(sb.toString());

		int lastOffset = this.offset + this.length;
	    int space = _offset - lastOffset;
	    this.length = this.length + space + _length;

	    this.isConcating = false;
	}

	private void parse(String columnName) {

		if (columnName.endsWith(".")) {
			columnName += "[dummy]"; // for "COL.*"
		}

		String[] strs = columnName.split("[.]");
		if (strs.length == 3) {
			this.schemaName = strs[0];
			this.tableName = strs[1];
			this.columnName = strs[2];

		} else if (strs.length == 2) {
			this.tableName = strs[0];
			this.columnName = strs[1];

		} else if (strs.length == 1) {
			this.columnName = strs[0];

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
			sb.append(".");
		}
		sb.append(columnName);

//		if(isOuterJoin){
//			sb.append(" (+)");
//		}

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

	public String getColumnName() {
		return columnName;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public String getTableName() {
		return tableName;
	}

	/**
	 * for "COL.*"
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAliasName() {
		if (hasAlias()) {
			return super.getAliasName();
		} else {
			return columnName;
		}
	}

	public boolean isConcating() {
		return isConcating;
	}

	public void setConcating(boolean isConcating) {
		this.isConcating = isConcating;
	}

	public boolean isOuterJoin() {
		return isOuterJoin;
	}

//	public void setOuterJoint(boolean isJoin) {
//		this.isOuterJoin = isJoin;
//	}
}
