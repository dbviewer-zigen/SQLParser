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

public class TestFunction extends TestCase {
	private void check(String in, String out) {
		try {

			SqlFormatRule rule = new SqlFormatRule();
			rule.addFunctions(new String[] {
				"row_number"
			});
			ISqlParser parser = new SqlParser(in, rule);
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

	public void testFunction() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT COUNT(COL1) CNT FROM TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"COUNT AS CNT\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"COL1\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"CNT\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT COL1 FROM TBL WHERE MAX(COL) = 1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL1\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTFunction text=\"MAX\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTColumn text=\"COL\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        DECODE(B.COL1");
		in.append("            ,1,(");
		in.append("                SELECT");
		in.append("                        MAX(100)");
		in.append("                    FROM");
		in.append("                        DUAL");
		in.append("            )");
		in.append("        ) COL2");
		in.append("    FROM");
		in.append("        (");
		in.append("            SELECT");
		in.append("                    '1' COL1");
		in.append("                FROM");
		in.append("                    DUAL");
		in.append("        ) B");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"DECODE AS COL2\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"B.COL1\" />\r\n");
		sb.append("     <ASTComma text=\",\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("     <ASTComma text=\",\" />\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTFunction text=\"MAX\">\r\n");
		sb.append("         <ASTParentheses text=\"\">\r\n");
		sb.append("          <ASTValue text=\"100\" />\r\n");
		sb.append("         </ASTParentheses>\r\n");
		sb.append("        </ASTFunction>\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"DUAL\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"COL2\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"B\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTValue text=\"'1' AS COL1\">\r\n");
		sb.append("       <ASTInnerAlias text=\"COL1\" />\r\n");
		sb.append("      </ASTValue>\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"DUAL\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        DECODE(B.COL1, 1) COL2");
		in.append("    FROM");
		in.append("        DUAL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"DECODE AS COL2\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"B.COL1\" />\r\n");
		sb.append("     <ASTComma text=\",\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"COL2\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"DUAL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction5() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT COUNT(*) CNT FROM TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"COUNT AS CNT\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"*\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"CNT\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction6() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        TENCD\r\n");
		// in.append("        ,FLOOR(MIN(YMD) / 100) * 100 MIN_YMD\r\n");
		// in.append("        ,FLOOR(MIN(YMD) / 100) * 100 MIN_YMD\r\n");
		in.append("        ,MAX(YMD) * 100 MAX_YMD\r\n");
		in.append("        ,MAX(YMD) * 100 MAX_YMD\r\n");
		in.append("    FROM\r\n");
		in.append("        MAAT0160\r\n");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTExpression text=\"expression MAX_YMD\">\r\n");
		sb.append("    <ASTOperator text=\"*\">\r\n");
		sb.append("     <ASTFunction text=\"MAX\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTColumn text=\"YMD\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("     <ASTValue text=\"100\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTInnerAlias text=\"MAX_YMD\" />\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTExpression text=\"expression MAX_YMD\">\r\n");
		sb.append("    <ASTOperator text=\"*\">\r\n");
		sb.append("     <ASTFunction text=\"MAX\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTColumn text=\"YMD\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("     <ASTValue text=\"100\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTInnerAlias text=\"MAX_YMD\" />\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0160\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction7() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        COUNT(YMD) + 1 CNT \r\n");
		// in.append("        FLOOR(MIN(YMD) / 100) * 100 \r\n");
		in.append("    FROM\r\n");
		in.append("        MAAT0160\r\n");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTExpression text=\"expression CNT\">\r\n");
		sb.append("    <ASTOperator text=\"+\">\r\n");
		sb.append("     <ASTFunction text=\"COUNT\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTColumn text=\"YMD\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("    <ASTInnerAlias text=\"CNT\" />\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0160\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	public void testFunction8() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        MAX(COUNT(YMD) + 1) CNT \r\n");
		// in.append("        FLOOR(MIN(YMD) / 100) * 100 \r\n");
		in.append("    FROM\r\n");
		in.append("        MAAT0160\r\n");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"MAX AS CNT\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"+\">\r\n");
		sb.append("       <ASTFunction text=\"COUNT\">\r\n");
		sb.append("        <ASTParentheses text=\"\">\r\n");
		sb.append("         <ASTColumn text=\"YMD\" />\r\n");
		sb.append("        </ASTParentheses>\r\n");
		sb.append("       </ASTFunction>\r\n");
		sb.append("       <ASTValue text=\"1\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"CNT\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0160\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction9() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        MAX(MIN(YMD) / 200) * 1\r\n");
		in.append("    FROM\r\n");
		in.append("        MAAT0160\r\n");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"*\">\r\n");
		sb.append("     <ASTFunction text=\"MAX\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTExpression text=\"expression\">\r\n");
		sb.append("        <ASTOperator text=\"/\">\r\n");
		sb.append("         <ASTFunction text=\"MIN\">\r\n");
		sb.append("          <ASTParentheses text=\"\">\r\n");
		sb.append("           <ASTColumn text=\"YMD\" />\r\n");
		sb.append("          </ASTParentheses>\r\n");
		sb.append("         </ASTFunction>\r\n");
		sb.append("         <ASTValue text=\"200\" />\r\n");
		sb.append("        </ASTOperator>\r\n");
		sb.append("       </ASTExpression>\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAAT0160\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction10() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("       \r\n");
		in.append("        SUM(SIRESU - SRHENPINSU)\r\n");
		in.append("        ,SUM(SIREGNK - SRHENPINGNK)\r\n");
		in.append("        ,SUM(SIREBIK - SRHENPINBIK)\r\n");
		in.append("        ,SUM(SRHENPINSU)\r\n");
		in.append("        ,SUM(SRHENPINGNK)\r\n");
		in.append("        ,SUM(SRHENPINBIK)\r\n");
		in.append("    FROM\r\n");
		in.append("        MAMT0120\r\n");
		in.append("    WHERE\r\n");
		in.append("        SUM(BUNRUI3) + SUM(HOGE) > 0\r\n");
		in.append("        AND TENCD = 1\r\n");
		in.append("        AND MONTH = 2\r\n");
		in.append("        AND YMDO = 200702\r\n");
		in.append("        AND BUNRUI = 1\r\n");
		in.append("        AND BUNRUI2 = 1\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"SUM\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"-\">\r\n");
		sb.append("       <ASTColumn text=\"SIRESU\" />\r\n");
		sb.append("       <ASTColumn text=\"SRHENPINSU\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"SUM\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"-\">\r\n");
		sb.append("       <ASTColumn text=\"SIREGNK\" />\r\n");
		sb.append("       <ASTColumn text=\"SRHENPINGNK\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"SUM\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"-\">\r\n");
		sb.append("       <ASTColumn text=\"SIREBIK\" />\r\n");
		sb.append("       <ASTColumn text=\"SRHENPINBIK\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"SUM\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"SRHENPINSU\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"SUM\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"SRHENPINGNK\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"SUM\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"SRHENPINBIK\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAMT0120\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\">\">\r\n");
		sb.append("     <ASTOperator text=\"+\">\r\n");
		sb.append("      <ASTFunction text=\"SUM\">\r\n");
		sb.append("       <ASTParentheses text=\"\">\r\n");
		sb.append("        <ASTColumn text=\"BUNRUI3\" />\r\n");
		sb.append("       </ASTParentheses>\r\n");
		sb.append("      </ASTFunction>\r\n");
		sb.append("      <ASTFunction text=\"SUM\">\r\n");
		sb.append("       <ASTParentheses text=\"\">\r\n");
		sb.append("        <ASTColumn text=\"HOGE\" />\r\n");
		sb.append("       </ASTParentheses>\r\n");
		sb.append("      </ASTFunction>\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("     <ASTValue text=\"0\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"MONTH\" />\r\n");
		sb.append("     <ASTValue text=\"2\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"YMDO\" />\r\n");
		sb.append("     <ASTValue text=\"200702\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"BUNRUI\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"BUNRUI2\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction11() {

		// String test = "abc \r\n bbb \r\n aaa";
		// System.out.println(test.replaceAll("\\r\\n|\\r|\\n", ""));

		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        TABLE_NAME\r\n");
		in.append("        ,COLUMN_NAME\r\n");
		in.append("        ,row_num\r\n");
		in.append("    FROM\r\n");
		in.append("        (\r\n");
		in.append("            SELECT\r\n");
		in.append("                    TABLE_NAME\r\n");
		in.append("                    ,COLUMN_NAME\r\n");
		in.append("                    ,row_number() over(\r\n");
		in.append("                    ORDER BY\r\n");
		in.append("                        table_name\r\n");
		in.append("                        ,COLUMN_NAME\r\n");
		in.append("                    ) AS row_num\r\n");
		in.append("                FROM\r\n");
		in.append("                    USER_TAB_COLUMNS\r\n");
		in.append("                WHERE\r\n");
		in.append("                    TABLE_NAME NOT IN (\r\n");
		in.append("                        SELECT\r\n");
		in.append("                                VIEW_NAME\r\n");
		in.append("                            FROM\r\n");
		in.append("                                USER_VIEWS\r\n");
		in.append("                    )\r\n");
		in.append("                    AND DATA_TYPE = 'DATE'\r\n");
		in.append("        )\r\n");
		in.append("    WHERE\r\n");
		in.append("        row_num BETWEEN 1613 AND 2112\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TABLE_NAME\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"COLUMN_NAME\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"row_num\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"TABLE_NAME\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"COLUMN_NAME\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTFunction text=\"row_number\">\r\n");
		sb.append("       <ASTParentheses text=\"\" />\r\n");
		sb.append("      </ASTFunction>\r\n");
		sb.append("      <ASTOver text=\"over\">\r\n");
		sb.append("       <ASTParentheses text=\"row_num\">\r\n");
		sb.append("        <ASTOrderby text=\"order by\">\r\n");
		sb.append("         <ASTColumn text=\"table_name\" />\r\n");
		sb.append("         <ASTComma text=\",\" />\r\n");
		sb.append("         <ASTColumn text=\"COLUMN_NAME\" />\r\n");
		sb.append("        </ASTOrderby>\r\n");
		sb.append("        <ASTInnerAlias text=\"row_num\" />\r\n");
		sb.append("       </ASTParentheses>\r\n");
		sb.append("      </ASTOver>\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"USER_TAB_COLUMNS\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTColumn text=\"TABLE_NAME\" />\r\n");
		sb.append("      <ASTKeyword text=\"NOT\" />\r\n");
		sb.append("      <ASTKeyword text=\"IN\" />\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("        <ASTSelect text=\"select\">\r\n");
		sb.append("         <ASTColumn text=\"VIEW_NAME\" />\r\n");
		sb.append("        </ASTSelect>\r\n");
		sb.append("        <ASTFrom text=\"from\">\r\n");
		sb.append("         <ASTTable text=\"USER_VIEWS\" />\r\n");
		sb.append("        </ASTFrom>\r\n");
		sb.append("       </ASTSelectStatement>\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"DATA_TYPE\" />\r\n");
		sb.append("        <ASTValue text=\"'DATE'\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTColumn text=\"row_num\" />\r\n");
		sb.append("   <ASTKeyword text=\"BETWEEN\" />\r\n");
		sb.append("   <ASTValue text=\"1613\" />\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTValue text=\"2112\" />\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction12() {

		// String test = "abc \r\n bbb \r\n aaa";
		// System.out.println(test.replaceAll("\\r\\n|\\r|\\n", ""));

		StringBuffer in = new StringBuffer();
		in.append("            SELECT\r\n");
		in.append("                    TABLE_NAME\r\n");
		in.append("                    ,COLUMN_NAME\r\n");
		in.append("                    ,row_number() over(\r\n");
		in.append("                    PARTITION BY\r\n");
		in.append("                        table_name\r\n");
		in.append("                    ORDER BY\r\n");
		in.append("                        table_name\r\n");
		in.append("                        ,COLUMN_NAME\r\n");
		in.append("                    ) AS row_num\r\n");
		in.append("                FROM\r\n");
		in.append("                    USER_TAB_COLUMNS\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TABLE_NAME\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"COLUMN_NAME\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"row_number\">\r\n");
		sb.append("    <ASTParentheses text=\"\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTOver text=\"over\">\r\n");
		sb.append("    <ASTParentheses text=\"row_num\">\r\n");
		sb.append("     <ASTPartitionBy text=\"partition by\">\r\n");
		sb.append("      <ASTColumn text=\"table_name\" />\r\n");
		sb.append("     </ASTPartitionBy>\r\n");
		sb.append("     <ASTOrderby text=\"order by\">\r\n");
		sb.append("      <ASTColumn text=\"table_name\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"COLUMN_NAME\" />\r\n");
		sb.append("     </ASTOrderby>\r\n");
		sb.append("     <ASTInnerAlias text=\"row_num\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTOver>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"USER_TAB_COLUMNS\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testFunction13() {
		StringBuffer in = new StringBuffer();
		in.append("            SELECT\r\n");
		in.append("                    COUNT(*) AS CNT\r\n");
		in.append("                    ,COL\r\n");
		in.append("            FROM\r\n");
		in.append("                    USER_TAB_COLUMNS\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTFunction text=\"COUNT\">\r\n");
		sb.append("    <ASTParentheses text=\"CNT\">\r\n");
		sb.append("     <ASTColumn text=\"*\" />\r\n");
		sb.append("     <ASTInnerAlias text=\"CNT\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"USER_TAB_COLUMNS\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
