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
import zigen.sql.parser.ast.ASTCreateStatement;
import zigen.sql.parser.ast.ASTDeleteStatement;
import zigen.sql.parser.ast.ASTDropStatement;
import zigen.sql.parser.ast.ASTExpression;
import zigen.sql.parser.ast.ASTInsertStatement;
import zigen.sql.parser.ast.ASTParentheses;
import zigen.sql.parser.ast.ASTSelectStatement;
import zigen.sql.parser.ast.ASTStatement;
import zigen.sql.parser.ast.ASTUpdateStatement;
import zigen.sql.parser.exception.NotFoundParentNodeException;
import zigen.sql.parser.exception.ParserException;

public class Node implements INode {

	protected INode parent;

	protected List children;

	protected String name;

	protected int offset;

	protected int length;

	protected int id;

	protected int scope;


	/*
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!super.equals(o)) {
			return false;
		}
		if (o == null) {
			return false;
		}
		if (o.getClass() != getClass()) {
			return false;
		}
		Node castedObj = (Node) o;
		return ((this.children == null ? castedObj.children == null : this.children.equals(castedObj.children))
				&& (this.id == castedObj.id));
	}*/


	public Node(String name) {
		this(name, 0, 0, ISqlParser.SCOPE_DEFAULT);
	}


	public Node(String name, int offset, int length, int scope) {
		this.name = name;
		this.offset = offset;
		this.length = length;
		this.scope = scope;
	}

//	public Node(String name, int offset, int length) {
//		this(name, offset, length, ISqlParser.SCOPE_DEFAULT);
//	}



	public String getName() {
		return name;
	}

	public void setParent(INode n) {
		parent = n;
	}

	public INode getParent() {
		return parent;
	}

	public void addChild(INode n) {
		if (n != null) {
			if (children == null) {
				children = new ArrayList();
			}
			children.add(n);
			n.setParent(this);

		}
	}

	public void removeChild(INode child) {
		children.remove(child);
		if (child != null) {
			child.setParent(null);
		}
	}

	public INode getChild(int i) {
		return (INode) children.get(i);
	}

	public INode getLastChild() {
		if (children == null || children.size() == 0) {
			return null;
		} else {
			return (INode) children.get(children.size() - 1);
		}
	}

	public int getChildrenSize() {
		return (children == null) ? 0 : children.size();
	}

	public List getChildren() {
		return children;
	}

