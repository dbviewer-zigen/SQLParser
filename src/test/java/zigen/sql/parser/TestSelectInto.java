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

public class TestSelectInto extends TestCase {
	private void check(String in, String out) {
		try {

			SqlFormatRule rule = new SqlFormatRule();
			ISqlParser parser = new SqlParser(in, rule);
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
		in.append("SELECT * FROM user_m INTO OUTFILE \"/tmp/output.txt\"");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"user_m\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTInto text=\"INTO OUTFILE \">\r\n");
		sb.append("   <ASTOutfile text=\"OUTFILE \"\"/tmp/output.txt\"\"\" />\r\n");
		sb.append("  </ASTInto>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT * INTO OUTFILE \"/tmp/output.txt\" FROM user_m ");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTInto text=\"INTO OUTFILE \">\r\n");
		sb.append("   <ASTOutfile text=\"OUTFILE \"\"/tmp/output.txt\"\"\" />\r\n");
		sb.append("  </ASTInto>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"user_m\" />\r\n");
		sb.append("  </ASTFrom>\r\n");

		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

}
