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

public class TestSelectCause extends TestCase {
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
		in.append("SELECT");
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

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test2222() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        1");
		in.append("        ,DECODE(A, 1) HOGE");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    JIHATUCNT");
		in.append("                FROM");
		in.append("                    MAAT0020");
		in.append("        ) JIHATU2");
		in.append("    FROM");
		in.append("        MAAT0010 SYOHIN");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"1\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"DECODE AS HOGE\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"A\" />\r\n");
		sb.append("     <ASTComma text=\",\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"HOGE\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"JIHATU2\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"JIHATUCNT\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAT0020\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"JIHATU2\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0010 AS SYOHIN\">\r\n");
		sb.append("    <ASTInnerAlias text=\"SYOHIN\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �ʖ��̃J����
	 *
	 */
	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL A");
		in.append("    FROM");
		in.append("        TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTColumn>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �ʖ��̃J����
	 *
	 */
	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL AS A");
		in.append("    FROM");
		in.append("        TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTColumn>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL, COL2");
		in.append("    FROM");
		in.append("        TBL");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"COL2\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test5() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        TBL.COL");
		in.append("    FROM");
		in.append("        TBL");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TBL.COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test6() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        TBL.*");
		in.append("    FROM");
		in.append("        TBL");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TBL.*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	// public void test7() {
	// StringBuffer in = new StringBuffer();
	// in.append("SELECT");
	// in.append(" TBL.*");
	// in.append(" FROM");
	// in.append(" TBL -- A");
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append("<Node text=\"root\">\r\n");
	// sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
	// sb.append(" <ASTSelect text=\"select\">\r\n");
	// sb.append(" <ASTSelectList text=\"SelectList\">\r\n");
	// sb.append(" <ASTColumn text=\"TBL.*\" />\r\n");
	// sb.append(" </ASTSelectList>\r\n");
	// sb.append(" </ASTSelect>\r\n");
	// sb.append(" <ASTFrom text=\"from\">\r\n");
	// sb.append(" <ASTFromList text=\"FromList\">\r\n");
	// sb.append(" <ASTTable text=\"TBL\" />\r\n");
	// sb.append(" </ASTFromList>\r\n");
	// sb.append(" </ASTFrom>\r\n");
	// sb.append(" </ASTSelectStatement>\r\n");
	// sb.append("</Node>\r\n");
	//
	// //String in = in.toString().replaceAll(".*--", replacement)
	//
	// check(in.toString(), sb.toString());
	//
	// }
	public void testLineCommentSupport() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        TBL.*-- B\r");
		in.append("    FROM");
		in.append("        TBL B -- A");

		StringBuffer out = new StringBuffer();
		out.append("SELECT");
		out.append("        TBL.*");
		out.append("    FROM");
		out.append("        TBL B ");

		String con = in.toString().replaceAll("--.*(\r\n|\r|\n|$)", "");
		// System.out.println(con);
		assertEquals(con, out.toString());
	}

	public void testSubQuery() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        EMPNO");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    EMPNO");
		in.append("                FROM");
		in.append("                    EMP");
		in.append("        ) MAXEMP");
		in.append("    FROM");
		in.append("        EMP");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"EMPNO\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"MAXEMP\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"EMPNO\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"EMP\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"MAXEMP\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"EMP\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testSubQuery4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        EMPNO");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    EMPNO");
		in.append("                FROM");
		in.append("                    EMP");
		in.append("        ) MAXEMP");
		in.append("    ,");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"EMPNO\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"MAXEMP\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"EMPNO\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"EMP\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"MAXEMP\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testSubQuery42() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        max(EMPNO, HOGE) A");
		in.append("    FROM EMP");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"max AS A\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"EMPNO\" />\r\n");
		sb.append("     <ASTComma text=\",\" />\r\n");
		sb.append("     <ASTColumn text=\"HOGE\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"EMP\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
