/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.Node;


public class ASTRoot extends Node {

	public ASTRoot() {
		super("root", 0, 0, 0);
	}

	public String toString() {
		return "Node"+ " text=\"" + name + "\"";
	}

	public String getNodeClassName() {
		return "Node";
	}

	
}