	public Object accept(IVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public Object childrenAccept(IVisitor visitor, Object data) {
		try {
			if (children != null) {
				for (Iterator iter = children.iterator(); iter.hasNext();) {
					INode node = (INode) iter.next();
					node.accept(visitor, data);

				}
			}
		} catch (RuntimeException e) {
			;
		}
		return data;
	}

	public String getNodeClassName() {
		String name = getClass().getName();
		return name = name.substring(name.lastIndexOf(".") + 1);
	}

	public String toString() {
//		return getNodeClassName() + " text=\"" + name + "\"";
		return getNodeClassName() + " text=\"" + getName() + "\"";
	}

	public String toString(String prefix) {
		return prefix + toString();
	}

	public void dump(String prefix) {
		System.out.println(toString(prefix));
		if (children != null) {
			for (int i = 0; i < children.size(); ++i) {
				Node n = (Node) children.get(i);
				if (n != null) {
					n.dump(prefix + " ");
				}
			}
		}
	}

	public ASTStatement getASTStatement() throws ParserException {
		return getASTStatement(this);
	}

	private ASTStatement getASTStatement(INode node) throws ParserException {
		if (node instanceof ASTStatement) {
			return (ASTStatement) node;
		} else {
			if (node.getParent() != null) {
				return getASTStatement(node.getParent());
			} else {
				return null;
			}
		}
	}

	public ASTSelectStatement getASTSelectStatement() throws ParserException {
		return getASTSelectStatement(this);
	}

	private ASTSelectStatement getASTSelectStatement(INode node) throws ParserException {
		if (node instanceof ASTSelectStatement) {
			return (ASTSelectStatement) node;
		} else {
			if (node.getParent() != null) {
				return getASTSelectStatement(node.getParent());
			} else {
				throw new NotFoundParentNodeException("ASTSelectStatement");
			}
		}
	}

	public ASTInsertStatement getASTInsertStatement() throws ParserException {
		return getASTInsertStatement(this);
	}

	private ASTInsertStatement getASTInsertStatement(INode node) throws ParserException {
		if (node instanceof ASTInsertStatement) {
			return (ASTInsertStatement) node;
		} else {
			if (node.getParent() != null) {
				return getASTInsertStatement(node.getParent());
			} else {
				throw new NotFoundParentNodeException("ASTInsertStatement");
			}
		}
	}

	public ASTUpdateStatement getASTUpdateStatement() throws ParserException {
		return getASTUpdateStatement(this);
	}

	private ASTUpdateStatement getASTUpdateStatement(INode node) throws ParserException {
		if (node instanceof ASTUpdateStatement) {
			return (ASTUpdateStatement) node;
		} else {
			if (node.getParent() != null) {
				return getASTUpdateStatement(node.getParent());
			} else {
				throw new NotFoundParentNodeException("ASTUpdateStatement");
			}
		}
	}

	public ASTDeleteStatement getASTDeleteStatement() throws ParserException {
		return getASTDeleteStatement(this);
	}

	private ASTDeleteStatement getASTDeleteStatement(INode node) throws ParserException {
		if (node instanceof ASTDeleteStatement) {
			return (ASTDeleteStatement) node;
		} else {
			if (node.getParent() != null) {
				return getASTDeleteStatement(node.getParent());
			} else {
				throw new NotFoundParentNodeException("ASTDeleteStatement");
			}
		}
	}

	public ASTCreateStatement getASTCreateStatement() throws ParserException {
		return getASTCreateStatement(this);
	}

	private ASTCreateStatement getASTCreateStatement(INode node) throws ParserException {
		if (node instanceof ASTCreateStatement) {
			return (ASTCreateStatement) node;
		} else {
			if (node.getParent() != null) {
				return getASTCreateStatement(node.getParent());
			} else {
				throw new NotFoundParentNodeException("ASTCreateStatement");
			}
		}
	}

	public ASTDropStatement getASTDropStatement() throws ParserException {
		return getASTDropStatement(this);
	}

	private ASTDropStatement getASTDropStatement(INode node) throws ParserException {
		if (node instanceof ASTDropStatement) {
			return (ASTDropStatement) node;
		} else {
			if (node.getParent() != null) {
				return getASTDropStatement(node.getParent());
			} else {
				throw new NotFoundParentNodeException("ASTDropStatement");
			}
		}
	}

	public ASTParentheses getASTParentheses() throws ParserException {
		return getASTParentheses(this);
	}

	private ASTParentheses getASTParentheses(INode node) throws ParserException {
		if (node instanceof ASTParentheses) {
			return (ASTParentheses) node;
		} else {
			if (node.getParent() != null) {
				return getASTParentheses(node.getParent());
			} else {
				return null;
			}
		}
	}

	public ASTExpression getASTExpression() throws ParserException {
		return getASTExpression(this);
	}

	private ASTExpression getASTExpression(INode node) throws ParserException {
		if (node instanceof ASTExpression) {
			return (ASTExpression) node;
// <-- do not get over Statement
		} else if(node instanceof ASTStatement){
			return null;
// -->			
		} else {
			if (node.getParent() != null) {
				return getASTExpression(node.getParent());
			} else {
				return null;
			}
		}
	}

	public ASTAlias getASTAlias() throws ParserException {
		return getASTAlias(this);
	}

	private ASTAlias getASTAlias(INode node) throws ParserException {
		if (node instanceof ASTAlias) {
			return (ASTAlias) node;					
		} else {
			if (node.getParent() != null) {
				return getASTAlias(node.getParent());
			} else {
				return null;
			}
		}
	}


	public void addChildToStatement(INode addNode){
		ASTStatement st = getASTStatement();
		if(st != null) st.addChild(addNode);
	}

	public void addChildToStatementParent(INode addNode){
		ASTStatement st = getASTStatement();
		if(st != null && st.getParent() != null){
			st.getParent().addChild(addNode);
		}
	}


	public INode getChild(String nodeName) {
		if (children != null) {
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				INode n = (INode) iter.next();
				if (n.getNodeClassName().equals(nodeName)) {
					return n;
				}

			}
		}
		return null;
	}

	public final int getOffset() {
		return offset;
	}

	public final int getLength() {
		return length;
	}

	public final int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public final int getScope(){
		return scope;
	}
}
