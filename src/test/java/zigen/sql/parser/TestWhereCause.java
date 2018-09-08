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

public class TestWhereCause extends TestCase {
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
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        COL1=COL2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        COL1=1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTColumn text=\"COL1\" />\r\n");
		sb.append("   <ASTOperator text=\"=\" />\r\n");
		sb.append("   <ASTValue text=\"1\" />\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>");

	}

	/**
	 * ���̃e�X�g
	 */
	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        10 + 100 * 2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"+\">\r\n");
		sb.append("     <ASTValue text=\"10\" />\r\n");
		sb.append("     <ASTOperator text=\"*\">\r\n");
		sb.append("      <ASTValue text=\"100\" />\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	/**
	 * ���̃e�X�g
	 */
	public void test3a() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        (10 + 100) * 2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"*\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"+\">\r\n");
		sb.append("        <ASTValue text=\"10\" />\r\n");
		sb.append("        <ASTValue text=\"100\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("     <ASTValue text=\"2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test3b() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        10 = 10");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTValue text=\"10\" />\r\n");
		sb.append("     <ASTValue text=\"10\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        COL1='123'");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"'123'\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test6() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        COL1=100");
		in.append("     AND (COL2=200");
		in.append("     OR COL3=300)");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"100\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"COL2\" />\r\n");
		sb.append("      <ASTValue text=\"200\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("    <ASTKeyword text=\"OR\" />\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"COL3\" />\r\n");
		sb.append("      <ASTValue text=\"300\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test7() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        COL1=100 /2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTOperator text=\"/\">\r\n");
		sb.append("      <ASTValue text=\"100\" />\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test8() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL");
		in.append("    WHERE");
		in.append("        COL1=100 /2 AND COL2=200 /4");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTOperator text=\"/\">\r\n");
		sb.append("      <ASTValue text=\"100\" />\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("     <ASTOperator text=\"/\">\r\n");
		sb.append("      <ASTValue text=\"200\" />\r\n");
		sb.append("      <ASTValue text=\"4\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");

		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test9() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT * FROM EMP");
		in.append("  WHERE DEPTNO IN (SELECT DEPTNO FROM DEPT)");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"*\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"EMP\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTColumn text=\"DEPTNO\" />\r\n");
		sb.append("   <ASTKeyword text=\"IN\" />\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"DEPTNO\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"DEPT\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test11() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL A");
		in.append("    WHERE");
		in.append("        (");
		in.append("            (");
		in.append("                TO_NUMBER(TO_CHAR(SYSDATE, 'YYYYMMDD')) <= YOTEI");
		in.append("                AND YOTEI <= 20070314");
		in.append("            )");
		in.append("            OR YOTEI = 0");
		in.append("        )");
		in.append("        AND TENCD_LAST = 1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"<=\">\r\n");
		sb.append("       <ASTFunction text=\"TO_NUMBER\">\r\n");
		sb.append("        <ASTParentheses text=\"\">\r\n");
		sb.append("         <ASTFunction text=\"TO_CHAR\">\r\n");
		sb.append("          <ASTParentheses text=\"\">\r\n");
		sb.append("           <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("           <ASTComma text=\",\" />\r\n");
		sb.append("           <ASTValue text=\"'YYYYMMDD'\" />\r\n");
		sb.append("          </ASTParentheses>\r\n");
		sb.append("         </ASTFunction>\r\n");
		sb.append("        </ASTParentheses>\r\n");
		sb.append("       </ASTFunction>\r\n");
		sb.append("       <ASTColumn text=\"YOTEI\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("     <ASTKeyword text=\"AND\" />\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"<=\">\r\n");
		sb.append("       <ASTColumn text=\"YOTEI\" />\r\n");
		sb.append("       <ASTValue text=\"20070314\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTKeyword text=\"OR\" />\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"YOTEI\" />\r\n");
		sb.append("      <ASTValue text=\"0\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"TENCD_LAST\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test12() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        1");
		in.append("    FROM");
		in.append("        DUAL");
		in.append("    WHERE");
		in.append("        (SELECT 1 FROM DUAL) = 1");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"1\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"DUAL\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");

		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test13() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        1");
		in.append("    FROM");
		in.append("        DUAL");
		in.append("    WHERE");
		in.append("        1 = (SELECT 1 FROM DUAL) ");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"1\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"DUAL\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");

		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test14() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        1");
		in.append("    FROM");
		in.append("        DUAL");
		in.append("    WHERE");
		in.append("        1 + (2) ");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"1\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"+\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void test15() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        T.TENCD");
		in.append("    FROM");
		in.append("        MAAT0010 S");
		in.append("    WHERE");
		in.append("        T.YMD <= TO_CHAR(SYSDATE, 'YYYYMMDD')");
		in.append("        AND S.SYOHIN = T.SYOHIN");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"T.TENCD\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0010 AS S\">\r\n");
		sb.append("    <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"<=\">\r\n");
		sb.append("     <ASTColumn text=\"T.YMD\" />\r\n");
		sb.append("     <ASTFunction text=\"TO_CHAR\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("       <ASTComma text=\",\" />\r\n");
		sb.append("       <ASTValue text=\"'YYYYMMDD'\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTKeyword text=\"AND\" />\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("     <ASTColumn text=\"T.SYOHIN\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test16() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        T.TENCD");
		in.append("    FROM");
		in.append("        MAAT0010 S");
		in.append("    WHERE");
		in.append("        T.YMD <= TO_CHAR(SYSDATE, 'YYYYMMDD') + 2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"T.TENCD\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0010 AS S\">\r\n");
		sb.append("    <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"<=\">\r\n");
		sb.append("     <ASTColumn text=\"T.YMD\" />\r\n");
		sb.append("     <ASTOperator text=\"+\">\r\n");
		sb.append("      <ASTFunction text=\"TO_CHAR\">\r\n");
		sb.append("       <ASTParentheses text=\"\">\r\n");
		sb.append("        <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("        <ASTComma text=\",\" />\r\n");
		sb.append("        <ASTValue text=\"'YYYYMMDD'\" />\r\n");
		sb.append("       </ASTParentheses>\r\n");
		sb.append("      </ASTFunction>\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
