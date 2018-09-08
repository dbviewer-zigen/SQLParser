/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.ast;


public class ASTOutfile extends ASTKeyword {
	String filePath;

	public ASTOutfile(String name, int offset, int length, int scope) {
		super(name, offset, length, scope);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		if (getFilePath() != null) {
			sb.append(" \"");
			sb.append(filePath);
			sb.append("\"");
		}
		return getNodeClassName() + " text=\"" + sb.toString() + "\"";
	}
	
}
