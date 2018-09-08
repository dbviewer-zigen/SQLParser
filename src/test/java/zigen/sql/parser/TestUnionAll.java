/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import junit.framework.TestCase;
import zigen.sql.format.SqlFormatRule;
import zigen.sql.parser.ast.ASTRoot;
import zigen.sql.parser.exception.ParserException;
import zigen.sql.parser.util.FileUtil;

public class TestUnionAll extends TestCase {
	private void check(String in, String out) {
		try {

			ISqlParser parser = new SqlParser(in, new SqlFormatRule());
			INode node = new ASTRoot();
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
	 * UNION
	 *
	 */
	public void testUnion() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        SYSDATE");
		in.append("    FROM");
		in.append("        DUAL ");
		in.append("UNION ");
		in.append(" SELECT");
		in.append("        SYSDATE");
		in.append("    FROM");
		in.append("        DUAL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	/**
	 * UNION
	 *
	 */
	public void testUnionA() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        SYSDATE");
		in.append("    FROM");
		in.append("        DUAL1 ");
		in.append(" UNION ");
		in.append(" SELECT");
		in.append("        SYSDATE");
		in.append("    FROM");
		in.append("        DUAL2");
		in.append(" UNION ");
		in.append(" SELECT");
		in.append("        SYSDATE");
		in.append("    FROM");
		in.append("        DUAL3");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL1\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL2\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL3\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	/**
	 * UNION ALL
	 *
	 */
	public void testUnionAll() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        SYSDATE");
		in.append("    FROM");
		in.append("        DUAL ");
		in.append("UNION ");
		in.append("ALL SELECT");
		in.append("        SYSDATE");
		in.append("    FROM");
		in.append("        DUAL");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion All\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * UNION
	 *
	 */
	public void testUnion2() {
		StringBuffer in = new StringBuffer();
		in.append("(SELECT SYSDATE FROM DUAL)");
		in.append(" UNION");
		in.append("(SELECT SYSDATE FROM DUAL)");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTParentheses text=\"\">\r\n");
		sb.append("  <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("   <ASTSelect text=\"select\">\r\n");
		sb.append("    <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("   </ASTSelect>\r\n");
		sb.append("   <ASTFrom text=\"from\">\r\n");
		sb.append("    <ASTTable text=\"DUAL\" />\r\n");
		sb.append("   </ASTFrom>\r\n");
		sb.append("  </ASTSelectStatement>\r\n");
		sb.append(" </ASTParentheses>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append(" <ASTParentheses text=\"\">\r\n");
		sb.append("  <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("   <ASTSelect text=\"select\">\r\n");
		sb.append("    <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("   </ASTSelect>\r\n");
		sb.append("   <ASTFrom text=\"from\">\r\n");
		sb.append("    <ASTTable text=\"DUAL\" />\r\n");
		sb.append("   </ASTFrom>\r\n");
		sb.append("  </ASTSelectStatement>\r\n");
		sb.append(" </ASTParentheses>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	/**
	 * UNION
	 *
	 */
	public void testUnion3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT A.* FROM (");
		in.append(" (SELECT COL1 FROM TBL1)");
		in.append(" UNION");
		in.append(" (SELECT COL1 FROM TBL2)");
		in.append(" ) A");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"A.*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"A\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL1\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL2\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testUnion4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT SYSDATE FROM(");
		in.append(" ( SELECT  COL1 FROM DUAL1 )");
		in.append(" UNION");
		in.append(" ( SELECT  COL1 FROM DUAL2 ))");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"DUAL1\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"DUAL2\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testUnion5() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT SYSDATE FROM(");
		in.append("  SELECT  COL1 FROM DUAL1 ");
		in.append(" UNION");
		in.append("  SELECT  COL1 FROM DUAL2 )");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"DUAL1\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"DUAL2\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testUnion6() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        SYSDATE\r\n");
		in.append("    FROM\r\n");
		in.append("        (\r\n");
		in.append("            SELECT\r\n");
		in.append("                    COL1\r\n");
		in.append("                FROM\r\n");
		in.append("                    DUAL1\r\n");
		in.append("        )\r\n");
		in.append("UNION\r\n");
		in.append("(\r\n");
		in.append("    SELECT\r\n");
		in.append("            COL1\r\n");
		in.append("        FROM\r\n");
		in.append("            DUAL2\r\n");
		in.append(")\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"DUAL1\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append(" <ASTParentheses text=\"\">\r\n");
		sb.append("  <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("   <ASTSelect text=\"select\">\r\n");
		sb.append("    <ASTColumn text=\"COL1\" />\r\n");
		sb.append("   </ASTSelect>\r\n");
		sb.append("   <ASTFrom text=\"from\">\r\n");
		sb.append("    <ASTTable text=\"DUAL2\" />\r\n");
		sb.append("   </ASTFrom>\r\n");
		sb.append("  </ASTSelectStatement>\r\n");
		sb.append(" </ASTParentheses>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testUnion7() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        SYSDATE\r\n");
		in.append("    FROM\r\n");
		in.append("        (\r\n");
		in.append("            SELECT\r\n");
		in.append("                    COL1\r\n");
		in.append("                FROM\r\n");
		in.append("                    DUAL1\r\n");
		in.append("        )\r\n");
		in.append("UNION All\r\n");
		in.append("(\r\n");
		in.append("    SELECT\r\n");
		in.append("            COL1\r\n");
		in.append("        FROM\r\n");
		in.append("            DUAL2\r\n");
		in.append(")\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"DUAL1\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion All\" />\r\n");
		sb.append(" <ASTParentheses text=\"\">\r\n");
		sb.append("  <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("   <ASTSelect text=\"select\">\r\n");
		sb.append("    <ASTColumn text=\"COL1\" />\r\n");
		sb.append("   </ASTSelect>\r\n");
		sb.append("   <ASTFrom text=\"from\">\r\n");
		sb.append("    <ASTTable text=\"DUAL2\" />\r\n");
		sb.append("   </ASTFrom>\r\n");
		sb.append("  </ASTSelectStatement>\r\n");
		sb.append(" </ASTParentheses>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	/**
	 * UNION
	 *
	 */
	public void testUnion1() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT A.* FROM (");
		in.append(" (SELECT COL1 FROM TBL1)");
		in.append(" UNION");
		in.append(" (SELECT COL1 FROM TBL2)");
		in.append(" ) A");
		in.append(" WHERE COL1 = 1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"A.*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"A\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL1\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL2\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	/**
	 * UNION
	 *
	 */
	public void testUnionAA() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        A.*\r\n");
		in.append("    FROM\r\n");
		in.append("        (\r\n");
		in.append("            (\r\n");
		in.append("                SELECT\r\n");
		in.append("                        COL1\r\n");
		in.append("                    FROM\r\n");
		in.append("                        TBL1\r\n");
		in.append("            )\r\n");
		in.append("            UNION\r\n");
		in.append("            (\r\n");
		in.append("                SELECT\r\n");
		in.append("                        COL1\r\n");
		in.append("                    FROM\r\n");
		in.append("                        TBL2\r\n");
		in.append("            )\r\n");
		in.append("        ) A\r\n");
		in.append("    WHERE\r\n");
		in.append("        COL1 = 1\r\n");
		in.append("UNION ALL\r\n");
		in.append("SELECT\r\n");
		in.append("        B.*\r\n");
		in.append("    FROM\r\n");
		in.append("        (\r\n");
		in.append("            (\r\n");
		in.append("                SELECT\r\n");
		in.append("                        COL1\r\n");
		in.append("                    FROM\r\n");
		in.append("                        TBL1\r\n");
		in.append("            )\r\n");
		in.append("            UNION\r\n");
		in.append("            (\r\n");
		in.append("                SELECT\r\n");
		in.append("                        COL1\r\n");
		in.append("                    FROM\r\n");
		in.append("                        TBL2\r\n");
		in.append("            )\r\n");
		in.append("        ) B\r\n");
		in.append("    WHERE\r\n");
		in.append("        COL1 = 2\r\n");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"A.*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"A\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL1\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL2\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion All\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"B.*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"B\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL1\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("      <ASTSelect text=\"select\">\r\n");
		sb.append("       <ASTColumn text=\"COL1\" />\r\n");
		sb.append("      </ASTSelect>\r\n");
		sb.append("      <ASTFrom text=\"from\">\r\n");
		sb.append("       <ASTTable text=\"TBL2\" />\r\n");
		sb.append("      </ASTFrom>\r\n");
		sb.append("     </ASTSelectStatement>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}
}
