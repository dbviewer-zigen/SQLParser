/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import java.util.Map;
import java.util.TreeMap;

public class ASTVisitor2 implements IVisitor {
	private Map map = new TreeMap();

	private int index = -1;

	public Object visit(INode node, Object data) {
		setCurrentNode(node);
		node.childrenAccept(this, data);
		return data;
	}

	private void setCurrentNode(INode node) {
		index++;
		node.setId(index);
		map.put(new Integer(node.getOffset()), node);
	}

	public INode findNodeByOffset(int offset) {
		return (INode)map.get(new Integer(offset));
	}

	public INode findNodeByOffset(int offset, int length) {
		INode node = findNodeByOffset(offset);
		if(node != null){
			if(node.getLength() == length){
				return node;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	public INode findNode(int offset) {
		throw new UnsupportedOperationException("UnSupported Method");
	}

	public int getIndex() {
		throw new UnsupportedOperationException("UnSupported Method");
	}
}
