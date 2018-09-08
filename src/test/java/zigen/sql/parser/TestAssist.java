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

public class TestAssist extends TestCase {
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
		in.append("SELECT");
		in.append("        COL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test11() {

		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("    FROM");
		in.append("        (");
		in.append("            SELECT");
		in.append("                    MG.USER_NAME");
		in.append("                FROM");
		in.append("                    BBS I");
		in.append("                WHERE");
		in.append("                    i.BBSID = 123");
		in.append("        ) Alias1, BBS_INFO Alias2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"Alias1\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"MG.USER_NAME\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"BBS AS I\">\r\n");
		sb.append("       <ASTInnerAlias text=\"I\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"i.BBSID\" />\r\n");
		sb.append("        <ASTValue text=\"123\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"Alias1\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"BBS_INFO AS Alias2\">\r\n");
		sb.append("    <ASTInnerAlias text=\"Alias2\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        FROM");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTFrom text=\"from\" />\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        FROM ");
		in.append("  WHERE ");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTFrom text=\"from\" />\r\n");
		sb.append("  <ASTWhere text=\"where\" />\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("     COL1   FROM TBL");
		in.append("  UNION SELECT  FROM TBL2 ");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL1\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL2\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test5() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT count(*) FROM MAAT0010 A");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"count\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"*\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0010 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test6() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT count(*) CNT FROM MAAT0010 A");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"count AS CNT\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"*\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"CNT\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0010 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test7() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        MAAT0010.bunrui");
		// in.append("    FROM");
		// in.append("        MAAT0010");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"MAAT0010.bunrui\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test8() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COUNT ( * )");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"COUNT\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"*\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test9() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COUNT(*)");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"COUNT\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"*\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test10() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        '1' COL1");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"'1' AS COL1\">\r\n");
		sb.append("    <ASTInnerAlias text=\"COL1\" />\r\n");
		sb.append("   </ASTValue>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
