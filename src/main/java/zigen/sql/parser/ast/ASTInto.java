/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;

import zigen.sql.parser.INode;
import zigen.sql.parser.exception.UnexpectedTokenException;

public class ASTInto extends ASTKeyword {
	boolean isIntoOutfileCause;
	
	ASTOutfile outfile;
	
	public ASTInto(String name, int offset, int length, int scope) {
		super(name, offset, length, scope);
	}

	public void addChild(INode n) {
		if (!isIntoOutfileCause) {
			super.addChild(n);

			if (n instanceof ASTOutfile) {
				isIntoOutfileCause = true;
				outfile = (ASTOutfile)n;
			}

		}else{
			throw new UnexpectedTokenException(n.getName(), offset, length);
		}

	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (isIntoOutfileCause) {
			sb.append(" ");
			sb.append(outfile.getName());
			sb.append(" ");
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}

	public ASTOutfile getOutfile() {
		return outfile;
	}

	public boolean hasASTOutfile(){
		return isIntoOutfileCause;
	}
}