/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import java.util.HashMap;
import java.util.Map;

import zigen.sql.format.SqlFormatRule;
import zigen.sql.parser.ast.ASTAlias;
import zigen.sql.parser.ast.ASTBind;
import zigen.sql.parser.ast.ASTCase;
import zigen.sql.parser.ast.ASTCaseCause;
import zigen.sql.parser.ast.ASTColumn;
import zigen.sql.parser.ast.ASTComma;
import zigen.sql.parser.ast.ASTCreateStatement;
import zigen.sql.parser.ast.ASTDeleteStatement;
import zigen.sql.parser.ast.ASTDropStatement;
import zigen.sql.parser.ast.ASTElse;
import zigen.sql.parser.ast.ASTExpression;
import zigen.sql.parser.ast.ASTFrom;
import zigen.sql.parser.ast.ASTFunction;
import zigen.sql.parser.ast.ASTGroupby;
import zigen.sql.parser.ast.ASTHavingBy;
import zigen.sql.parser.ast.ASTInnerAlias;
import zigen.sql.parser.ast.ASTInsertStatement;
import zigen.sql.parser.ast.ASTInto;
import zigen.sql.parser.ast.ASTJoin;
import zigen.sql.parser.ast.ASTKeyword;
import zigen.sql.parser.ast.ASTMinus;
import zigen.sql.parser.ast.ASTOn;
import zigen.sql.parser.ast.ASTOperator;
import zigen.sql.parser.ast.ASTOrderby;
import zigen.sql.parser.ast.ASTOutfile;
import zigen.sql.parser.ast.ASTOver;
import zigen.sql.parser.ast.ASTParentheses;
import zigen.sql.parser.ast.ASTPartitionBy;
import zigen.sql.parser.ast.ASTRoot;
import zigen.sql.parser.ast.ASTSelect;
import zigen.sql.parser.ast.ASTSelectStatement;
import zigen.sql.parser.ast.ASTSet;
import zigen.sql.parser.ast.ASTStatement;
import zigen.sql.parser.ast.ASTTable;
import zigen.sql.parser.ast.ASTTarget;
import zigen.sql.parser.ast.ASTThen;
import zigen.sql.parser.ast.ASTType;
import zigen.sql.parser.ast.ASTUnion;
import zigen.sql.parser.ast.ASTUpdateStatement;
import zigen.sql.parser.ast.ASTValue;
import zigen.sql.parser.ast.ASTValues;
import zigen.sql.parser.ast.ASTWhen;
import zigen.sql.parser.ast.ASTWhere;
import zigen.sql.parser.ast.ASTWith;
import zigen.sql.parser.exception.LoopException;
import zigen.sql.parser.exception.ParserException;
import zigen.sql.parser.exception.UnexpectedTokenException;
import zigen.sql.parser.util.Util;
import zigen.sql.tokenizer.SqlTokenizer;
import zigen.sql.tokenizer.Token;
import zigen.sql.tokenizer.TokenUtil;

public class SqlParser implements ISqlParser {

	protected SqlTokenizer tokenizer;

	protected int offset = 0;

	protected int length = 0;

	protected int scope = SCOPE_DEFAULT;

	public SqlParser(String sql, SqlFormatRule rule) {
		this.tokenizer = new SqlTokenizer(sql, rule);
	}

	public SqlParser(String sql, SqlTokenizer sqlTokenizer) {
		this.tokenizer = sqlTokenizer;
	}

	public boolean isCanceled(){
		return false;
	}

	public SqlParser() {
	}

