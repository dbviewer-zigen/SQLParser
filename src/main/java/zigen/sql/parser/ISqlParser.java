/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import zigen.sql.parser.exception.ParserException;

public interface ISqlParser {

	public final static int SCOPE_DEFAULT = 0;

	public final static int SCOPE_SELECT = 1;

	public final static int SCOPE_FROM = 2;

	public final static int SCOPE_WHERE = 3;

	public final static int SCOPE_ORDER = 4;

	public final static int SCOPE_BY = 4;

	public final static int SCOPE_INSERT = 10;

	public final static int SCOPE_INTO = 11;

	public final static int SCOPE_INTO_COLUMNS = 12;

	public final static int SCOPE_VALUES = 13;

	public final static int SCOPE_UPDATE = 20;

	public final static int SCOPE_SET = 21;

	public final static int SCOPE_DELETE = 30;

	public final static int SCOPE_CREATE = 50;

	public final static int SCOPE_DROP = 60;

	public final static int SCOPE_TARGET = 70; // for drop

	public abstract void parse(INode node) throws ParserException;

	public abstract int getScope();

	public abstract String dump(INode node);

	public abstract String dumpXml(INode node);

	public abstract boolean isCanceled();

}
