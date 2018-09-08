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

public class TestCommentsCause extends TestCase {
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

	// /**
	// * �V���v���ȃJ����
	// *
	// */
	// public void test1() {
	// StringBuffer in = new StringBuffer();
	// in.append("SELECT");
	// in.append("        COL /*�R�����g*/");
	// in.append("    FROM");
	// in.append("        TBL");
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append("<Node text=\"root\">\r\n");
	// sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
	// sb.append("  <ASTSelect text=\"select\">\r\n");
	// sb.append("   <ASTSelectList text=\"SelectList\">\r\n");
	// sb.append("    <ASTColumn text=\"COL\" />\r\n");
	// sb.append("   </ASTSelectList>\r\n");
	// sb.append("  </ASTSelect>\r\n");
	// sb.append("  <ASTFrom text=\"from\">\r\n");
	// sb.append("   <ASTFromList text=\"FromList\">\r\n");
	// sb.append("    <ASTTable text=\"TBL\" />\r\n");
	// sb.append("   </ASTFromList>\r\n");
	// sb.append("  </ASTFrom>\r\n");
	// sb.append(" </ASTSelectStatement>\r\n");
	// sb.append("</Node>\r\n");
	// check(in.toString(), sb.toString());
	//
	// }
	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("/*aaaaaaa*/ SELECT /*aaa*/");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL -- hogehoge \n");
		in.append("    FROM");
		in.append("        TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}
}
