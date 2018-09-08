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

public class TestInsertCause extends TestCase {
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
		in.append("INSERT");
		in.append("  INTO TBL VALUES('123',123)");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTInsertStatement text=\"insert\">\r\n");
		sb.append("  <ASTInto text=\"INTO\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTInto>\r\n");
		sb.append("  <ASTValues text=\"values\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTValue text=\"'123'\" />\r\n");
		sb.append("    <ASTComma text=\",\" />\r\n");
		sb.append("    <ASTValue text=\"123\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTValues>\r\n");
		sb.append(" </ASTInsertStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("INSERT");
		in.append("  INTO TBL (COL1, COL2)");
		in.append(" VALUES('123',123)");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTInsertStatement text=\"insert\">\r\n");
		sb.append("  <ASTInto text=\"INTO\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTInto>\r\n");
		sb.append("  <ASTParentheses text=\"\">\r\n");
		sb.append("   <ASTColumn text=\"COL1\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"COL2\" />\r\n");
		sb.append("  </ASTParentheses>\r\n");
		sb.append("  <ASTValues text=\"values\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTValue text=\"'123'\" />\r\n");
		sb.append("    <ASTComma text=\",\" />\r\n");
		sb.append("    <ASTValue text=\"123\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTValues>\r\n");
		sb.append(" </ASTInsertStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("INSERT");
		in.append("  INTO TBL");
		in.append(" SELECT * FROM TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTInsertStatement text=\"insert\">\r\n");
		sb.append("  <ASTInto text=\"INTO\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTInto>\r\n");
		sb.append("  <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("   <ASTSelect text=\"select\">\r\n");
		sb.append("    <ASTColumn text=\"*\" />\r\n");
		sb.append("   </ASTSelect>\r\n");
		sb.append("   <ASTFrom text=\"from\">\r\n");
		sb.append("    <ASTTable text=\"TBL\" />\r\n");
		sb.append("   </ASTFrom>\r\n");
		sb.append("  </ASTSelectStatement>\r\n");
		sb.append(" </ASTInsertStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
