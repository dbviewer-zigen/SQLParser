/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import java.util.ArrayList;
import java.util.List;

public class ASTVisitor implements IVisitor {
	private List nodes = new ArrayList();

	private int index = -1;

	public Object visit(INode node, Object data) {
		setCurrentNode(node);
		node.childrenAccept(this, data);
		return data;
	}

	private void setCurrentNode(INode node) {
		node.setId(index);
		nodes.add(node);
		index++;
	}

	public INode findNode(int index) {
		if (index < nodes.size()) {
			return (INode) nodes.get(index);
		} else {
			return null;
		}
	}

	public int getIndex() {
		return index;
	}
}
