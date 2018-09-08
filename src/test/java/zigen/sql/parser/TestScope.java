/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import junit.framework.TestCase;
import zigen.sql.format.SqlFormatRule;
import zigen.sql.parser.exception.ParserException;

public class TestScope extends TestCase {
	private void check(String in, int expect) {
		try {
			ISqlParser parser = new SqlParser(in, new SqlFormatRule());
			INode node = new Node("root");
			parser.parse(node);

			ASTVisitorToString visitor = new ASTVisitorToString();
			node.accept(visitor, null);
			visitor.print();

			assertEquals(expect, parser.getScope());
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}

	private void check2(String in, int expect, int offset) {
		try {
			ISqlParser parser = new SqlParser(in, new SqlFormatRule());
			INode node = new Node("root");
			parser.parse(node);

			ASTVisitor2 visitor = new ASTVisitor2();
			node.accept(visitor, null);

			INode nn = visitor.findNodeByOffset(offset);

			assertEquals(expect, nn.getScope());
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		}
	}

	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT ");
		check(in.toString(), ISqlParser.SCOPE_SELECT);
		check2(in.toString(), ISqlParser.SCOPE_SELECT, 0);
	}

	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT FROM ");
		check(in.toString(), ISqlParser.SCOPE_FROM);
		check2(in.toString(), ISqlParser.SCOPE_FROM, 7);
	}

	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT FROM WHERE ");
		check(in.toString(), ISqlParser.SCOPE_WHERE);
		check2(in.toString(), ISqlParser.SCOPE_WHERE, 12);
	}

	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT FROM WHERE ORDER BY");
		check(in.toString(), ISqlParser.SCOPE_BY);
		check2(in.toString(), ISqlParser.SCOPE_BY, 18);
	}

	public void test42() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT FROM ORDER BY");
		check(in.toString(), ISqlParser.SCOPE_BY);
		check2(in.toString(), ISqlParser.SCOPE_BY, 12);
	}

	public void test43() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT FROM WHERE GROUP BY");
		check(in.toString(), ISqlParser.SCOPE_BY);
		check2(in.toString(), ISqlParser.SCOPE_BY, 18);
	}

	public void test44() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT FROM GROUP BY");
		check(in.toString(), ISqlParser.SCOPE_BY);
		check2(in.toString(), ISqlParser.SCOPE_BY, 12);
	}

	public void testScope() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        EMPNO");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    MAX( EMPNO )");
		in.append("                FROM");
		in.append("                    EMP");
		in.append("        ) MAXEMP");

		check(in.toString(), ISqlParser.SCOPE_SELECT);
		check2(in.toString(), ISqlParser.SCOPE_SELECT, 132);

	}

	public void testScope2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        EMPNO");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    MAX( EMPNO )");
		in.append("                FROM");
		in.append("                    EMP");

		check(in.toString(), ISqlParser.SCOPE_FROM);
		check2(in.toString(), ISqlParser.SCOPE_FROM, in.toString().length() - 3);

	}

	public void testScope21() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        EMPNO");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    MAX( EMPNO )");
		in.append("                FROM");
		in.append("                    EMP");
		in.append("                WHERE");

		check(in.toString(), ISqlParser.SCOPE_WHERE);
		check2(in.toString(), ISqlParser.SCOPE_WHERE, in.toString().length() - 5);

	}

	public void testScope3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        EMPNO");
		in.append("        ,(");
		in.append("            SELECT");
		in.append("                    MAX( EMPNO )");
		in.append("                FROM");
		in.append("                    EMP");
		in.append("        ) MAXEMP");
		in.append("        ,");

		check(in.toString(), ISqlParser.SCOPE_SELECT);
		check2(in.toString(), ISqlParser.SCOPE_SELECT, in.toString().length() - 1);

	}
}
