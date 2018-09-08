/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

public interface IVisitor {

	public Object visit(INode node, Object data);

	public INode findNode(int index);

	public int getIndex();

}
