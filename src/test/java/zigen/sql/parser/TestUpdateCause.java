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

public class TestUpdateCause extends TestCase {
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
		in.append("UPDATE TBL");
		in.append("  SET COL1='123',COL2=123");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTUpdateStatement text=\"update\">\r\n");
		sb.append("  <ASTTable text=\"TBL\" />\r\n");
		sb.append("  <ASTSet text=\"set\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"'123'\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTComma text=\",\" />\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("     <ASTValue text=\"123\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSet>\r\n");
		sb.append(" </ASTUpdateStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("UPDATE TBL");
		in.append("  SET COL1='123',COL2=COL1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTUpdateStatement text=\"update\">\r\n");
		sb.append("  <ASTTable text=\"TBL\" />\r\n");
		sb.append("  <ASTSet text=\"set\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"'123'\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTComma text=\",\" />\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSet>\r\n");
		sb.append(" </ASTUpdateStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("UPDATE TBL");
		in.append("  SET COL1='123',COL2=ABC");
		in.append("  WHERE COL1='123' AND COL2='ABC'");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTUpdateStatement text=\"update\">\r\n");
		sb.append("  <ASTTable text=\"TBL\" />\r\n");
		sb.append("  <ASTSet text=\"set\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"'123'\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTComma text=\",\" />\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("     <ASTColumn text=\"ABC\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSet>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTValue text=\"'123'\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("     <ASTValue text=\"'ABC'\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTUpdateStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �V���v���ȃJ����
	 *
	 */
	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("UPDATE");
		in.append("        COM");
		in.append("    SET");
		in.append("        BBB = '111'");
		in.append("    WHERE");
		in.append("        BBB IN (");
		in.append("            SELECT");
		in.append("                    BBB");
		in.append("                FROM");
		in.append("                    COM");
		in.append("        )");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTUpdateStatement text=\"update\">\r\n");
		sb.append("  <ASTTable text=\"COM\" />\r\n");
		sb.append("  <ASTSet text=\"set\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"BBB\" />\r\n");
		sb.append("     <ASTValue text=\"'111'\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSet>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTColumn text=\"BBB\" />\r\n");
		sb.append("   <ASTKeyword text=\"IN\" />\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"BBB\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"COM\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTUpdateStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test5() {
		StringBuffer in = new StringBuffer();
		in.append("update tbl set col = 1, col2 = 2 where a = 1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTUpdateStatement text=\"update\">\r\n");
		sb.append("  <ASTTable text=\"tbl\" />\r\n");
		sb.append("  <ASTSet text=\"set\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"col\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTComma text=\",\" />\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"col2\" />\r\n");
		sb.append("     <ASTValue text=\"2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSet>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"a\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTUpdateStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

}
