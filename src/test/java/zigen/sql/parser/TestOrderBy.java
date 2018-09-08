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

public class TestOrderBy extends TestCase {
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

	public void testOrderBy() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        NUMERIC_SCALE as DECIMAL_DIGITS");
		in.append("    FROM");
		in.append("        information_schema.COLUMNS");
		in.append("    ORDER BY");
		in.append("        COL1");
		in.append("        , COL2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"NUMERIC_SCALE AS DECIMAL_DIGITS\">\r\n");
		sb.append("    <ASTInnerAlias text=\"DECIMAL_DIGITS\" />\r\n");
		sb.append("   </ASTColumn>\r\n");

		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"information_schema.COLUMNS\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTOrderby text=\"order by\">\r\n");
		sb.append("   <ASTColumn text=\"COL1\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"COL2\" />\r\n");
		sb.append("  </ASTOrderby>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testOrderBy2() {
		StringBuffer in = new StringBuffer();
		in.append("ORDER BY");
		in.append("        COL1");
		in.append("        , COL2");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\" />\r\n");

		check(in.toString(), sb.toString());

	}

}
