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

public class TestExpression extends TestCase {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        COL1 = COL2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
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
		in.append("select Where");
		in.append("        1 <> 2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"<>\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("     <ASTValue text=\"2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        1 + 2 - 3");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"-\">\r\n");
		sb.append("     <ASTOperator text=\"+\">\r\n");
		sb.append("      <ASTValue text=\"1\" />\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("     <ASTValue text=\"3\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        1 + 2 * 3");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"+\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("     <ASTOperator text=\"*\">\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("      <ASTValue text=\"3\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test5() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        1 * 2 + 3");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"+\">\r\n");
		sb.append("     <ASTOperator text=\"*\">\r\n");
		sb.append("      <ASTValue text=\"1\" />\r\n");
		sb.append("      <ASTValue text=\"2\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("     <ASTValue text=\"3\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test6() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        1 * 2 + 4 = ");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTOperator text=\"+\">\r\n");
		sb.append("      <ASTOperator text=\"*\">\r\n");
		sb.append("       <ASTValue text=\"1\" />\r\n");
		sb.append("       <ASTValue text=\"2\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("      <ASTValue text=\"4\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test7() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        1 * 2 + 4 = 5");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTOperator text=\"+\">\r\n");
		sb.append("      <ASTOperator text=\"*\">\r\n");
		sb.append("       <ASTValue text=\"1\" />\r\n");
		sb.append("       <ASTValue text=\"2\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("      <ASTValue text=\"4\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("     <ASTValue text=\"5\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test8() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        1 * 2 + 4 = 5 - 1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTOperator text=\"+\">\r\n");
		sb.append("      <ASTOperator text=\"*\">\r\n");
		sb.append("       <ASTValue text=\"1\" />\r\n");
		sb.append("       <ASTValue text=\"2\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("      <ASTValue text=\"4\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("     <ASTOperator text=\"-\">\r\n");
		sb.append("      <ASTValue text=\"5\" />\r\n");
		sb.append("      <ASTValue text=\"1\" />\r\n");
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
		in.append("select Where");
		in.append("        1 * 2 + 4 = COL2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTOperator text=\"+\">\r\n");
		sb.append("      <ASTOperator text=\"*\">\r\n");
		sb.append("       <ASTValue text=\"1\" />\r\n");
		sb.append("       <ASTValue text=\"2\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("      <ASTValue text=\"4\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test10() {
		StringBuffer in = new StringBuffer();
		in.append("select Where");
		in.append("        COL1 = COL2 AND COL3 = COL4");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\" />\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"COL3\" />\r\n");
		sb.append("     <ASTColumn text=\"COL4\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());
	}

	public void test11() {
		StringBuffer in = new StringBuffer();
		in.append("select (JISSEKI.M1ARARIGAKU - YOSAN.M1ARAGAKU) M1ARARISAI");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTParentheses text=\"M1ARARISAI\">\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"-\">\r\n");
		sb.append("      <ASTColumn text=\"JISSEKI.M1ARARIGAKU\" />\r\n");
		sb.append("      <ASTColumn text=\"YOSAN.M1ARAGAKU\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("    <ASTInnerAlias text=\"M1ARARISAI\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test12() {
		StringBuffer in = new StringBuffer();
		in.append("select \n");
		in.append("        NVL(\r\n");
		in.append("            ROUND(M1URIAGE / 1000,1)\r\n");
		in.append("            ,2\r\n");
		in.append("        ) M1URIAGE");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"NVL AS M1URIAGE\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTFunction text=\"ROUND\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTExpression text=\"expression\">\r\n");
		sb.append("        <ASTOperator text=\"/\">\r\n");
		sb.append("         <ASTColumn text=\"M1URIAGE\" />\r\n");
		sb.append("         <ASTValue text=\"1000\" />\r\n");
		sb.append("        </ASTOperator>\r\n");
		sb.append("        <ASTComma text=\",\" />\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTExpression>\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("     <ASTComma text=\",\" />\r\n");
		sb.append("     <ASTValue text=\"2\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"M1URIAGE\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

}
