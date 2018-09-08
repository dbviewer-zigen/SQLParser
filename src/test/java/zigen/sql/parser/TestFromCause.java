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

public class TestFromCause extends TestCase {

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
	 * �X�L�[�}�t�e�[�u��
	 *
	 */
	public void test1() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        SCHEMA01.TBL");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"SCHEMA01.TBL\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	/**
	 * �����e�[�u��
	 *
	 */
	public void test1b() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL1, TBL2, TBL3");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL1\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");// ��ŏ���
		sb.append("   <ASTTable text=\"TBL2\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");// ��ŏ���
		sb.append("   <ASTTable text=\"TBL3\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �����e�[�u�� + �ʖ�
	 *
	 */
	public void test1c() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL1 A, TBL2, TBL3");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL1 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");

		sb.append("   <ASTComma text=\",\" />\r\n");// ��ŏ���
		sb.append("   <ASTTable text=\"TBL2\" />\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");// ��ŏ���
		sb.append("   <ASTTable text=\"TBL3\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");
		check(in.toString(), sb.toString());

	}

	/**
	 * �����e�[�u�� + �ʖ�
	 *
	 */
	public void test1d() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL1 A, TBL2 B, TBL3");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL1 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"TBL2 AS B\">\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"TBL3\" />\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �����e�[�u�� + �ʖ�
	 *
	 */
	public void test1e() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL1 A, TBL2 B, TBL3 C");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL1 AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"TBL2 AS B\">\r\n");
		sb.append("    <ASTInnerAlias text=\"B\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("   <ASTComma text=\",\" />\r\n");
		sb.append("   <ASTTable text=\"TBL3 AS C\">\r\n");
		sb.append("    <ASTInnerAlias text=\"C\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �ʖ��̃J����
	 *
	 */
	public void test2() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        TBL A");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"TBL AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}

	/**
	 * �X�L�[�}�t�A�ʖ��J����
	 *
	 */
	public void test3() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        SCHEMANAME.TBL A");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTTable text=\"SCHEMANAME.TBL AS A\">\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTTable>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());
	}

	/**
	 * �X�L�[�}�t�A�ʖ��J����
	 *
	 */
	public void test4() {
		StringBuffer in = new StringBuffer();
		in.append("SELECT");
		in.append("        COL");
		in.append("    FROM");
		in.append("        (SELECT * FROM DUAL) A");

		StringBuffer sb = new StringBuffer();
		sb.append("<Node text=\"root\">\r\n");
		sb.append(" <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("  <ASTSelect text=\"select\">\r\n");
		sb.append("   <ASTColumn text=\"COL\" />\r\n");
		sb.append("  </ASTSelect>\r\n");
		sb.append("  <ASTFrom text=\"from\">\r\n");
		sb.append("   <ASTParentheses text=\"A\">\r\n");
		sb.append("    <ASTSelectStatement text=\"SelectStatement\">\r\n");
		sb.append("     <ASTSelect text=\"select\">\r\n");
		sb.append("      <ASTColumn text=\"*\" />\r\n");
		sb.append("     </ASTSelect>\r\n");
		sb.append("     <ASTFrom text=\"from\">\r\n");
		sb.append("      <ASTTable text=\"DUAL\" />\r\n");
		sb.append("     </ASTFrom>\r\n");
		sb.append("    </ASTSelectStatement>\r\n");
		sb.append("    <ASTInnerAlias text=\"A\" />\r\n");
		sb.append("   </ASTParentheses>\r\n");
		sb.append("  </ASTFrom>\r\n");
		sb.append(" </ASTSelectStatement>\r\n");
		sb.append("</Node>\r\n");

		check(in.toString(), sb.toString());

	}
}
