/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import java.util.Date;

import junit.framework.TestCase;
import zigen.sql.format.SqlFormatRule;
import zigen.sql.parser.exception.ParserException;
import zigen.sql.parser.util.FileUtil;

public class TestLongSql extends TestCase {
	private void check(String in, String out) {
		try {
			long startTime = new Date().getTime();
			ISqlParser parser = new SqlParser(in, new SqlFormatRule());
			INode node = new Node("root");
			long endTime = new Date().getTime();
			System.out.println((endTime - startTime) / 1000.0 + "sec");

			long startTime2 = new Date().getTime();
			parser.parse(node);
			long endTime2 = new Date().getTime();
			System.out.println((endTime2 - startTime2) / 1000.0 + "sec");

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

	public void testGroupBy2222() {

		StringBuffer in = new StringBuffer();
		in.append("            SELECT");
		in.append("                    SYOHIN");
		in.append("                    ,TENCD");
		in.append("                    ,MAX( YOTEI ) MYOTEI");
		in.append("                FROM\n");
		in.append("                     (SELECT * \nFROM MAAT0710 S \nUNION SELECT * FROM MAAT0712)");
		in.append("                GROUP BY");
		in.append("                    SYOHIN123");
		in.append("                    ,TENCD456");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"SYOHIN\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTFunction text=\"MAX AS MYOTEI\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTColumn text=\"YOTEI\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("    <ASTInnerAlias text=\"MYOTEI\" />\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"*\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAT0710 AS S\">\r\n");
		sb.append("       <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"*\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAT0712\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTGroupby text=\"group by\">\r\n");
		sb.append("   <ASTColumn text=\"SYOHIN123\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"TENCD456\" />\r\n");
		sb.append("  </ASTGroupby>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testSqlA() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        LIST1.TENCD");
		in.append("    FROM");
		in.append("        (");
		in.append("            SELECT");
		in.append("                    MS.TENCD");
		in.append("                FROM");
		in.append("                    MAAT0010 S");
		in.append("                WHERE");
		in.append("                    S.SYOHIN = MS.SYOHIN");
		in.append("            UNION");
		in.append("            SELECT");
		in.append("                    MS.TENCD");
		in.append("                FROM");
		in.append("                    MAAT0710 S");
		in.append("                WHERE");
		in.append("                    S.SYOHIN = MS.SYOHIN");
		in.append("        ) LIST1");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    HOGESYOHIN");
		in.append("                    ,TENCD");
		in.append("                    ,MAX( YOTEI ) MYOTEI");
		in.append("                FROM");
		in.append("                    (");
		in.append("                        SELECT");
		in.append("                                S.SYOHIN");
		in.append("                                ,0 YOTEI");
		in.append("                            FROM");
		in.append("                                MAAT0010 S");
		in.append("                        UNION");
		in.append("                        SELECT");
		in.append("                                S.SYOHIN");
		in.append("                            FROM");
		in.append("                                MAAT0710 S");
		in.append("                    )");
		in.append("                GROUP BY");
		in.append("                    SYOHIN123");
		in.append("                    ,TENCD456");
		in.append("        ) LIST2");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    SYOHIN");
		in.append("                    ,TENCD");
		in.append("                FROM");
		in.append("                    MAAW0090");
		in.append("                GROUP BY");
		in.append("                    SYOHIN");
		in.append("                    ,TENCD");
		in.append("        ) LIST3");
		in.append("    WHERE");
		in.append("        LIST1.SYOHIN = LIST2.SYOHIN");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"LIST1.TENCD\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"LIST1\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAT0010 AS S\">\r\n");
		sb.append("       <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("        <ASTColumn text=\"MS.SYOHIN\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAT0710 AS S\">\r\n");
		sb.append("       <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("        <ASTColumn text=\"MS.SYOHIN\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"LIST1\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"LIST2\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"HOGESYOHIN\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTFunction text=\"MAX AS MYOTEI\">\r\n");
		sb.append("       <ASTParentheses text=\"\">\r\n");
		sb.append("        <ASTColumn text=\"YOTEI\" />\r\n");
		sb.append("       </ASTParentheses>\r\n");
		sb.append("       <ASTInnerAlias text=\"MYOTEI\" />\r\n");
		sb.append("      </ASTFunction>\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("        <ASTSelect text=\"select\">\r\n");
		sb.append("         <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("         <ASTComma text=\",\" />\r\n");
		sb.append("         <ASTValue text=\"0 AS YOTEI\">\r\n");
		sb.append("          <ASTInnerAlias text=\"YOTEI\" />\r\n");
		sb.append("         </ASTValue>\r\n");
		sb.append("        </ASTSelect>\r\n");
		sb.append("        <ASTFrom text=\"from\">\r\n");
		sb.append("         <ASTTable text=\"MAAT0010 AS S\">\r\n");
		sb.append("          <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("         </ASTTable>\r\n");
		sb.append("        </ASTFrom>\r\n");
		sb.append("       </ASTSelectStatement>\r\n");
		sb.append("       <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append("       <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("        <ASTSelect text=\"select\">\r\n");
		sb.append("         <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("        </ASTSelect>\r\n");
		sb.append("        <ASTFrom text=\"from\">\r\n");
		sb.append("         <ASTTable text=\"MAAT0710 AS S\">\r\n");
		sb.append("          <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("         </ASTTable>\r\n");
		sb.append("        </ASTFrom>\r\n");
		sb.append("       </ASTSelectStatement>\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTGroupby text=\"group by\">\r\n");
		sb.append("      <ASTColumn text=\"SYOHIN123\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"TENCD456\" />\r\n");
		sb.append("     </ASTGroupby>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"LIST2\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"LIST3\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"SYOHIN\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAW0090\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTGroupby text=\"group by\">\r\n");
		sb.append("      <ASTColumn text=\"SYOHIN\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("     </ASTGroupby>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"LIST3\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"LIST1.SYOHIN\" />\r\n");
		sb.append("     <ASTColumn text=\"LIST2.SYOHIN\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testSql() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("                    SYOHIN");
		in.append("                    ,'������' || '����' TESTCOL");
		in.append("                    ,TENCD");
		in.append("                    ,YOTEI  MYOTEI");
		in.append("                FROM");
		in.append("                    (");
		in.append("                        SELECT");
		in.append("                                S.SYOHIN");
		in.append("                                ,MS.TENCD");
		in.append("                                ,TIN.TANABN");
		in.append("                                ,S.BUNRUI");
		in.append("                                ,S.BUNRUI2");
		in.append("                                ,S.BUNRUI3");
		in.append("                                ,S.BUNRUI4");
		in.append("                                ,S.BUNRUI5");
		in.append("                                ,S.BUNRUI6");
		in.append("                                ,0 YOTEI");
		in.append("                            FROM");
		in.append("                                MAAT0010 S");
		in.append("                                ,MAAT0020 MS");
		in.append("                                ,MAAT0210 TIN");
		in.append("                            WHERE");
		in.append("                                S.SYOHIN = MS.SYOHIN");
		in.append("                                AND S.MENTE <> 5");
		in.append("                                AND MS.SYOHIN = TIN. SYOHIN ( + )");
		in.append("                                AND MS.TENCD = TIN.TENCD( + )");
		in.append("                                AND MS.TENCD = 1");
		in.append("                        UNION All");
		in.append("                        SELECT");
		in.append("                                S.SYOHIN");
		in.append("                                ,MS.TENCD");
		in.append("                                ,TIN.TANABN");
		in.append("                                ,S.BUNRUI");
		in.append("                                ,S.BUNRUI2");
		in.append("                                ,S.BUNRUI3");
		in.append("                                ,S.BUNRUI4");
		in.append("                                ,S.BUNRUI5");
		in.append("                                ,S.BUNRUI6");
		in.append("                                ,S.YOTEI");
		in.append("                            FROM");
		in.append("                                MAAT0710 S");
		in.append("                                ,MAAT0720 MS");
		in.append("                                ,MAAT0210 TIN");
		in.append("                            WHERE");
		in.append("                                S.SYOHIN = MS.SYOHIN");
		in.append("                                AND S.YOTEI = MS.YOTEI");
		in.append("                                AND MS.SYOHIN = TIN.SYOHIN ( + )");
		in.append("                                AND MS.TENCD = TIN.TENCD( + )");
		in.append("                                AND MS.TENCD = 1");
		in.append("                    )");
		in.append("                WHERE");
		in.append("                    TENCD_LAST = 1");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"SYOHIN\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTValue text=\"'������'||'����' AS TESTCOL\">\r\n");
		sb.append("    <ASTInnerAlias text=\"TESTCOL\" />\r\n");
		sb.append("   </ASTValue>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"YOTEI AS MYOTEI\">\r\n");
		sb.append("    <ASTInnerAlias text=\"MYOTEI\" />\r\n");
		sb.append("   </ASTColumn>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"TIN.TANABN\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI2\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI3\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI4\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI5\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI6\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTValue text=\"0 AS YOTEI\">\r\n");
		sb.append("       <ASTInnerAlias text=\"YOTEI\" />\r\n");
		sb.append("      </ASTValue>\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAT0010 AS S\">\r\n");
		sb.append("       <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTTable text=\"MAAT0020 AS MS\">\r\n");
		sb.append("       <ASTInnerAlias text=\"MS\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTTable text=\"MAAT0210 AS TIN\">\r\n");
		sb.append("       <ASTInnerAlias text=\"TIN\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("        <ASTColumn text=\"MS.SYOHIN\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"<>\">\r\n");
		sb.append("        <ASTColumn text=\"S.MENTE\" />\r\n");
		sb.append("        <ASTValue text=\"5\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"MS.SYOHIN\" />\r\n");
		sb.append("        <ASTColumn text=\"TIN.SYOHIN(+)\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("        <ASTColumn text=\"TIN.TENCD(+)\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTUnion text=\"ASTUnion All\" />\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"TIN.TANABN\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI2\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI3\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI4\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI5\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.BUNRUI6\" />\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTColumn text=\"S.YOTEI\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MAAT0710 AS S\">\r\n");
		sb.append("       <ASTInnerAlias text=\"S\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTTable text=\"MAAT0720 AS MS\">\r\n");
		sb.append("       <ASTInnerAlias text=\"MS\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("      <ASTComma text=\",\" />\r\n");
		sb.append("      <ASTTable text=\"MAAT0210 AS TIN\">\r\n");
		sb.append("       <ASTInnerAlias text=\"TIN\" />\r\n");
		sb.append("      </ASTTable>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"S.SYOHIN\" />\r\n");
		sb.append("        <ASTColumn text=\"MS.SYOHIN\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"S.YOTEI\" />\r\n");
		sb.append("        <ASTColumn text=\"MS.YOTEI\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"MS.SYOHIN\" />\r\n");
		sb.append("        <ASTColumn text=\"TIN.SYOHIN(+)\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("        <ASTColumn text=\"TIN.TENCD(+)\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"MS.TENCD\" />\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
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

	public void testLoingA() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        SYOHIN");
		in.append("    FROM");
		in.append("        EMP");
		in.append("    WHERE");
		in.append("        (");
		// in.append(" (");
		// in.append(" TO_NUMBER(TO_CHAR(SYSDATE, 'YYYYMMDD')) <= YOTEI");
		// in.append(" AND YOTEI <= 20070314");
		// in.append(" )");
		// in.append(" OR YOTEI = 10");
		in.append("            YOTEI = 10");
		in.append("        )");
		in.append("        AND TENCD_LAST = 1");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"SYOHIN\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"EMP\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"=\">\r\n");
		sb.append("      <ASTColumn text=\"YOTEI\" />\r\n");
		sb.append("      <ASTValue text=\"10\" />\r\n");
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

	public void testLoingC() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT\r\n");
		in.append("        TENCD\r\n");
		in.append("    FROM\r\n");
		in.append("        (\r\n");
		in.append("            SELECT\r\n");
		in.append("                    MISE.TENCD\r\n");
		in.append("                FROM\r\n");
		in.append("                    (\r\n");
		in.append("                        SELECT\r\n");
		in.append("                                TENCD\r\n");
		in.append("                            FROM\r\n");
		in.append("                                MAMV0120\r\n");
		in.append("                    ) JISSEKI --����\r\n");
		in.append("        )\r\n");
		in.append("UNION ALL\r\n");
		in.append("SELECT\r\n");
		in.append("        999999 TENCD\r\n");
		in.append("    FROM\r\n");
		in.append("        (\r\n");
		in.append("            SELECT\r\n");
		in.append("                    JISSEKI.BUNRUI\r\n");
		in.append("                FROM\r\n");
		in.append("                    (\r\n");
		in.append("                        SELECT\r\n");
		in.append("                                BUNRUI\r\n");
		in.append("                            FROM\r\n");
		in.append("                                MAMV0120\r\n");
		in.append("                    ) JISSEKI --����\r\n");
		in.append("        )\r\n");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"MISE.TENCD\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTParentheses text=\"JISSEKI\">\r\n");
		sb.append("       <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("        <ASTSelect text=\"select\">\r\n");
		sb.append("         <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("        </ASTSelect>\r\n");
		sb.append("        <ASTFrom text=\"from\">\r\n");
		sb.append("         <ASTTable text=\"MAMV0120\" />\r\n");
		sb.append("        </ASTFrom>\r\n");
		sb.append("       </ASTSelectStatement>\r\n");
		sb.append("       <ASTInnerAlias text=\"JISSEKI\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion All\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTValue text=\"999999 AS TENCD\">\r\n");
		sb.append("    <ASTInnerAlias text=\"TENCD\" />\r\n");
		sb.append("   </ASTValue>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"JISSEKI.BUNRUI\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTParentheses text=\"JISSEKI\">\r\n");
		sb.append("       <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("        <ASTSelect text=\"select\">\r\n");
		sb.append("         <ASTColumn text=\"BUNRUI\" />\r\n");
		sb.append("        </ASTSelect>\r\n");
		sb.append("        <ASTFrom text=\"from\">\r\n");
		sb.append("         <ASTTable text=\"MAMV0120\" />\r\n");
		sb.append("        </ASTFrom>\r\n");
		sb.append("       </ASTSelectStatement>\r\n");
		sb.append("       <ASTInnerAlias text=\"JISSEKI\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

}