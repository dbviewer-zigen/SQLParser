/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

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
import zigen.sql.parser.exception.ParserException;

public interface INode {

	public void setParent(INode n);

	public INode getParent();

	public void addChild(INode n);

	public INode getChild(int i);

	public int getChildrenSize();

	public INode getLastChild();

	public List getChildren();

	public int getOffset();

	public int getLength();

	public Object accept(IVisitor visitor, Object data);

	public Object childrenAccept(IVisitor visitor, Object data);

	public void removeChild(INode child);

	public String getNodeClassName();

	public String getName();

	public ASTStatement getASTStatement() throws ParserException;

	public ASTSelectStatement getASTSelectStatement() throws ParserException;

	public ASTInsertStatement getASTInsertStatement() throws ParserException;

	public ASTUpdateStatement getASTUpdateStatement() throws ParserException;

	public ASTDeleteStatement getASTDeleteStatement() throws ParserException;

	public ASTCreateStatement getASTCreateStatement() throws ParserException;

	public ASTDropStatement getASTDropStatement() throws ParserException;

	public ASTParentheses getASTParentheses() throws ParserException;
	
	public ASTAlias getASTAlias() throws ParserException;
	
	public ASTExpression getASTExpression() throws ParserException;
	
	public void addChildToStatement(INode addNode);
	
	public void addChildToStatementParent(INode addNode);

	public INode getChild(String nodeName);
	
	public void setId(int id);
	
	public int getId();
	
	public int getScope();
}