	public void parse(INode node) throws ParserException {
		INode x;
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;
			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();

					if (begin.getParent() instanceof ASTFunction) {
						parse(begin.getParent().getParent());
					} else {
						parse(begin.getParent());
					}

					return;
				} else if ("(".equals(getToken())) {
					INode lastNode = node.getLastChild();
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					if (lastNode instanceof ASTFunction) {
						p.setASTFunction((ASTFunction) lastNode);
						lastNode.addChild(p);
					} else {
						node.addChild(p);
					}
					parse(p);

					break;
				} else if (",".equals(getToken())) {

					node.addChild(new ASTComma(offset, length, scope));
				} else {
				}
				break;
			case TokenUtil.TYPE_OPERATOR:
				tokenizer.pushBack();
				parseExpression(node);
				break;
			case TokenUtil.TYPE_KEYWORD:
				if ("select".equalsIgnoreCase(getToken())) {
					scope = SCOPE_SELECT;

					INode last = node.getLastChild();
					if (last instanceof ASTUnion) {
						ASTSelectStatement ss = new ASTSelectStatement(offset, length, scope);
						ASTSelect select = new ASTSelect(offset, length, scope);
						ss.addChild(select);
						last.getParent().addChild(ss);
						parseSelectStatement(select);
						break;
					} else {

						ASTSelectStatement ss = new ASTSelectStatement(offset, length, scope);
						ASTSelect select = new ASTSelect(offset, length, scope);
						ss.addChild(select);
						node.addChild(ss);
						parseSelectStatement(select);
						break;
					}
				} else if ("insert".equalsIgnoreCase(getToken())) {
					scope = SCOPE_INSERT;
					ASTInsertStatement st = new ASTInsertStatement(offset, length, scope);
					node.addChild(st);
					parseInsertStatement(st);
					break;

				} else if ("update".equalsIgnoreCase(getToken())) {
					scope = SCOPE_UPDATE;
					ASTUpdateStatement st = new ASTUpdateStatement(offset, length, scope);
					node.addChild(st);
					parseUpdateStatement(st);
					break;

				} else if ("delete".equalsIgnoreCase(getToken())) {
					scope = SCOPE_DELETE;
					ASTDeleteStatement st = new ASTDeleteStatement(offset, length, scope);
					node.addChild(st);
					parseDeleteStatement(st);
					break;

				} else if ("create".equalsIgnoreCase(getToken())) {
					scope = SCOPE_CREATE;
					ASTCreateStatement st = new ASTCreateStatement(offset, length, scope);
					node.addChild(st);
					parseCreateStatement(st);
					break;

				} else if ("create or replace".equalsIgnoreCase(getToken())) {
					scope = SCOPE_CREATE;
					ASTCreateStatement st = new ASTCreateStatement(offset, length, scope);
					st.changeCreateOrReplace();

					node.addChild(st);
					parseCreateStatement(st);
					break;

				} else if ("drop".equalsIgnoreCase(getToken())) {
					scope = SCOPE_DROP;
					ASTDropStatement st = new ASTDropStatement(offset, length, scope);
					node.addChild(st);
					parseDropStatement(st);
					break;

				} else if ("where".equalsIgnoreCase(getToken())) {
					scope = SCOPE_WHERE;
					ASTWhere where = new ASTWhere(offset, length, scope);
					node.addChildToStatement(where);
					parseWhereClause(where);
					break;
				} else if ("union".equalsIgnoreCase(getToken())) {
					parseUnion(node, false);

				} else if ("union all".equalsIgnoreCase(getToken())) {
					parseUnion(node, true);

				} else if ("minus".equalsIgnoreCase(getToken())) {
					scope = SCOPE_DEFAULT;
					node.addChild(new ASTMinus(offset, length, scope));

				} else if ("from".equalsIgnoreCase(getToken())) {
					scope = SCOPE_FROM;
					ASTFrom from = new ASTFrom(offset, length, scope);
					node.addChildToStatement(from);

					parseFromClause(from);
					return;

					// Support With for DB2
//				} else if("with".equalsIgnoreCase(getToken())){
//					//INode last = node.getLastChild();
//					ASTWith with = new ASTWith(offset, length, scope);
//					node.addChildToStatement(with);
//					parseWithClause(with);
//					return;

				} else if ("order by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;

					ASTOrderby by = new ASTOrderby(offset, length, scope);

					if(node instanceof ASTParentheses){
						ASTParentheses p = (ASTParentheses)node;
						p.addChild(by);
//						if(p.isOpen() && "OVER".equalsIgnoreCase(p.getASTFunction().getName())){
//							p.addChild(by);
//						}

					}else{
						node.addChildToStatement(by);

					}
					parseOrderByClause(by);
					break;

				} else if ("partition by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTPartitionBy by = new ASTPartitionBy(offset, length, scope);
					if(node instanceof ASTParentheses){
						ASTParentheses p = (ASTParentheses)node;
						p.addChild(by);
					}else{
						node.addChildToStatement(by);
					}
					parseOrderByClause(by);
					break;


				} else if ("group by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTGroupby by = new ASTGroupby(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;
				} else if ("having by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTHavingBy by = new ASTHavingBy(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;

				} else if ("case".equalsIgnoreCase(getToken())) {
					ASTCaseCause cc = new ASTCaseCause(offset, length, scope);
					node.addChild(cc);

					ASTCase c = new ASTCase(offset, length, scope);
					cc.addChild(c);

					parseCase(cc);
					break;

				} else if ("then".equalsIgnoreCase(getToken()) || "when".equalsIgnoreCase(getToken()) || "else".equalsIgnoreCase(getToken()) || "end".equalsIgnoreCase(getToken())) {
					if (node instanceof ASTCaseCause) {
						tokenizer.pushBack();
						parseCase((ASTCaseCause) node);
					} else if (node instanceof ASTCase || node instanceof ASTWhen || node instanceof ASTThen || node instanceof ASTElse) {
						tokenizer.pushBack();
						parseCase((ASTCaseCause) node.getParent());
						return;
					} else {
						throw new UnexpectedTokenException(node.getClass().getName(), offset, length);
					}

				} else if ("into".equalsIgnoreCase(getToken())) {

					ASTInto into = new ASTInto(getToken(), offset, length, scope);
					if (node instanceof ASTSelect || node instanceof ASTFrom) {
						node.getASTSelectStatement().addChild(into);
						parse(into);
					}else{
						node.addChild(new ASTKeyword(getToken(), offset, length, scope));
					}

				} else if ("as".equalsIgnoreCase(getToken())) {
					INode last = node.getLastChild();

					if(last instanceof ASTParentheses){
						ASTParentheses p = (ASTParentheses)last;
						if(p.isForFunction()){
							if (nextToken() == TokenUtil.TYPE_NAME) {
								p.setAliasName(getToken(), offset, length);
								parse(node.getParent());
							} else {
								tokenizer.pushBack();
							}
							break;
						}
					}

					if (last instanceof ASTAlias) {
						ASTAlias alias = (ASTAlias) last;
						if (nextToken() == TokenUtil.TYPE_NAME) {
							alias.setAliasName(getToken(), offset, length);
						} else {
							tokenizer.pushBack();
						}
					} else {
						throw new UnexpectedTokenException(node.getClass().getName(), offset, length);
					}

				} else if (token.getSubType() == TokenUtil.SUBTYPE_KEYWORD_FUNCTION) {
					ASTFunction function = new ASTFunction(getToken(), offset, length, scope);
					node.addChild(function);

				} else if ("LEFT JOIN".equalsIgnoreCase(getToken()) || "LEFT OUTER JOIN".equalsIgnoreCase(getToken()) || "RIGHT JOIN".equalsIgnoreCase(getToken())
						|| "RIGHT OUTER JOIN".equalsIgnoreCase(getToken()) || "INNER JOIN".equalsIgnoreCase(getToken())) {
					scope = SCOPE_FROM;
					node.addChild(new ASTJoin(getToken(), offset, length, scope));

				} else if ("JOIN".equalsIgnoreCase(getToken())) {
					scope = SCOPE_FROM;
					node.addChild(new ASTJoin(getToken(), offset, length, scope));


				} else if ("on".equalsIgnoreCase(getToken())) {
					//scope = SCOPE_WHERE;
					ASTOn on = new ASTOn(getToken(), offset, length, scope);
					node.addChild(on);


				} else if("over".equalsIgnoreCase(getToken())){
					INode last = node.getLastChild();
					ASTOver over = new ASTOver(getToken(), offset, length, scope);
					node.addChild(over);
					parse(over);


				} else {
					if (node instanceof ASTOperator) {
						tokenizer.pushBack();
						parse(node.getParent());
					} else {
						node.addChild(new ASTKeyword(getToken(), offset, length, scope));
					}
				}
				break;

