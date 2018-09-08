/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;
import zigen.sql.parser.Node;

public class ASTLiteral extends Node {

	public ASTLiteral(String literal,int offset, int length, int scope) {
		super(literal, offset, length, scope);
	}

	public void addLiteral(String literal) {
		this.name += literal;
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
}
