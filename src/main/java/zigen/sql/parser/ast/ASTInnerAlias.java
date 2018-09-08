/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.Node;

public class ASTInnerAlias extends Node {

	public ASTInnerAlias(String name, int offset, int length, int scope) {
		super(name, offset, length, scope);
	}

}
