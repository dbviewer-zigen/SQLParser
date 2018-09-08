/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;
import zigen.sql.parser.INode;
import zigen.sql.parser.exception.UnexpectedTokenException;


public class ASTCreateStatement extends ASTStatement {

    ASTType type;
    
	public ASTCreateStatement(int offset, int length, int scope) {
		super("create", offset, length, scope);
	}

	public void changeCreateOrReplace(){
		super.name = "create or replace";
	}
	
	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}
    public void addChild(INode n){
    	if(type == null && n instanceof ASTType){
    		super.addChild(n);
    		type = (ASTType)n;
    	}else{
    		throw new UnexpectedTokenException(n.getName(), offset, length);
    	}
    }
	public ASTType getASTType() {
		return type;
	}

	public boolean hasType(){
		return type != null && !"".equals(type);
	}
}
