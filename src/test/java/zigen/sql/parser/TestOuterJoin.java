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

public class TestOuterJoin extends TestCase {
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

	public void testSimple() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        A.PART, B.PART");
		in.append(" FROM ORDERS_LIST1 A, ORDERS_LIST2 B");
		in.append("    WHERE");
		in.append("        A.PART = B.PART( + )");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"A.PART\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"B.PART\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST1 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST2 AS B\">\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"A.PART\" />\r\n");
		sb.append("     <ASTColumn text=\"B.PART(+)\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

		// // for test
		ISqlParser parser = new SqlParser(in.toString(), new SqlFormatRule());
		INode node = new ASTRoot();
		parser.parse(node);

		// ASTVisitorConvert visitor = new ASTVisitorConvert();
		// node.accept(visitor, null);

		FileUtil.writeXml(getClass().getName(), parser.dumpXml(node));

	}

	public void testSimple2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        A.PART, B.PART FROM ORDERS_LIST1 A, ORDERS_LIST2 B");
		in.append("    WHERE");
		in.append("        A.PART(+) = B.PART");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"A.PART\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"B.PART\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST1 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST2 AS B\">\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"A.PART(+)\" />\r\n");
		sb.append("     <ASTColumn text=\"B.PART\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTWhere>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	public void testSimple3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        A.PART, B.PART FROM ORDERS_LIST1 A, ORDERS_LIST2 B");
		in.append("    WHERE");
		in.append("        A.PART(+) = (SELECT COL FROM DUAL)");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"A.PART\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"B.PART\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST1 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST2 AS B\">\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append("  <ASTWhere text=\"where\">\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"A.PART(+)\" />\r\n");
		sb.append("     <ASTParentheses text=\"\">\r\n");
		sb.append("      <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("       <ASTSelect text=\"select\">\r\n");
		sb.append("        <ASTColumn text=\"COL\" />\r\n");
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

	public void testSimple4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        A.PART, B.PART");
		in.append(" FROM ORDERS_LIST1 A LEFT OUTER JOIN ORDERS_LIST2 B");
		in.append(" ON A.PART = B.PART");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"A.PART\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTColumn text=\"B.PART\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST1 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTJoin text=\"LEFT OUTER JOIN\" />\r\n");
		sb.append("   <ASTTable text=\"ORDERS_LIST2 AS B\">\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTOn text=\"ON\" />\r\n");
		sb.append("   <ASTExpression text=\"expression\">\r\n");
		sb.append("    <ASTOperator text=\"=\">\r\n");
		sb.append("     <ASTColumn text=\"A.PART\" />\r\n");
		sb.append("     <ASTColumn text=\"B.PART\" />\r\n");
		sb.append("    </ASTOperator>\r\n");
		sb.append("   </ASTExpression>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
