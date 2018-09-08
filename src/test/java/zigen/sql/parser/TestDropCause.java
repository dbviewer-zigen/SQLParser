/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import junit.framework.TestCase;
import zigen.sql.format.SqlFormatRule;
import zigen.sql.parser.exception.ParserException;
import zigen.sql.parser.util.FileUtil;

public class TestDropCause extends TestCase {
	private void check(String in, String out) {
		try {

			ISqlParser parser = new SqlParser(in, new SqlFormatRule());
			INode node = new Node("root");
			parser.parse(node);

			ASTVisitorToString visitor = new ASTVisitorToString();
			node.accept(visitor, null);
			visitor.print();

			FileUtil.writeXml(getClass().getName(), parser.dumpXml(node));
			assertEquals(out, parser.dump(node));
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("DROP");
		in.append(" TABLE");
		in.append("  TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTDropStatement text=\"drop\">\r\n");
		sb.append("  <ASTType text=\"table\" />\r\n");
		sb.append("  <ASTTarget text=\"TBL\" />\r\n");
		sb.append(" </ASTDropStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

}
