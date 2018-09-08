/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import zigen.sql.parser.ast.ASTAlias;
import zigen.sql.parser.ast.ASTCaseCause;
import zigen.sql.parser.ast.ASTExpression;
import zigen.sql.parser.ast.ASTFunction;
import zigen.sql.parser.ast.ASTInnerAlias;
import zigen.sql.parser.ast.ASTOperator;
import zigen.sql.parser.ast.ASTParentheses;
import zigen.sql.parser.ast.ASTRoot;
import zigen.sql.parser.ast.ASTSelectStatement;
import zigen.sql.parser.ast.ASTTable;

public class ASTVisitorPrint implements IVisitor {

	StringBuffer sb = new StringBuffer();

	boolean isShowAs = false;

	public INode findNode(int offset) {
		throw new UnsupportedOperationException("UnSupported Method");
	}

	public int getIndex() {
		throw new UnsupportedOperationException("UnSupported Method");
	}

	private void setAliasName(ASTAlias target) {
		if (target.hasAlias()) {
			if (isShowAs) {
				sb.append("AS ");
			}
			sb.append(target.getAliasName() + " ");
		}
	}

	public Object visit(INode node, Object data) {

		if (node instanceof ASTRoot || node instanceof ASTSelectStatement || node instanceof ASTInnerAlias || node instanceof ASTExpression) {

			node.childrenAccept(this, data);

		} else if (node instanceof ASTTable) {
			ASTTable table = (ASTTable) node;
			sb.append(table.getName() + " ");

			setAliasName(table);

			node.childrenAccept(this, data);

		} else if (node instanceof ASTFunction) {
			ASTFunction function = (ASTFunction) node;
			sb.append(function.getName() + ""); // not insert space
			node.childrenAccept(this, data);

			setAliasName(function);

		} else if (node instanceof ASTParentheses) {
			ASTParentheses p = (ASTParentheses) node;
			sb.append("(");
			node.childrenAccept(this, data);
			sb.deleteCharAt(sb.toString().length() - 1); // delete space before ")"
			sb.append(") ");

			setAliasName(p);

		} else if (node instanceof ASTOperator) {

			ASTOperator ope = (ASTOperator) node;
			if (ope.hasRightChild()) {

				node.getChild(0).accept(this, null);
				sb.append(node.getName() + " ");
				node.getChild(1).accept(this, null);

			} else if (ope.hasLeftChild()) {

				node.getChild(0).accept(this, null);
				sb.append(node.getName() + " ");

			} else {
				sb.append(node.getName() + " ");
				node.childrenAccept(this, data);
			}

		} else if (node instanceof ASTCaseCause) {
			ASTCaseCause cc = (ASTCaseCause) node;
			node.childrenAccept(this, data);
			sb.deleteCharAt(sb.toString().length() - 1); // delete space before ")"

			if (cc.isComplete()) {
				sb.append(" end ");
			}

			setAliasName(cc);

		} else {
			sb.append(node.getName() + " ");
			node.childrenAccept(this, data);
		}

		return data;
	}

	public void print() {
		System.out.println(sb.toString());
	}
}
