/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.INode;
import zigen.sql.parser.Node;
import zigen.sql.parser.exception.ExistAliasNameException;

abstract public class ASTAlias extends Node {

	protected boolean alias;

	protected INode aliasNode;

	protected boolean updatableAliasName = true;

	public ASTAlias(String id, int offset, int length, int scope) {
		super(id, offset, length, scope);
	}

	public String getAliasName() {
		return aliasNode == null ? null : aliasNode.getName();
	}

	public void setAliasName(String aliasName, int offset, int length) {
		if (hasAlias()) {
			throw new ExistAliasNameException(aliasName, offset, length);
		}

		if (updatableAliasName) {
			aliasNode = new ASTInnerAlias(aliasName, offset, length, scope);
			this.addChild(aliasNode);
		}
		alias = true;
	}

	public boolean hasAlias() {
		return alias;
	}

	public boolean isUpdatableAliasName() {
		return updatableAliasName;
	}

	public void setUpdatableAliasName(boolean updatable) {
		this.updatableAliasName = updatable;
	}

	public int getAliasLength() {
		return aliasNode == null ? 0 : aliasNode.getLength();
	}

	public int getAliasOffset() {
		return aliasNode == null ? 0 : aliasNode.getOffset();
	}

}
