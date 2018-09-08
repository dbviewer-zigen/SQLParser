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

public class TestPlsql extends TestCase {
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

	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append("    function\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"function\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");

		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append(" OR REPLACE\r\n");
		in.append("    function\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"function\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	// �����t
	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append(" OR REPLACE\r\n");
		in.append("    function\n");
		in.append("     SP_TEST2(abc) ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"function\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	// �����t
	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append(" OR REPLACE\r\n");
		in.append("    function\n");
		in.append("     COM.SP_TEST2(abc) ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"function\">\r\n");
		sb.append("   <ASTTarget text=\"COM.SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	// �����t
	public void test5() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");;
		in.append("    table\n");
		in.append("     COM.SP_TEST2(abc);");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"table\">\r\n");
		sb.append("   <ASTTarget text=\"COM.SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test6() {
		StringBuffer in = new StringBuffer();
		in.append("CREATE\r\n");
		in.append("    OR REPLACE PROCEDURE\r\n");
		in.append("     SP_TEST2 IS BEGIN NULL;\r\n");
		in.append("END;");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"PROCEDURE\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

}
