/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.ASTVisitor;
import zigen.sql.parser.INode;
import zigen.sql.parser.exception.UnexpectedTokenException;


public class ASTType extends ASTKeyword {
    boolean isPackageBody;

    ASTTarget target;

	public ASTType(String type, int offset, int length, int scope) {
		super(type, offset, length, scope);
	}

	public Object accept(ASTVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

    public boolean isPackageBody() {
        return isPackageBody;
    }

    public void addChild(INode n){
    	if(target == null & n instanceof ASTTarget){
    		super.addChild(n);
    		target = (ASTTarget)n;
    	}else{
    		throw new UnexpectedTokenException(n.getName(), offset, length);
    	}
    }

    public void setPackageBody(boolean isPackageBody) {
        //if("package".equals(name)){
    	if("package".equalsIgnoreCase(name)){
            this.isPackageBody = isPackageBody;
            if(isPackageBody){
                name = "package body";
            }else{
                name = "package";
            }
        }else{
            throw new IllegalStateException("This Node's ID is not package");
        }
    }

	public ASTTarget getASTTarget() {
		return target;
	}

	public boolean hasTarget(){
		return target != null && !"".equals(target);
	}
}