			case TokenUtil.TYPE_NAME:

				INode lastNode = node.getLastChild();

				if ("OUTFILE".equalsIgnoreCase(getToken())) {
					node.addChild(new ASTOutfile(getToken(), offset, length, scope));

				} else {

					if (lastNode instanceof ASTAlias) {
						if (node instanceof ASTFunction) {
							ASTFunction func = (ASTFunction) node;
							func.setAliasName(getToken(), offset, length);
							parse(func.getParent());
						} else {
							// for Oracle row_number() over(order by table, column)
							if("OVER".equalsIgnoreCase(getToken()) && "ROW_NUMBER".equalsIgnoreCase(lastNode.getName())){
								node.addChild(new ASTFunction(getToken(), offset, length, scope));
							}else{
								((ASTAlias) lastNode).setAliasName(getToken(), offset, length);
							}
						}
					} else if(lastNode instanceof ASTOutfile){
						ASTOutfile outfile = (ASTOutfile)lastNode;
						outfile.setFilePath(getToken());

					} else if (node instanceof ASTFrom) {
						if (lastNode instanceof ASTOn) {
							node.addChild(new ASTColumn(getToken(), offset, length, scope));
						} else {
							node.addChild(new ASTTable(getToken(), offset, length, scope));
						}

					} else if (node instanceof ASTOperator) {
						parseValue(lastNode);
					} else {
						node.addChild(new ASTColumn(getToken(), offset, length, scope));
					}
				}

				break;
			case TokenUtil.TYPE_VALUE:
				INode lastNode2 = node.getLastChild();
				if (lastNode2 instanceof ASTAlias) {
					if (node instanceof ASTFunction) {
						ASTFunction func = (ASTFunction) node;
						func.setAliasName(getToken(), offset, length);
						parse(func.getParent());
						return;
					} else {
						((ASTAlias) lastNode2).setAliasName(getToken(), offset, length);
					}
					// -->
				} else if (lastNode2 instanceof ASTOperator) {
					throw new UnexpectedTokenException(getToken(), offset, length);
				} else {
					parseValue(node);
				}

				break;

