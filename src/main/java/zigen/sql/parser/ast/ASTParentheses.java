/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;

public class ASTParentheses extends ASTAlias {

	private int endOffset;

	private int scope = 0;

	private boolean forFunction;

	public ASTParentheses(int offset, int length, int scope) {
		super("ASTParentheses", offset, length, scope);
	}

	public String getName(){
		return "()";
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (getAliasName() != null) {
			sb.append(getAliasName());
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public boolean isForFunction() {
		return forFunction;
	}

	public void setASTFunction(ASTFunction function) {
		this.function = function;
		this.forFunction = true;
	}

	ASTFunction function;
	public ASTFunction getASTFunction(){
		return function;
	}

	public boolean isOpen(){
		return (endOffset == 0);
	}

}
