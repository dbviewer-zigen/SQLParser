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

public class TestWithForDB2_1 extends TestCase {
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
		// in.append("with tmp_table as (");
		in.append("SELECT");
		in.append("        CASE");
		in.append("            WHEN (");
		// in.append("                1 = (2)");
		in.append("                1 = (");
		in.append("                    SELECT 1 FROM syscat.SCHEMATA s JOIN syscat.tables t ON s.SCHEMANAME = t.TABSCHEMA ");
		in.append("                    WHERE s.SCHEMANAME = 'SYSCAT  '");
		in.append("                    )");
		in.append("            ) THEN 'A' ");
		in.append("            ELSE 'B' ");
		in.append("        END AS dummy_value");
		in.append("    FROM");
		in.append("        syscat.SCHEMATA");
		// in.append(")");
		// in.append("select * from tmp_table");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">").append("\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">").append("\r\n");
		sb.append("  <ASTSelect text=\"select\">").append("\r\n");
		sb.append("   <ASTCaseCause text=\"dummy_value\">").append("\r\n");
		sb.append("    <ASTCase text=\"case\" />").append("\r\n");
		sb.append("    <ASTWhen text=\"when\">").append("\r\n");
		sb.append("     <ASTParentheses text=\"\">").append("\r\n");
		sb.append("      <ASTExpression text=\"expression\">").append("\r\n");
		sb.append("       <ASTOperator text=\"=\">").append("\r\n");
		sb.append("        <ASTValue text=\"1\" />").append("\r\n");
		sb.append("        <ASTParentheses text=\"\">").append("\r\n");
		sb.append("         <ASTSelectStatement text=\"SelectStatement\">").append("\r\n");
		sb.append("          <ASTSelect text=\"select\">").append("\r\n");
		sb.append("           <ASTValue text=\"1\" />").append("\r\n");
		sb.append("          </ASTSelect>").append("\r\n");
		sb.append("          <ASTFrom text=\"from\">").append("\r\n");
		sb.append("           <ASTTable text=\"syscat.SCHEMATA AS s\">").append("\r\n");
		sb.append("            <ASTInnerAlias text=\"s\" />").append("\r\n");
		sb.append("           </ASTTable>").append("\r\n");
		sb.append("           <ASTJoin text=\"JOIN\" />").append("\r\n");
		sb.append("           <ASTTable text=\"syscat.tables AS t\">").append("\r\n");
		sb.append("            <ASTInnerAlias text=\"t\" />").append("\r\n");
		sb.append("           </ASTTable>").append("\r\n");
		sb.append("           <ASTOn text=\"ON\" />").append("\r\n");
		sb.append("           <ASTExpression text=\"expression\">").append("\r\n");
		sb.append("            <ASTOperator text=\"=\">").append("\r\n");
		sb.append("             <ASTColumn text=\"s.SCHEMANAME\" />").append("\r\n");
		sb.append("             <ASTColumn text=\"t.TABSCHEMA\" />").append("\r\n");
		sb.append("            </ASTOperator>").append("\r\n");
		sb.append("           </ASTExpression>").append("\r\n");
		sb.append("          </ASTFrom>").append("\r\n");
		sb.append("          <ASTWhere text=\"where\">").append("\r\n");
		sb.append("           <ASTExpression text=\"expression\">").append("\r\n");
		sb.append("            <ASTOperator text=\"=\">").append("\r\n");
		sb.append("             <ASTColumn text=\"s.SCHEMANAME\" />").append("\r\n");
		sb.append("             <ASTValue text=\"'SYSCAT  '\" />").append("\r\n");
		sb.append("            </ASTOperator>").append("\r\n");
		sb.append("           </ASTExpression>").append("\r\n");
		sb.append("          </ASTWhere>").append("\r\n");
		sb.append("         </ASTSelectStatement>").append("\r\n");
		sb.append("        </ASTParentheses>").append("\r\n");
		sb.append("       </ASTOperator>").append("\r\n");
		sb.append("      </ASTExpression>").append("\r\n");
		sb.append("     </ASTParentheses>").append("\r\n");
		sb.append("    </ASTWhen>").append("\r\n");
		sb.append("    <ASTThen text=\"then\">").append("\r\n");
		sb.append("     <ASTValue text=\"'A'\" />").append("\r\n");
		sb.append("    </ASTThen>").append("\r\n");
		sb.append("    <ASTElse text=\"else\">").append("\r\n");
		sb.append("     <ASTValue text=\"'B'\" />").append("\r\n");
		sb.append("    </ASTElse>").append("\r\n");
		sb.append("    <ASTInnerAlias text=\"dummy_value\" />").append("\r\n");
		sb.append("   </ASTCaseCause>").append("\r\n");
		sb.append("  </ASTSelect>").append("\r\n");
		sb.append("  <ASTFrom text=\"from\">").append("\r\n");
		sb.append("   <ASTTable text=\"syscat.SCHEMATA\" />").append("\r\n");
		sb.append("  </ASTFrom>").append("\r\n");
		sb.append(" </ASTSelectStatement>").append("\r\n");
		sb.append("</Node>").append("\r\n");

		check(in.toString(), sb.toString());

	}

}
