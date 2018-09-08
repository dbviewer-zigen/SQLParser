/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser;

import java.util.HashMap;
import java.util.Map;

public class SqlKeyword {
	static final Map keywordMap = new HashMap();

	static final Map functionMap = new HashMap();

	public static final String[] Keywords = {
			"add",
			"all",
			"alter",
			"and",
			"as",
			"asc",
			"between",
//			"body",
			"by",
			"cascade",
			"column",
			"comment",
			"commit",
			"constraint",
			"create",
			"date",
			"default",
			"delete",
			"desc",
			"distinct",
			"drop",
			"escape",
			"exists",
			"foreign",
			"from",
			"function",  // PLSQL
			"group",
			"having",
			"in",
			"index",
			"inner",
			"insert",
			"into",
			"is",
			"join",
			"key",
			"left",
			"like",
			"match",
            "minus",    // for Oracle
			"not",
			"null",
			"on",
			"option",
			"or",
			"order",
			"outer",
			"package",   // PLSQL
			"primary",
			"procedure", // PLSQL
			"right",
			"rollback",
			"rows",
			"schema",
			"select",
			"set",
			"show",
			//			"sysdate",
			"table",
			"temporary",
			"time",
			"timestamp",
			"trigger",   //PLSQL
			"truncate",
			"type",
			"union",
			"unique",
			"update",
			"values",
			"view",
			"where",
			"with"
	};

	public static final String[] Functions = {
			"count", "max", "min"
	};

	public static final String[] PLSQLTypes = {
		"function", "procedure", "trigger", "package"
	};

	static {
		for (int i = 0; i < Keywords.length; i++) {
			keywordMap.put(Keywords[i], Keywords[i]);
		}
		for (int i = 0; i < Functions.length; i++) {
			functionMap.put(Functions[i], Functions[i]);
		}
	}

	public static boolean isKeyword(String target) {
		target = target.toLowerCase();
		return (keywordMap.containsKey(target));
	}

	public static boolean isFunction(String target) {
		target = target.toLowerCase();
		return (functionMap.containsKey(target));
	}

}
