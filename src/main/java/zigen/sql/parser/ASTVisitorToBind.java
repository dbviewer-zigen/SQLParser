/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import zigen.sql.parser.ast.ASTAlias;
import zigen.sql.parser.ast.ASTBind;
import zigen.sql.parser.ast.ASTCaseCause;
import zigen.sql.parser.ast.ASTExpression;
import zigen.sql.parser.ast.ASTFunction;
import zigen.sql.parser.ast.ASTInnerAlias;
import zigen.sql.parser.ast.ASTOperator;
import zigen.sql.parser.ast.ASTOutfile;
import zigen.sql.parser.ast.ASTParentheses;
import zigen.sql.parser.ast.ASTRoot;
import zigen.sql.parser.ast.ASTSelectStatement;
import zigen.sql.parser.ast.ASTTable;

public class ASTVisitorToBind implements IVisitor {

	List list = null;

	boolean isShowAs = false;

	public ASTVisitorToBind(){
	}


	public INode findNode(int offset) {
		throw new UnsupportedOperationException("UnSupported Method");
	}

	public int getIndex() {
		throw new UnsupportedOperationException("UnSupported Method");
	}


	public Object visit(INode node, Object data) {
		if (node instanceof ASTBind) {
			if(list == null){
				list = new ArrayList();
			}
			list.add(node);
		}		
		node.childrenAccept(this, data);
		return data;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if(list != null){
			int i = 0;
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ASTBind bind = (ASTBind) iterator.next();
				if(i > 0){
					sb.append(", ");
				}
				sb.append(bind.toString());
				i++;
			}
		}
		return sb.toString();
	}

	public void print(){
		if(list != null){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				ASTBind bind = (ASTBind) iterator.next();
				System.out.println(bind.toString());
			}
		}
	}
}
