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

public class TestCreatesql extends TestCase {
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

	public void test1A() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append("    function\n");
		in.append("     SC.SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"function\">\r\n");
		sb.append("   <ASTTarget text=\"SC.SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_1() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append("    procedure\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"procedure\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_2() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append("    trigger\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"trigger\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_3() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append("    package\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"package\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");;
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_4() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append("    package body\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"package body\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");;
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_5() {
		StringBuffer in = new StringBuffer();
		in.append("create or replace \n");
		in.append("    function\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"function\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");;;
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_6() {
		StringBuffer in = new StringBuffer();
		in.append("create or replace \n");
		in.append("    procedure\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"procedure\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");;
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_7() {
		StringBuffer in = new StringBuffer();
		in.append("create or replace \n");
		in.append("    trigger\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"trigger\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");;
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_8() {
		StringBuffer in = new StringBuffer();
		in.append("create or replace \n");
		in.append("    package\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"package\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");;
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test1_9() {
		StringBuffer in = new StringBuffer();
		in.append("create or replace \n");
		in.append("    package body\n");
		in.append("     SP_TEST2 ABC;\n  ABC\n ABC;");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">\r\n");
		sb.append("  <ASTType text=\"package body\">\r\n");
		sb.append("   <ASTTarget text=\"SP_TEST2\" />\r\n");
		sb.append("  </ASTType>\r\n");;
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append(" OR REPLACE \r\n");
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

	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append(" OR REPLACE \r\n");
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

	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("create\n");
		in.append(" OR REPLACE \r\n");
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

	public void testSequence() {
		StringBuffer in = new StringBuffer();
		in.append("create sequence test_seq START WITH 1 INCREMENT BY 1 NOCACHE;");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"sequence\">\r\n");
		sb.append("   <ASTTarget text=\"test_seq\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void testSequence2() {
		StringBuffer in = new StringBuffer();
		in.append("create sequence sc.test_seq START WITH 1 INCREMENT BY 1 NOCACHE;");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"sequence\">\r\n");
		sb.append("   <ASTTarget text=\"sc.test_seq\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void testCreateFunc() {
		StringBuffer in = new StringBuffer();
		in.append("�@CREATE FUNCTION tesf(dt IN NUMBER) RETURN NUMBER\r\n");
		in.append("�@IS\r\n");
		in.append("�@�@d NUMBER;\r\n");
		in.append("�@BEGIN\r\n");
		in.append("�@�@d := dt * 2;\r\n");
		in.append("�@�@RETURN d;\r\n");
		in.append("�@END;\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create\">\r\n");
		sb.append("  <ASTType text=\"FUNCTION\">\r\n");
		sb.append("   <ASTTarget text=\"tesf\" />\r\n");
		sb.append("  </ASTType>\r\n");
		sb.append(" </ASTCreateStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void testCreatePackageBody(){
		StringBuffer in = new StringBuffer();
		in.append("CREATE OR REPLACE PACKAGE aa_test_pk IS").append("\r\n");
		in.append("-- --------------------").append("\r\n");
		in.append("-- �v���V�[�W�����Ftest_pk").append("\r\n");
		in.append("-- --------------------").append("\r\n");
		in.append("PROCEDURE test_pk(").append("\r\n");
		in.append("aaaa IN NUMBER DEFAULT 0").append("\r\n");
		in.append(");").append("\r\n");
		in.append("END aa_test_pk ;").append("\r\n");
		in.append("2.�{�f�B��").append("\r\n");
		in.append("CREATE OR REPLACE PACKAGE BODY aa_test_pk IS").append("\r\n");
		in.append("-- --------------------").append("\r\n");
		in.append("-- �v���V�[�W�����Ftest_pk").append("\r\n");
		in.append("-- --------------------").append("\r\n");
		in.append("PROCEDURE test_pk(").append("\r\n");
		in.append("aaaa IN NUMBER DEFAULT 0").append("\r\n");
		in.append(") IS").append("\r\n");
		in.append("b_result BOOLEAN DEFAUTE FALSE;").append("\r\n");
		in.append("BEGIN").append("\r\n");
		in.append("IF ( b_result = FALSE) THEN").append("\r\n");
		in.append("RETURN;").append("\r\n");
		in.append("END IF;").append("\r\n");
		in.append("EXCEPTION").append("\r\n");
		in.append("WHEN OTHERS THEN").append("\r\n");
		in.append("NULL;").append("\r\n");
		in.append("END aa_test_pk ;").append("\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTCreateStatement text=\"create or replace\">").append("\r\n");
		sb.append("  <ASTType text=\"package body\">").append("\r\n");
		sb.append("   <ASTTarget text=\"aa_test_pk\" />").append("\r\n");
		sb.append("  </ASTType>").append("\r\n");
		sb.append(" </ASTCreateStatement>").append("\r\n");
		sb.append("</Node>").append("\r\n");

		check(in.toString(), sb.toString());

	}
}
