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

public class TestExists extends TestCase {
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

	public void testSub() {
		StringBuffer in = new StringBuffer();
		in.append("(");
		in.append("    SELECT");
		in.append("            'X'");
		in.append("        FROM");
		in.append("            H_M_TEIKIKIHONMST_T B");
		in.append("        WHERE");
		in.append("            A.COOP_CD = B.COOP_CD");
		in.append("            AND A.KNO = B.KNO");
		in.append("            AND A.KIKANO = B.KIKANO");
		in.append("            AND B.COOP_CD = 493");
		in.append(")");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTParentheses text=\"\">\r\n");
		sb.append("  <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("   <ASTSelect text=\"select\">\r\n");
		sb.append("    <ASTValue text=\"'X'\" />\r\n");
		sb.append("   </ASTSelect>\r\n");
		sb.append("   <ASTFrom text=\"from\">\r\n");
		sb.append("    <ASTTable text=\"H_M_TEIKIKIHONMST_T AS B\">\r\n");
		sb.append("     <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("    </ASTTable>\r\n");
		sb.append("   </ASTFrom>\r\n");
		sb.append("   <ASTWhere text=\"where\">\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"A.COOP_CD\" />\r\n");
		sb.append("      <ASTColumn text=\"B.COOP_CD\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("    <ASTKeyword text=\"AND\" />\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"A.KNO\" />\r\n");
		sb.append("      <ASTColumn text=\"B.KNO\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("    <ASTKeyword text=\"AND\" />\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"A.KIKANO\" />\r\n");
		sb.append("      <ASTColumn text=\"B.KIKANO\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("    <ASTKeyword text=\"AND\" />\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"B.COOP_CD\" />\r\n");
		sb.append("      <ASTValue text=\"493\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("   </ASTWhere>\r\n");
		sb.append("  </ASTSelectStatement>\r\n");
		sb.append(" </ASTParentheses>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COOP_CD");
		in.append("        ,KNO");
		in.append("        ,KIKANO");
		in.append("    FROM");
		in.append("        H_T_KYONYUF_KJI_T A");
		in.append("    WHERE");
		in.append("        NOT EXISTS(");
		in.append("            SELECT");
		in.append("                    'X'");
		in.append("                FROM");
		in.append("                    H_M_TEIKIKIHONMST_T B");
		in.append("                WHERE");
		in.append("                    A.COOP_CD = B.COOP_CD");
		in.append("                    AND A.KNO = B.KNO");
		in.append("                    AND A.KIKANO = B.KIKANO");
		in.append("                    AND B.COOP_CD = 493");
		in.append("        )");
		in.append("        AND A.KNO = 72060 AND  A.KIKANO = 212181");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COOP_CD\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"KNO\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"KIKANO\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"H_T_KYONYUF_KJI_T AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTKeyword text=\"NOT\" />\r\n");
		sb.append("   <ASTKeyword text=\"EXISTS\" />\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTValue text=\"'X'\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"H_M_TEIKIKIHONMST_T AS B\">\r\n");
		sb.append("       <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"A.COOP_CD\" />\r\n");
		sb.append("        <ASTColumn text=\"B.COOP_CD\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"A.KNO\" />\r\n");
		sb.append("        <ASTColumn text=\"B.KNO\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"A.KIKANO\" />\r\n");
		sb.append("        <ASTColumn text=\"B.KIKANO\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"B.COOP_CD\" />\r\n");
		sb.append("        <ASTValue text=\"493\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"A.KNO\" />\r\n");
		sb.append("     <ASTValue text=\"72060\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"A.KIKANO\" />\r\n");
		sb.append("     <ASTValue text=\"212181\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
