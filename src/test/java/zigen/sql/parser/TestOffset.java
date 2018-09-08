/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import junit.framework.TestCase;
import zigen.sql.format.SqlFormatRule;
import zigen.sql.parser.ast.ASTParentheses;
import zigen.sql.parser.exception.ParserException;
import zigen.sql.parser.util.FileUtil;

public class TestOffset extends TestCase {
	private void check(String in, int begin, int end) {
		try {
			ISqlParser parser = new SqlParser(in, new SqlFormatRule());
			INode node = new Node("root");
			parser.parse(node);

			ASTVisitorToString visitor = new ASTVisitorToString();
			node.accept(visitor, null);
			visitor.print();
			FileUtil.writeXml(getClass().getName(), parser.dumpXml(node));

			ASTParentheses p = (ASTParentheses) ASTUtil.findChildDepth(node, "ASTParentheses");

			assertEquals(begin, p.getOffset());
			assertEquals(end, p.getEndOffset());
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}

	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("()");
		check(in.toString(), 0, 1);
	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("(     )");
		check(in.toString(), 0, 6);
	}

}
