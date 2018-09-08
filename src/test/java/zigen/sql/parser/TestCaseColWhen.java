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

public class TestCaseColWhen extends TestCase {

	private void check(String in, String out) {
		try {

			SqlFormatRule rule = new SqlFormatRule();
			rule.addFunctions(new String[] {
				"LOCATE"
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

	public void test0() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        CASE COL");
		in.append("            WHEN null THEN '�k��'");
		in.append("            WHEN 1 THEN '�C�`'");
		in.append("            ELSE '�ȊO'");
		in.append("        END HOGE");
		in.append("    FROM");
		in.append("        TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTCaseCause text=\"HOGE\">\r\n");
		sb.append("    <ASTCase text=\"case\">\r\n");
		sb.append("     <ASTColumn text=\"COL\" />\r\n");
		sb.append("    </ASTCase>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"null\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�k��'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�C�`'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTValue text=\"'�ȊO'\" />\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("    <ASTInnerAlias text=\"HOGE\" />\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        CASE COL");
		in.append("            WHEN null THEN '�k��'");
		in.append("            WHEN 1 THEN '�C�`'");
		in.append("            ELSE '�ȊO'");
		in.append("        END");
		in.append("    FROM");
		in.append("        TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTCaseCause text=\"\">\r\n");
		sb.append("    <ASTCase text=\"case\">\r\n");
		sb.append("     <ASTColumn text=\"COL\" />\r\n");
		sb.append("    </ASTCase>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"null\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�k��'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�C�`'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTValue text=\"'�ȊO'\" />\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        CASE COL");
		in.append("            WHEN COL2 THEN '�k��'");
		in.append("            WHEN 1 THEN COL3");
		in.append("            ELSE '�ȊO'");
		in.append("        END");
		in.append("    FROM");
		in.append("        TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTCaseCause text=\"\">\r\n");
		sb.append("    <ASTCase text=\"case\">\r\n");
		sb.append("     <ASTColumn text=\"COL\" />\r\n");
		sb.append("    </ASTCase>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTColumn text=\"COL2\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�k��'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTColumn text=\"COL3\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTValue text=\"'�ȊO'\" />\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        CASE COL");
		in.append("            WHEN (");
		in.append("                SELECT");
		in.append("                        COL4");
		in.append("                    FROM");
		in.append("                        TBL2");
		in.append("            ) THEN '�k��'");
		in.append("            ELSE '�ȊO'");
		in.append("        END");
		in.append("    FROM");
		in.append("        TBL");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTCaseCause text=\"\">\r\n");
		sb.append("    <ASTCase text=\"case\">\r\n");
		sb.append("     <ASTColumn text=\"COL\" />\r\n");
		sb.append("    </ASTCase>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTColumn text=\"COL4\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"TBL2\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�k��'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTValue text=\"'�ȊO'\" />\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        CASE COL");
		in.append("            WHEN (");
		in.append("                SELECT");
		in.append("                        COL4");
		in.append("                    FROM");
		in.append("                        TBL2");
		in.append("            ) THEN '�k��'");
		in.append("            WHEN 1 THEN (");
		in.append("                SELECT");
		in.append("                        COL5");
		in.append("                    FROM");
		in.append("                        TBL3");
		in.append("            )");
		in.append("            ELSE '�ȊO'");
		in.append("        END");
		in.append("    FROM");
		in.append("        TBL");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTCaseCause text=\"\">\r\n");
		sb.append("    <ASTCase text=\"case\">\r\n");
		sb.append("     <ASTColumn text=\"COL\" />\r\n");
		sb.append("    </ASTCase>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTColumn text=\"COL4\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"TBL2\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�k��'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTColumn text=\"COL5\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"TBL3\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTValue text=\"'�ȊO'\" />\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test5() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        CASE COL");
		in.append("            WHEN (");
		in.append("                SELECT");
		in.append("                        COL4");
		in.append("                    FROM");
		in.append("                        TBL2");
		in.append("            ) THEN '�k��'");
		in.append("            WHEN 1 THEN (");
		in.append("                SELECT");
		in.append("                        COL5");
		in.append("                    FROM");
		in.append("                        TBL3");
		in.append("            )");
		in.append("            ELSE (");
		in.append("                SELECT");
		in.append("                        COL6");
		in.append("                    FROM");
		in.append("                        TBL4");
		in.append("            )");
		in.append("        END");
		in.append("    FROM");
		in.append("        TBL");
		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTCaseCause text=\"\">\r\n");
		sb.append("    <ASTCase text=\"case\">\r\n");
		sb.append("     <ASTColumn text=\"COL\" />\r\n");
		sb.append("    </ASTCase>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTColumn text=\"COL4\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"TBL2\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"'�k��'\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTColumn text=\"COL5\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"TBL3\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTColumn text=\"COL6\" />\r\n");
		sb.append("       </ASTSelect>\r\n");
		sb.append("       <ASTFrom text=\"from\">\r\n");
		sb.append("        <ASTTable text=\"TBL4\" />\r\n");
		sb.append("       </ASTFrom>\r\n");
		sb.append("      </ASTSelectStatement>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testFunction() {
		StringBuffer in = new StringBuffer();
		in.append("LOCATE('unsigned', COLUMN_TYPE)");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTFunction text=\"LOCATE\">\r\n");
		sb.append("  <ASTParentheses text=\"\">\r\n");
		sb.append("   <ASTValue text=\"'unsigned'\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"COLUMN_TYPE\" />\r\n");
		sb.append("  </ASTParentheses>\r\n");
		sb.append(" </ASTFunction>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testFunction2() {
		StringBuffer in = new StringBuffer();
		in.append("TO_NUMBER(TO_CHAR(SYSDATE, 'YYYYMMDD'))");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTFunction text=\"TO_NUMBER\">\r\n");
		sb.append("  <ASTParentheses text=\"\">\r\n");
		sb.append("   <ASTFunction text=\"TO_CHAR\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("     <ASTComma text=\",\" />\r\n");
		sb.append("     <ASTValue text=\"'YYYYMMDD'\" />\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("  </ASTParentheses>\r\n");
		sb.append(" </ASTFunction>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testFunction3() {
		StringBuffer in = new StringBuffer();
		in.append("TO_NUMBER(TO_CHAR(SYSDATE, 'YYYYMMDD')) <= YOTEI");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTExpression text=\"expression\">\r\n");
		sb.append("  <ASTOperator text=\"<=\">\r\n");
		sb.append("   <ASTFunction text=\"TO_NUMBER\">\r\n");
		sb.append("    <ASTParentheses text=\"\">\r\n");
		sb.append("     <ASTFunction text=\"TO_CHAR\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTValue text=\"SYSDATE\" />\r\n");
		sb.append("       <ASTComma text=\",\" />\r\n");
		sb.append("       <ASTValue text=\"'YYYYMMDD'\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("    </ASTParentheses>\r\n");
		sb.append("   </ASTFunction>\r\n");
		sb.append("   <ASTColumn text=\"YOTEI\" />\r\n");
		sb.append("  </ASTOperator>\r\n");
		sb.append(" </ASTExpression>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test6() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        CASE");
		in.append("            WHEN LOCATE('unsigned', COLUMN_TYPE) != 0");
		in.append("            AND LOCATE('unsigned', DATA_TYPE) = 0 THEN CONCAT(DATA_TYPE, ' unsigned')");
		in.append("            ELSE DATA_TYPE");
		in.append("        END AS TYPE_NAME");
		in.append("        ,NUMERIC_SCALE AS DECIMAL_DIGITS");
		in.append("    FROM");
		in.append("        TABLEA");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTCaseCause text=\"TYPE_NAME\">\r\n");
		sb.append("    <ASTCase text=\"case\" />\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"!=\">\r\n");
		sb.append("       <ASTFunction text=\"LOCATE\">\r\n");
		sb.append("        <ASTParentheses text=\"\">\r\n");
		sb.append("         <ASTValue text=\"'unsigned'\" />\r\n");
		sb.append("         <ASTComma text=\",\" />\r\n");
		sb.append("         <ASTColumn text=\"COLUMN_TYPE\" />\r\n");
		sb.append("        </ASTParentheses>\r\n");
		sb.append("       </ASTFunction>\r\n");
		sb.append("       <ASTValue text=\"0\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("     <ASTKeyword text=\"AND\" />\r\n");
		sb.append("     <ASTExpression text=\"expression\">\r\n");
		sb.append("      <ASTOperator text=\"=\">\r\n");
		sb.append("       <ASTFunction text=\"LOCATE\">\r\n");
		sb.append("        <ASTParentheses text=\"\">\r\n");
		sb.append("         <ASTValue text=\"'unsigned'\" />\r\n");
		sb.append("         <ASTComma text=\",\" />\r\n");
		sb.append("         <ASTColumn text=\"DATA_TYPE\" />\r\n");
		sb.append("        </ASTParentheses>\r\n");
		sb.append("       </ASTFunction>\r\n");
		sb.append("       <ASTValue text=\"0\" />\r\n");
		sb.append("      </ASTOperator>\r\n");
		sb.append("     </ASTExpression>\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTFunction text=\"CONCAT\">\r\n");
		sb.append("      <ASTParentheses text=\"\">\r\n");
		sb.append("       <ASTColumn text=\"DATA_TYPE\" />\r\n");
		sb.append("       <ASTComma text=\",\" />\r\n");
		sb.append("       <ASTValue text=\"' unsigned'\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTFunction>\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTColumn text=\"DATA_TYPE\" />\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("    <ASTInnerAlias text=\"TYPE_NAME\" />\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"NUMERIC_SCALE AS DECIMAL_DIGITS\">\r\n");
		sb.append("    <ASTInnerAlias text=\"DECIMAL_DIGITS\" />\r\n");
		sb.append("   </ASTColumn>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TABLEA\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void test7() {

		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        JISSEKI.*");
		in.append("        ,(JISSEKI.M1ARARIGAKU - YOSAN.M1ARAGAKU) M1ARARISAI");
		in.append("        ,(");
		in.append("            JISSEKI.M1ARARIGAKU / NVL(364, 1)");
		in.append("        ) M1AVARARI");
		in.append("        ,CASE JISSEKI");
		in.append("            WHEN 0 THEN 0");
		in.append("            ELSE (JISSEKI.M1ARARIGAKU / JISSEKI.M1BUMTUBO)");
		in.append("        END M1TBARARIGAKU");
		in.append("    FROM");
		in.append("        A");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"JISSEKI.*\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"M1ARARISAI\">\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"-\">\r\n");
		sb.append("      <ASTColumn text=\"JISSEKI.M1ARARIGAKU\" />\r\n");
		sb.append("      <ASTColumn text=\"YOSAN.M1ARAGAKU\" />\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("    <ASTInnerAlias text=\"M1ARARISAI\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"M1AVARARI\">\r\n");
		sb.append("    <ASTExpression text=\"expression\">\r\n");
		sb.append("     <ASTOperator text=\"/\">\r\n");
		sb.append("      <ASTColumn text=\"JISSEKI.M1ARARIGAKU\" />\r\n");
		sb.append("      <ASTFunction text=\"NVL\">\r\n");
		sb.append("       <ASTParentheses text=\"\">\r\n");
		sb.append("        <ASTValue text=\"364\" />\r\n");
		sb.append("        <ASTComma text=\",\" />\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTParentheses>\r\n");
		sb.append("      </ASTFunction>\r\n");
		sb.append("     </ASTOperator>\r\n");
		sb.append("    </ASTExpression>\r\n");
		sb.append("    <ASTInnerAlias text=\"M1AVARARI\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTCaseCause text=\"M1TBARARIGAKU\">\r\n");
		sb.append("    <ASTCase text=\"case\">\r\n");
		sb.append("     <ASTColumn text=\"JISSEKI\" />\r\n");
		sb.append("    </ASTCase>\r\n");
		sb.append("    <ASTWhen text=\"when\">\r\n");
		sb.append("     <ASTValue text=\"0\" />\r\n");
		sb.append("    </ASTWhen>\r\n");
		sb.append("    <ASTThen text=\"then\">\r\n");
		sb.append("     <ASTValue text=\"0\" />\r\n");
		sb.append("    </ASTThen>\r\n");
		sb.append("    <ASTElse text=\"else\">\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"/\">\r\n");
		sb.append("        <ASTColumn text=\"JISSEKI.M1ARARIGAKU\" />\r\n");
		sb.append("        <ASTColumn text=\"JISSEKI.M1BUMTUBO\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTParentheses>\r\n");
		sb.append("    </ASTElse>\r\n");
		sb.append("    <ASTInnerAlias text=\"M1TBARARIGAKU\" />\r\n");
		sb.append("   </ASTCaseCause>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"A\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testFunction81() {

		StringBuffer in = new StringBuffer();
		in.append("            SELECT");
		in.append("                    (");
		in.append("                        CASE");
		in.append("                            WHEN TSUKI < 4 THEN NEN - 1");
		in.append("                            ELSE NEN");
		in.append("                        END");
		in.append("                    ) NEN");
		in.append("                FROM");
		in.append("                    MADT9010");
		in.append("                WHERE");
		in.append("                    GRPCD = 1");
		in.append("                    AND TENCD <> 0");
		in.append("                    AND SBUMON = 0");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTParentheses text=\"NEN\">\r\n");
		sb.append("    <ASTCaseCause text=\"\">\r\n");
		sb.append("     <ASTCase text=\"case\" />\r\n");
		sb.append("     <ASTWhen text=\"when\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"<\">\r\n");
		sb.append("        <ASTColumn text=\"TSUKI\" />\r\n");
		sb.append("        <ASTValue text=\"4\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhen>\r\n");
		sb.append("     <ASTThen text=\"then\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"-\">\r\n");
		sb.append("        <ASTColumn text=\"NEN\" />\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTThen>\r\n");
		sb.append("     <ASTElse text=\"else\">\r\n");
		sb.append("      <ASTColumn text=\"NEN\" />\r\n");
		sb.append("     </ASTElse>\r\n");
		sb.append("    </ASTCaseCause>\r\n");
		sb.append("    <ASTInnerAlias text=\"NEN\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MADT9010\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"GRPCD\" />\r\n");
		sb.append("     <ASTValue text=\"1\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"<>\">\r\n");
		sb.append("     <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("     <ASTValue text=\"0\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("   <ASTKeyword text=\"AND\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"SBUMON\" />\r\n");
		sb.append("     <ASTValue text=\"0\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testFunction8() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        MAMT0130.TENCD");
		in.append("    FROM");
		in.append("        (");
		in.append("            SELECT");
		in.append("                    (");
		in.append("                        CASE");
		in.append("                            WHEN TSUKI < 4 THEN NEN - 1");
		in.append("                            ELSE NEN");
		in.append("                        END");
		in.append("                    ) NEN");
		in.append("                FROM");
		in.append("                    MADT9010");
		in.append("                WHERE");
		in.append("                    GRPCD = 1");
		in.append("                    AND TENCD <> 0");
		in.append("                    AND SBUMON = 0");
		in.append("        ) MADT9010");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    (");
		in.append("                        CASE");
		in.append("                            WHEN MONTH < 4 THEN KAIYY - 1");
		in.append("                            ELSE KAIYY");
		in.append("                        END");
		in.append("                    ) KAIYY");
		in.append("                FROM");
		in.append("                    MADT0041");
		in.append("                WHERE");
		in.append("                    TENCD <> 0");
		in.append("        ) MADT0041");
		in.append("    WHERE");
		in.append("        MAMT0130.BUMON = MAAV0050.BUMON");
		in.append("    GROUP BY");
		in.append("        MAMT0130.TENCD");
		in.append("        ,MAMT0130.YEAR");
		in.append("        ,MAMT0130.MONTH");
		in.append("        ,MAAV0050.BUNRUI1");
		// in.append("UNION");
		// in.append("SELECT");
		// in.append("        TENCD");
		// in.append("    FROM");
		// in.append("        MAMT0120");
		// in.append("    WHERE");
		// in.append("        BUNRUI5 > 0");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"MAMT0130.TENCD\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"MADT9010\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTParentheses text=\"NEN\">\r\n");
		sb.append("       <ASTCaseCause text=\"\">\r\n");
		sb.append("        <ASTCase text=\"case\" />\r\n");
		sb.append("        <ASTWhen text=\"when\">\r\n");
		sb.append("         <ASTExpression text=\"expression\">\r\n");
		sb.append("          <ASTOperator text=\"<\">\r\n");
		sb.append("           <ASTColumn text=\"TSUKI\" />\r\n");
		sb.append("           <ASTValue text=\"4\" />\r\n");
		sb.append("          </ASTOperator>\r\n");
		sb.append("         </ASTExpression>\r\n");
		sb.append("        </ASTWhen>\r\n");
		sb.append("        <ASTThen text=\"then\">\r\n");
		sb.append("         <ASTExpression text=\"expression\">\r\n");
		sb.append("          <ASTOperator text=\"-\">\r\n");
		sb.append("           <ASTColumn text=\"NEN\" />\r\n");
		sb.append("           <ASTValue text=\"1\" />\r\n");
		sb.append("          </ASTOperator>\r\n");
		sb.append("         </ASTExpression>\r\n");
		sb.append("        </ASTThen>\r\n");
		sb.append("        <ASTElse text=\"else\">\r\n");
		sb.append("         <ASTColumn text=\"NEN\" />\r\n");
		sb.append("        </ASTElse>\r\n");
		sb.append("       </ASTCaseCause>\r\n");
		sb.append("       <ASTInnerAlias text=\"NEN\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MADT9010\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"GRPCD\" />\r\n");
		sb.append("        <ASTValue text=\"1\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"<>\">\r\n");
		sb.append("        <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("        <ASTValue text=\"0\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("      <ASTKeyword text=\"AND\" />\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"=\">\r\n");
		sb.append("        <ASTColumn text=\"SBUMON\" />\r\n");
		sb.append("        <ASTValue text=\"0\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"MADT9010\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTParentheses text=\"MADT0041\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTParentheses text=\"KAIYY\">\r\n");
		sb.append("       <ASTCaseCause text=\"\">\r\n");
		sb.append("        <ASTCase text=\"case\" />\r\n");
		sb.append("        <ASTWhen text=\"when\">\r\n");
		sb.append("         <ASTExpression text=\"expression\">\r\n");
		sb.append("          <ASTOperator text=\"<\">\r\n");
		sb.append("           <ASTColumn text=\"MONTH\" />\r\n");
		sb.append("           <ASTValue text=\"4\" />\r\n");
		sb.append("          </ASTOperator>\r\n");
		sb.append("         </ASTExpression>\r\n");
		sb.append("        </ASTWhen>\r\n");
		sb.append("        <ASTThen text=\"then\">\r\n");
		sb.append("         <ASTExpression text=\"expression\">\r\n");
		sb.append("          <ASTOperator text=\"-\">\r\n");
		sb.append("           <ASTColumn text=\"KAIYY\" />\r\n");
		sb.append("           <ASTValue text=\"1\" />\r\n");
		sb.append("          </ASTOperator>\r\n");
		sb.append("         </ASTExpression>\r\n");
		sb.append("        </ASTThen>\r\n");
		sb.append("        <ASTElse text=\"else\">\r\n");
		sb.append("         <ASTColumn text=\"KAIYY\" />\r\n");
		sb.append("        </ASTElse>\r\n");
		sb.append("       </ASTCaseCause>\r\n");
		sb.append("       <ASTInnerAlias text=\"KAIYY\" />\r\n");
		sb.append("      </ASTParentheses>\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"MADT0041\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("     <ASTWhere text=\"where\">\r\n");
		sb.append("      <ASTExpression text=\"expression\">\r\n");
		sb.append("       <ASTOperator text=\"<>\">\r\n");
		sb.append("        <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("        <ASTValue text=\"0\" />\r\n");
		sb.append("       </ASTOperator>\r\n");
		sb.append("      </ASTExpression>\r\n");
		sb.append("     </ASTWhere>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"MADT0041\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"MAMT0130.BUMON\" />\r\n");
		sb.append("     <ASTColumn text=\"MAAV0050.BUMON\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append("  <ASTGroupby text=\"group by\">\r\n");
		sb.append("   <ASTColumn text=\"MAMT0130.TENCD\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"MAMT0130.YEAR\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"MAMT0130.MONTH\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"MAAV0050.BUNRUI1\" />\r\n");
		sb.append("  </ASTGroupby>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	public void testFunction9() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        TENCD");
		in.append("    FROM");
		in.append("        MAMT0120");
		in.append("    WHERE");
		in.append("        BUNRUI5 > 0");
		in.append("UNION ");
		in.append("SELECT");
		in.append("        TENCD");
		in.append("    FROM");
		in.append("        MAMT0120");
		in.append("    WHERE");
		in.append("        BUNRUI5 > 0");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAMT0120\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\">\">\r\n");
		sb.append("     <ASTColumn text=\"BUNRUI5\" />\r\n");
		sb.append("     <ASTValue text=\"0\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append(" <ASTUnion text=\"ASTUnion\" />\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"TENCD\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"MAMT0120\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\">\">\r\n");
		sb.append("     <ASTColumn text=\"BUNRUI5\" />\r\n");
		sb.append("     <ASTValue text=\"0\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}
}