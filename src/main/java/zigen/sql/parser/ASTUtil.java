/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import java.util.List;

public class ASTUtil {


	public static INode findParent(INode node, String type) {
		if (node == null || type.equals(node.getNodeClassName())) {
			return node;
		} else {
			return findParent((INode) node.getParent(), type);
		}

	}

	public static INode findChildDepth(INode current, String key) {
		List list = current.getChildren();
		if (list == null)
			return null;

		for (int i = 0; i < list.size(); i++) {
			if (current.getChild(i).getNodeClassName().equals(key))
				return current.getChild(i);

			INode node = findChildDepth(current.getChild(i), key);
			if (node != null)
				return node;
		}

		return null;
	}

	public static INode findChildWide(INode current, String key) {
		List list = current.getChildren();
		if (list == null)
			return null;

		for (int i = 0; i < list.size(); i++) {
			if (current.getChild(i).getNodeClassName().equals(key))
				return current.getChild(i);
		}

		for (int i = 0; i < list.size(); i++) {
			INode node = findChildWide(current.getChild(i), key);
			if (node != null)
				return node;
		}

		return null;
	}
}