			default:
				break;
			}
		}
	}

	protected void parseSelectStatement(ASTSelect node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();
					parse(begin.getParent());
					return;
				} else if ("(".equals(getToken())) {
					INode lastNode = node.getLastChild();
					if (lastNode instanceof ASTFunction) {
						ASTParentheses p = new ASTParentheses(offset, length, scope);
						p.setASTFunction((ASTFunction) lastNode);
						lastNode.addChild(p);
						parse(p);
					} else {
						if (node instanceof ASTSelect) {
							ASTParentheses p = new ASTParentheses(offset, length, scope);
							node.addChild(p);
							parse(p);
							break;
						}
						// skipToken(node, offset, length, scope);
						throw new UnexpectedTokenException(node.getClass().getName(), offset, length);
					}
					// -->
					break;
				} else if (",".equals(getToken())) {
					node.addChild(new ASTComma(offset, length, scope));
				} else if ("(*)".equals(getToken())) {
					INode lastNode = node.getLastChild();
					if (lastNode instanceof ASTFunction) {
						ASTParentheses p = new ASTParentheses(offset, length, scope);
						ASTColumn col = new ASTColumn("*", offset, length, scope);
						p.addChild(col);
						p.setASTFunction((ASTFunction)lastNode);
						lastNode.addChild(p);
						parse(lastNode);
					} else {
						throw new UnexpectedTokenException(getToken(), offset, length);
					}

				} else if ("||".equals(getToken())) {
					INode lastNode = node.getLastChild();
					if (lastNode instanceof ASTColumn) {
						ASTColumn col = (ASTColumn) lastNode;
						col.addConcat(getToken(), offset, length);

					} else {
						throw new UnexpectedTokenException(getToken(), offset, length);
					}

				} else {
					throw new UnexpectedTokenException(getToken(), offset, length);

				}
				break;

			case TokenUtil.TYPE_OPERATOR: // case '*':
				if ("*".equals(getToken())) {
					node.addChild(new ASTColumn(getToken(), offset, length, scope));
				} else {
					tokenizer.pushBack();
					parseExpression(node);
				}
				break;
			case TokenUtil.TYPE_VALUE:
				INode lastNode1 = node.getLastChild();
				if (lastNode1 instanceof ASTColumn) {
					ASTColumn col = (ASTColumn) lastNode1;
					if (col.isConcating()) {
						col.addColumn(getToken(), offset, length);
					} else {
						col.setAliasName(getToken(), offset, length);
					}
				} else if (lastNode1 instanceof ASTAlias) {
					((ASTAlias) lastNode1).setAliasName(getToken(), offset, length);
				} else {
					parseValue(node);
				}
				break;

			case TokenUtil.TYPE_NAME:
				INode lastNode = node.getLastChild();
				if (lastNode instanceof ASTColumn) {
					ASTColumn col = (ASTColumn) lastNode;
					if (col.isConcating()) {
						col.addColumn(getToken(), offset, length);
					} else {
						col.setAliasName(getToken(), offset, length);
					}
				} else if (lastNode instanceof ASTAlias) {
					((ASTAlias) lastNode).setAliasName(getToken(), offset, length);
				} else {
					ASTColumn col = new ASTColumn(getToken(), offset, length, scope);
					node.addChild(col);
				}
				break;

			// case TokenUtil.TYPE_
			//
			// break;

			case TokenUtil.TYPE_KEYWORD:
				if ("select".equalsIgnoreCase(getToken())) {
					scope = SCOPE_SELECT;
					tokenizer.pushBack();
					parse(node.getASTStatement().getParent());
					break;
				} else if ("from".equalsIgnoreCase(getToken())) {
					scope = SCOPE_FROM;
					ASTFrom from = new ASTFrom(offset, length, scope);
					ASTStatement select = node.getASTStatement();

					if (select != null) {
						ASTFrom wkFrom = (ASTFrom) ASTUtil.findChildWide(select, "ASTFrom");
						if (wkFrom != null && select.getParent() != null) {
							ASTStatement pSelect = select.getParent().getASTStatement();
							pSelect.addChild(from);
						} else {
							select.addChild(from);
						}
						parseFromClause(from);
					}

					break;

				} else if ("union".equalsIgnoreCase(getToken())) {
					parseUnion(node, false);
					break;
				} else if ("union all".equalsIgnoreCase(getToken())) {
					parseUnion(node, true);
					break;

				} else if ("minus".equalsIgnoreCase(getToken())) {
					scope = SCOPE_DEFAULT;
					node.addChildToStatementParent(new ASTMinus(offset, length, scope));

				} else if ("where".equalsIgnoreCase(getToken())) {
					scope = SCOPE_WHERE;
					ASTWhere where = new ASTWhere(offset, length, scope);
					node.addChildToStatement(where);
					parseWhereClause(where);
					break;

				} else if ("order by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTOrderby by = new ASTOrderby(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;

				} else if ("group by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTGroupby by = new ASTGroupby(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;
				} else if ("having by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTHavingBy by = new ASTHavingBy(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;

				} else if ("case".equalsIgnoreCase(getToken())) {
					ASTCaseCause cc = new ASTCaseCause(offset, length, scope);
					node.addChild(cc);

					ASTCase c = new ASTCase(offset, length, scope);
					cc.addChild(c);

					parseCase(cc);
					break;

				} else {
					if (token.getSubType() == TokenUtil.SUBTYPE_KEYWORD_FUNCTION) {
						ASTFunction col = new ASTFunction(getToken(), offset, length, scope);
						node.addChild(col);
					}else{
						tokenizer.pushBack();
						parse(node);
					}
					break;
				}
			default:
				break;
			}
		}

	}

	protected void parseWithClause(ASTWith node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();
					parse(begin.getParent());
					return;
				} else if ("(".equals(getToken())) {
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					node.addChild(p);
					parse(p);
					break;
				} else if (",".equals(getToken())) {
					node.addChild(new ASTComma(offset, length, scope));
				}
				break;

			case TokenUtil.TYPE_NAME:
				INode lastNode = node.getLastChild();
				if (lastNode instanceof ASTAlias) {
					((ASTAlias) lastNode).setAliasName(getToken(), offset, length);
				} else {
					ASTTable table = new ASTTable(getToken(), offset, length, scope);
					node.addChild(table);
				}
				break;

			case TokenUtil.TYPE_VALUE:
				INode lastNode2 = node.getLastChild();
				if (lastNode2 instanceof ASTAlias) {
					((ASTAlias) lastNode2).setAliasName(getToken(), offset, length);
				} else {
					parseValue(node);
				}

				break;

			case TokenUtil.TYPE_KEYWORD:
				if("as".equalsIgnoreCase(getToken())){
					node.addChild(new ASTKeyword("as", offset, length, scope));
					break;

				}else if("select".equalsIgnoreCase(getToken())){
					scope = SCOPE_SELECT;

					INode last = node.getLastChild();
					if (last instanceof ASTUnion) {
						ASTSelectStatement ss = new ASTSelectStatement(offset, length, scope);
						ASTSelect select = new ASTSelect(offset, length, scope);
						ss.addChild(select);
						last.getParent().addChild(ss);
						parseSelectStatement(select);
						break;
					} else {

						ASTSelectStatement ss = new ASTSelectStatement(offset, length, scope);
						ASTSelect select = new ASTSelect(offset, length, scope);
						ss.addChild(select);
						node.addChild(ss);
						parseSelectStatement(select);
						break;
					}
				} else {
					tokenizer.pushBack();
					parse(node);
					return;
				}
			default:
				break;
			}
		}

	}


	protected void parseFromClause(ASTFrom node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();
					parse(begin.getParent());
					return;
				} else if ("(".equals(getToken())) {
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					node.addChild(p);
					parse(p);
					break;
				} else if (",".equals(getToken())) {
					node.addChild(new ASTComma(offset, length, scope));
				}
				break;

			case TokenUtil.TYPE_NAME:
				INode lastNode = node.getLastChild();
				if (lastNode instanceof ASTAlias) {
					((ASTAlias) lastNode).setAliasName(getToken(), offset, length);
				} else {
					ASTTable table = new ASTTable(getToken(), offset, length, scope);
					node.addChild(table);
				}
				break;

			case TokenUtil.TYPE_VALUE:
				INode lastNode2 = node.getLastChild();
				if (lastNode2 instanceof ASTAlias) {
					((ASTAlias) lastNode2).setAliasName(getToken(), offset, length);
				} else {
					parseValue(node);
				}

				break;

			case TokenUtil.TYPE_KEYWORD:
				if ("where".equalsIgnoreCase(getToken())) {
					scope = SCOPE_WHERE;
					ASTWhere where = new ASTWhere(offset, length, scope);
					node.addChildToStatement(where);
					parseWhereClause(where);
					break;

				} else if ("union".equalsIgnoreCase(getToken())) {
					parseUnion(node, false);
					break;
				} else if ("union all".equalsIgnoreCase(getToken())) {
					parseUnion(node, true);
					break;
				} else if ("minus".equalsIgnoreCase(getToken())) {
					scope = SCOPE_DEFAULT;
					ASTMinus minus = new ASTMinus(offset, length, scope);

					ASTStatement st = node.getASTStatement();
					if (st != null && st.getParent() != null) {
						st.getParent().addChild(minus);
						parse(st.getParent());
						return;
					} else {
						ASTParentheses p = node.getASTParentheses();
						if (p != null) {
							p.addChild(minus);
							parse(p);
							return;
						}
					}
					throw new UnexpectedTokenException(getToken(), offset, length);

				} else {
					tokenizer.pushBack();
					parse(node);
					return;
				}
			default:
				break;
			}
		}

	}

	protected void parseWhereClause(ASTWhere node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;
			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();
					parse(begin.getParent());
					return;
				} else if ("(".equals(getToken())) {
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					node.addChild(p);
					parse(p);
					break;

				} else if (",".equals(getToken())) {
					break;
				} else {
					parseOuterJoinForOracle(node);
				}
				break;
			case TokenUtil.TYPE_OPERATOR:
				tokenizer.pushBack();
				parseExpression(node);
				break;
			case TokenUtil.TYPE_NAME:
				node.addChild(new ASTColumn(getToken(), offset, length, scope));
				break;

			case TokenUtil.TYPE_VALUE:
				parseValue(node);
				break;
			case TokenUtil.TYPE_KEYWORD:
				if ("union".equalsIgnoreCase(getToken())) {
					parseUnion(node, false);
					break;
				} else if ("union all".equalsIgnoreCase(getToken())) {
					parseUnion(node, true);
					break;

				} else if ("minus".equalsIgnoreCase(getToken())) {
					scope = SCOPE_DEFAULT;
					node.addChildToStatementParent(new ASTMinus(offset, length, scope));

				} else if ("select".equalsIgnoreCase(getToken())) {
					scope = SCOPE_SELECT;
					ASTSelectStatement ss = new ASTSelectStatement(offset, length, scope);
					ASTSelect select = new ASTSelect(offset, length, scope);
					ss.addChild(select);
					node.addChildToStatementParent(ss);
					parseSelectStatement(select);
					break;

				} else if ("order by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTOrderby by = new ASTOrderby(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;

				} else if ("group by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTGroupby by = new ASTGroupby(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;
				} else if ("having by".equalsIgnoreCase(getToken())) {
					scope = SCOPE_BY;
					ASTHavingBy by = new ASTHavingBy(offset, length, scope);
					node.addChildToStatement(by);
					parseOrderByClause(by);
					break;

				} else {
					tokenizer.pushBack();
					parse(node);
					break;
				}
			}
		}

	}

	protected void parseOrderByClause(ASTOrderby node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();
					parse(begin.getParent());
					return;
				} else if ("(".equals(getToken())) {
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					node.addChild(p);
					parse(p);
					break;
				} else if (",".equals(getToken())) {
					node.addChild(new ASTComma(offset, length, scope));
				}
				break;

			case TokenUtil.TYPE_NAME:
				INode lastNode = node.getLastChild();
				if (lastNode instanceof ASTAlias) {
					((ASTAlias) lastNode).setAliasName(getToken(), offset, length);
				} else {
					ASTColumn col = new ASTColumn(getToken(), offset, length, scope);
					node.addChild(col);
				}
				break;

			case TokenUtil.TYPE_KEYWORD:
				tokenizer.pushBack();
				return;
			default:
				break;
			}
		}

	}

	protected void skipToken(INode node, int offset, int length) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;
			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					return;
				} else if ("(".equals(getToken())) {
					// skipToken(node);
					skipToken(node, offset, length);
					break;
				}
				break;
			case TokenUtil.TYPE_KEYWORD:
				if ("select".equalsIgnoreCase(getToken())) {
					scope = SCOPE_SELECT;
					tokenizer.pushBack();
					// ASTParentheses p = new ASTParentheses(lineno);
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					node.addChild(p);
					parse(p);

				} else {
					tokenizer.pushBack();
					inner: for (;;) {
						switch (nextToken()) {
						case TokenUtil.TYPE_END_SQL:
							break inner;
						case TokenUtil.TYPE_SYMBOL:
							if (")".equals(getToken())) {
								break inner;
							}
						default:
							break;
						}
					}
					tokenizer.pushBack();
				}
				break;

			default:
				break;
			}
		}

	}

	protected void skipClause(INode node, int offset, int length) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				tokenizer.pushBack();
				return;
			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					tokenizer.pushBack();
					return;
				} else if ("(".equals(getToken())) {
					// skipClause(node);
					skipClause(node, offset, length);
					break;
				}
				break;

			case TokenUtil.TYPE_KEYWORD:

				if ("select".equalsIgnoreCase(getToken())) {
					scope = SCOPE_SELECT;
					tokenizer.pushBack();
					// ASTParentheses p = new ASTParentheses(lineno);
					ASTParentheses p = new ASTParentheses(offset, length, scope);

					node.addChild(p);
					parse(p);

				} else if ("by".equalsIgnoreCase(getToken())) {
					break;

				} else {
					tokenizer.pushBack();
					inner: for (;;) {
						switch (nextToken()) {
						case TokenUtil.TYPE_END_SQL:
							break inner;
						case TokenUtil.TYPE_SYMBOL:
							if (")".equals(getToken())) {
								break inner;
							}
						default:
							break;
						}
					}
					tokenizer.pushBack();
				}
				break;

			default:
				break;
			}
		}

	}

	protected void parseInsertStatement(INode node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;
			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					parseInsertStatement(node.getParent());
					break;
				} else if ("(".equals(getToken())) {
					if (node instanceof ASTInsertStatement) {
						scope = SCOPE_INTO_COLUMNS;
					} else {
						scope = SCOPE_DEFAULT;
					}
					ASTParentheses p = new ASTParentheses(offset, length, scope);

					node.addChild(p);
					parseInsertStatement(p);
					break;
				} else if (",".equals(getToken())) {
					if (node instanceof ASTParentheses) {
						node.addChild(new ASTComma(offset, length, scope));
					}
					break;
				}
				break;

			case TokenUtil.TYPE_KEYWORD:
				if ("into".equalsIgnoreCase(getToken())) {
					scope = SCOPE_INTO;
					ASTInto into = new ASTInto(getToken(), offset, length, scope);
					node.getASTInsertStatement().addChild(into);
					parseInsertStatement(into);
					break;

				} else if ("values".equalsIgnoreCase(getToken())) {
					scope = SCOPE_WHERE;
					ASTValues values = new ASTValues(offset, length, scope);
					node.addChild(values);
					parseInsertStatement(values);
					break;

				} else if ("select".equalsIgnoreCase(getToken())) {
					scope = SCOPE_SELECT;
					tokenizer.pushBack();
					parse(node.getASTInsertStatement());
					break;

				} else {
					break;
				}

			case TokenUtil.TYPE_NAME:
				if (node instanceof ASTInto) {
					node.addChild(new ASTTable(getToken(), offset, length, scope));
					parseInsertStatement(node.getASTInsertStatement());
					break;
				} else if (node instanceof ASTParentheses) {
					node.addChild(new ASTColumn(getToken(), offset, length, scope));
				} else {
					throw new UnexpectedTokenException(getToken(), offset, length);

				}
				break;
			case TokenUtil.TYPE_VALUE:
				parseValue(node);

				break;

			default:
				break;
			}
		}

	}

	protected void parseUpdateStatement(INode node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;
			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					parseUpdateStatement(node.getParent());
					break;
				} else if ("(".equals(getToken())) {
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					node.addChild(p);
					parseUpdateStatement(p);
					break;
				} else if (",".equals(getToken())) {
					node.addChild(new ASTComma(offset, length, scope));
				}
				break;

			case TokenUtil.TYPE_KEYWORD:
				if ("set".equalsIgnoreCase(getToken())) {
					scope = SCOPE_SET;
					ASTSet set = new ASTSet(offset, length, scope);
					node.getASTUpdateStatement().addChild(set);

					parseUpdateStatement(set);
					break;

				} else if ("where".equalsIgnoreCase(getToken())) {
					scope = SCOPE_WHERE;
					ASTWhere where = new ASTWhere(offset, length, scope);
					ASTUpdateStatement st = node.getASTUpdateStatement();
					st.addChild(where);
					// where Clause
					parseWhereClause(where);
					break;

				} else {
					break;
				}

			case TokenUtil.TYPE_OPERATOR:
				tokenizer.pushBack();
				parseExpression(node);
				break;

			case TokenUtil.TYPE_VALUE:
				parseValue(node);
				break;

			case TokenUtil.TYPE_NAME:
				if (node instanceof ASTUpdateStatement) {
					node.addChild(new ASTTable(getToken(), offset, length, scope));
					parseUpdateStatement(node.getASTUpdateStatement());
				} else if (node instanceof ASTSet) {
					node.addChild(new ASTColumn(getToken(), offset, length, scope));
				} else if (node instanceof ASTParentheses) {
					node.addChild(new ASTColumn(getToken(), offset, length, scope));
				} else {
					throw new UnexpectedTokenException(getToken(), offset, length);

				}
				break;

			default:
				break;
			}
		}

	}

	protected void parseDeleteStatement(ASTDeleteStatement node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();
					parse(begin.getParent());
					return;
				} else if ("(".equals(getToken())) {
					skipToken(node, offset, length);
					break;
				}
				break;

			case TokenUtil.TYPE_KEYWORD:
				if ("from".equalsIgnoreCase(getToken())) {
					scope = SCOPE_FROM;
					ASTFrom from = new ASTFrom(offset, length, scope);
					node.getASTDeleteStatement().addChild(from);
					parseFromClause(from);
					break;

				} else if ("where".equalsIgnoreCase(getToken())) {
					scope = SCOPE_WHERE;
					ASTWhere where = new ASTWhere(offset, length, scope);
					ASTDeleteStatement ds = node.getASTDeleteStatement();
					ds.addChild(where);
					// where Clause
					parseWhereClause(where);
					break;

				} else {
					break;
				}
			default:
				break;
			}
		}

	}

	public static Map createMap = new HashMap();

	protected void parseCreateStatement(ASTCreateStatement node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_KEYWORD:
				if(!node.hasType()){
					scope = SCOPE_TARGET;
					node.addChild(new ASTType(getToken(), offset, length, scope));
				}
				break;
			// case TokenUtil.TYPE_VALUE:
			case TokenUtil.TYPE_NAME:

				INode lastNode = node.getLastChild();
				if (lastNode instanceof ASTType) {
					if ("body".equalsIgnoreCase(getToken())) {
						ASTType type = (ASTType) lastNode;
						type.setPackageBody(true);
					} else {
						ASTType type = (ASTType)lastNode;
						if(!type.hasTarget()){
							ASTTarget name = new ASTTarget(getToken(), offset, length, scope);
							lastNode.addChild(name);
							break;
						}

					}
				}


			default:
				break;
			}
		}

	}

	protected void parseDropStatement(ASTDropStatement node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_KEYWORD:
				scope = SCOPE_TARGET;
				if ("function".equalsIgnoreCase(getToken())) {
					ASTType type = new ASTType("function", offset, length, scope);
					node.getASTDropStatement().addChild(type);
				} else if ("procedure".equalsIgnoreCase(getToken())) {
					ASTType type = new ASTType("procedure", offset, length, scope);
					node.getASTDropStatement().addChild(type);
				} else if ("trigger".equalsIgnoreCase(getToken())) {
					ASTType type = new ASTType("trigger", offset, length, scope);
					node.getASTDropStatement().addChild(type);
				} else if ("package".equalsIgnoreCase(getToken())) {
					ASTType type = new ASTType("package", offset, length, scope);
					node.getASTDropStatement().addChild(type);
				} else if ("table".equalsIgnoreCase(getToken())) {
					ASTType type = new ASTType("table", offset, length, scope);
					node.getASTDropStatement().addChild(type);
				} else if ("view".equalsIgnoreCase(getToken())) {
					ASTType type = new ASTType("view", offset, length, scope);
					node.getASTDropStatement().addChild(type);
				} else if ("synonym".equalsIgnoreCase(getToken())) {
					ASTType type = new ASTType("synonym", offset, length, scope);
					node.getASTDropStatement().addChild(type);
				}
				break;
			// case TokenUtil.TYPE_VALUE:
			case TokenUtil.TYPE_NAME:
				INode lastNode = node.getLastChild();
				if (lastNode instanceof ASTType) {
					if ("body".equalsIgnoreCase(getToken())) {
						ASTType type = (ASTType) lastNode;
						type.setPackageBody(true);
					} else {
						ASTTarget name = new ASTTarget(getToken(), offset, length, scope);
						node.addChild(name);
					}
				}
				break;

			default:
				break;
			}
		}

	}

	protected void parseCase(ASTCaseCause node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();
					parse(begin.getParent());
					return;
				} else if ("(".equals(getToken())) {
					INode lastNode = node.getLastChild();
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					if (lastNode instanceof ASTFunction) {
						p.setASTFunction((ASTFunction) lastNode);
						lastNode.addChild(p);
						parse(p);
					} else {
						node.getLastChild().addChild(p);
						parse(p);
					}
					return;
				} else if (",".equals(getToken())) {
					break;
				} else {
					;
				}
				break;
			case TokenUtil.TYPE_OPERATOR:
				tokenizer.pushBack();
				parseExpression(node);
				break;

			case TokenUtil.TYPE_NAME:
				INode n = node.getLastChild();
				n.addChild(new ASTColumn(getToken(), offset, length, scope));
				break;

			case TokenUtil.TYPE_VALUE:
				INode n2 = node.getLastChild();
				if (n2 instanceof ASTAlias) {
					ASTAlias alias = (ASTAlias) n2;
					alias.setAliasName(getToken(), offset, length);

				} else {
					// n2.addChild(new ASTValue(getToken(), offset, length,
					// scope));
					parseValue(n2);
				}
				break;

			case TokenUtil.TYPE_KEYWORD:
				if ("when".equalsIgnoreCase(getToken())) {
					node.addChild(new ASTWhen(offset, length, scope));

				} else if ("then".equalsIgnoreCase(getToken())) {
					node.addChild(new ASTThen(offset, length, scope));

				} else if ("else".equalsIgnoreCase(getToken())) {
					node.addChild(new ASTElse(offset, length, scope));

				} else if ("end".equalsIgnoreCase(getToken())) {
					node.setComplete(true);
					parse(node.getParent()); // ADD
					return;

				} else {
					if (token.getSubType() == TokenUtil.SUBTYPE_KEYWORD_FUNCTION) {
						// ASTFunction col = new ASTFunction(getToken(), offset,
						// length);
						// node.getLastChild().addChild(col);//
						tokenizer.pushBack();
						// parse(node);
						parse(node.getLastChild());

						return;
					}
				}
				break;
			}
		}
	}

	protected Token token;

	protected String getToken() {
		// return token.getOriginal();
		System.out.println(token.getCustom());
		return token.getCustom();
	}

	protected String _preToken;

	protected int checkPoint = 0;

	protected static final int MAX_SAME_WORD = 1000;

	protected int nextToken() {
		if (tokenizer.hasNext()) {
			this._preToken = (token != null) ? token.getOriginal() : "";
			this.token = (Token) tokenizer.next();
			this.offset = token.getIndex();
			this.length = token.getOriginalLength();
			if (token.getOriginal().equals(_preToken)) {
				checkPoint++;
			} else {
				checkPoint = 0;
			}
			if (checkPoint > MAX_SAME_WORD) {
				throw new LoopException(MAX_SAME_WORD);
			}

			return token.getType();

		} else {
			return TokenUtil.TYPE_END_SQL;
		}
	}

	protected void parseExpression(INode node) throws ParserException {
		for (;;) {
			if(isCanceled()) return;

			switch (nextToken()) {
			case TokenUtil.TYPE_END_SQL:
				return;

			case TokenUtil.TYPE_SYMBOL:
				if (")".equals(getToken())) {
					ASTParentheses begin = node.getASTParentheses();
					begin.setEndOffset(offset);
					scope = begin.getScope();

					if (begin.isForFunction()) {
						parse(begin.getParent().getParent());
					} else {
						parse(begin.getParent());
					}

					return;
				} else if ("(".equals(getToken())) {
					INode lastNode = node.getLastChild();
					ASTParentheses p = new ASTParentheses(offset, length, scope);
					if (lastNode instanceof ASTFunction) {
						p.setASTFunction((ASTFunction) lastNode);
						lastNode.addChild(p);
						parse(p);
					} else {
						// node.getLastChild().addChild(p);
						node.addChild(p);
						parse(p);
					}
					return;
				} else if (",".equals(getToken())) {
					// System.err.println(node.getParent());
					node.getParent().addChild(new ASTComma(offset, length, scope));
					parse(node.getParent());
					return;
				} else {
					parseOuterJoinForOracle(node);
				}
				break;

			case TokenUtil.TYPE_KEYWORD:
				if (token.getSubType() == TokenUtil.SUBTYPE_KEYWORD_FUNCTION) {
					ASTFunction function = new ASTFunction(getToken(), offset, length, scope);
					node.addChild(function);
					parseExpression(node);
					return;

				} else {
					ASTExpression exp1 = node.getASTExpression();
					if (exp1 != null) {
						tokenizer.pushBack();
						parse(exp1.getParent());
					} else {
						tokenizer.pushBack();
						parse(node);
					}
					return;
				}

			case TokenUtil.TYPE_NAME:
				if (node instanceof ASTOperator) {
					ASTOperator ope = (ASTOperator) node;
					if (ope.hasRightChild()) {
						ASTExpression exp = ope.getASTExpression();
						exp.setAliasName(getToken(), offset, length);
						parse(exp.getParent());
						return;
					} else {
						ASTColumn col = new ASTColumn(getToken(), offset, length, scope);
						node.addChild(col);
					}
				} else {
					ASTColumn col = new ASTColumn(getToken(), offset, length, scope);
					node.addChild(col);
				}

				break;
			case TokenUtil.TYPE_VALUE:
				// ASTValue val = new ASTValue(getToken(), offset, length,
				// scope);
				// node.addChild(val);
				parseValue(node);
				break;

			case TokenUtil.TYPE_OPERATOR:
				ASTOperator ope = new ASTOperator(getToken(), offset, length, scope);

				INode lastNode = node.getLastChild();
				if (lastNode instanceof ASTCase || lastNode instanceof ASTWhen || lastNode instanceof ASTThen || lastNode instanceof ASTElse) {
					tokenizer.pushBack();
					parseExpression(lastNode);
					return;

				} else if (lastNode instanceof ASTParentheses && ((ASTParentheses) lastNode).isForFunction()) {
					ASTParentheses p = (ASTParentheses) lastNode;
					ASTExpression exp = p.getASTFunction().getASTExpression();
					if (exp == null) {
						exp = new ASTExpression(offset, length, scope);
						tokenizer.pushBack();
						parseExpression(changeNode(p.getASTFunction(), exp));
						return;
					}

					parseExpression(changeNode(p.getASTFunction(), ope));

				} else if (lastNode instanceof ASTColumn || lastNode instanceof ASTValue || lastNode instanceof ASTFunction || lastNode instanceof ASTParentheses) {

					if(node.getParent() instanceof ASTExpression){

					}

					ASTExpression exp = node.getASTExpression();
					if (exp == null) {
						exp = new ASTExpression(offset, length, scope);
						tokenizer.pushBack();
						parseExpression(changeNode(lastNode, exp));
						return;
					}

					if (node instanceof ASTOperator) {
						ASTOperator pre = (ASTOperator) node;
						if (pre.compare(ope) < 0) {
							// parseExpression(changeNode(lastNode, ope));
							parseExpression(changeNode(lastNode, ope));
						} else {
							// parseExpression(changeNode(exp, ope));
							parseExpression(changeNode(pre, ope));
						}

					} else {
						parseExpression(changeNode(lastNode, ope));
						return;
					}
				}
			}

		}
	}

	protected void parseUnion(INode node, boolean isUnionAll) throws ParserException {
		scope = SCOPE_DEFAULT;
		ASTUnion union;
		if (isUnionAll) {
			union = new ASTUnion(offset, length, scope, true);
		} else {
			union = new ASTUnion(offset, length, scope);
		}

		if (node instanceof ASTRoot) {
			node.addChild(union);
			parse(node);
			return;
		}

		if (node instanceof ASTParentheses) {
			ASTParentheses p = (ASTParentheses) node;
			p.addChild(union);
			parse(p);
			return;
		}

		ASTStatement st = node.getASTStatement();
		if (st != null && st.getParent() != null) {
			st.getParent().addChild(union);
			parse(st.getParent());
			return;
		} else {
			ASTParentheses p = node.getASTParentheses();
			if (p != null) {
				p.addChild(union);
				parse(p);
				return;
			}
		}
		throw new UnexpectedTokenException(getToken(), offset, length);

	}

	protected void parseOuterJoinForOracle(INode node) {
		if ("(+)".equals(getToken())) {
			INode lastNode = node.getLastChild();
			if (lastNode instanceof ASTColumn) {
				((ASTColumn) lastNode).addOuterJoin(getToken(), offset, length);
//				((ASTColumn) lastNode).setOuterJoint(true);
			}
		}
	}

	private INode changeNode(INode from, INode to) {
		INode parent = from.getParent();
		parent.removeChild(from);
		parent.addChild(to);
		to.addChild(from);
		return to;
	}

	INode parseValue(INode parent) {
		INode node = null;
		if (token.getSubType() == TokenUtil.SUBTYPE_VALUE_BIND) {
			node = new ASTBind(getToken(), offset, length, scope);
		} else {
			node = new ASTValue(getToken(), offset, length, scope);
		}
		parent.addChild(node);
		return node;
	}

	public int getScope() {
		return scope;
	}

	public String dump(INode node) {
		StringBuffer sb = new StringBuffer();
		dump(sb, node, "");
		return sb.toString();
	}

	protected void dump(StringBuffer sb, INode node, String pre) {

		if (node instanceof ASTInnerAlias) {
			// node.getParent().removeChild(node);
		}

		if (node.getChildrenSize() != 0) {
			sb.append(pre + "<" + node.toString() + ">\r\n");
			for (int i = 0; i < node.getChildrenSize(); ++i) {
				INode n = (INode) node.getChild(i);
				if (n != null) {
					dump(sb, n, pre + " ");
				}
			}
			sb.append(pre + "</" + node.getNodeClassName() + ">\r\n");

		} else {
			sb.append(pre + "<" + node.toString() + " />\r\n");
		}
	}

	public String dumpXml(INode node) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		dumpXml(sb, node, "");
		return sb.toString();
	}

	protected void dumpXml(StringBuffer sb, INode node, String pre) {

		if (node instanceof ASTInnerAlias) {
			// node.getParent().removeChild(node);
		}

		if (node.getChildrenSize() != 0) {
			sb.append(pre + "<" + Util.encodeMarkup(node.toString()) + ">\r\n");
			for (int i = 0; i < node.getChildrenSize(); ++i) {
				INode n = (INode) node.getChild(i);
				if (n != null) {
					dumpXml(sb, n, pre + " ");
				}
			}
			sb.append(pre + "</" + node.getNodeClassName() + ">\r\n");

		} else {
			sb.append(pre + "<" + Util.encodeMarkup(node.toString()) + " />\r\n");
		}
	}

	public void setTokenizer(SqlTokenizer tokenizer) {
		this.tokenizer = tokenizer;
	}
}
