/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import zigen.sql.parser.INode;
import zigen.sql.parser.Node;

public class ASTOperator extends Node {
//public class ASTOperator extends ASTAlias {

	public static final String[] OPERATOR = { "!=", "<", "<=", "<>", "=", ">", ">=", "^=" };

	public static final String[] OPERATOR1 = { "+", "-" };

	public static final String[] OPERATOR2 = { "*", "/" };

	static Map map = new HashMap();

	static Map map1 = new HashMap();

	static Map map2 = new HashMap();
	static {
		for (int i = 0; i < OPERATOR.length; i++) {
			map.put(OPERATOR[i], OPERATOR[i]);
		}
		for (int i = 0; i < OPERATOR1.length; i++) {
			map1.put(OPERATOR1[i], OPERATOR1[i]);
		}
		for (int i = 0; i < OPERATOR2.length; i++) {
			map2.put(OPERATOR2[i], OPERATOR2[i]);
		}
	}

	public static int PRIORITY = 0;

	public static int PRIORITY_1 = 1; // + -

	public static int PRIORITY_2 = 2; // * /

	private int priority = PRIORITY;

	public ASTOperator(String s, int offset, int length, int scope) {
		super(s, offset, length, scope);

		if (map1.containsKey(getName())) {
			priority = PRIORITY_1;
		} else if (map2.containsKey(getName())) {
			priority = PRIORITY_2;
		} else if (map.containsKey(getName())) {
			priority = PRIORITY;
		}
	}

	public void addChild(INode n) {
		if (n != null) {
			if (children == null) {
				children = new ArrayList();
			}

			if (hasRightChild()) {

				System.err.println(n.getName() + ", " + n.getClass().getName());

			} else {
				children.add(n);
				n.setParent(this);
			}
		}
	}

	public int compare(ASTOperator ope) {
		return (getPriority() - ope.priority);
	}

	public boolean hasLeftChild() {
		return (children != null && children.size() >= 1);
	}

	public boolean hasRightChild() {
		return (children != null && children.size() == 2);
	}

	public int getPriority() {
		return priority;
	}


	public String toString() {
		return getNodeClassName() + " text=\"" + name + "\"";
	}

}
