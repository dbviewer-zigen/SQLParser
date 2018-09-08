/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.util;

public class Util {

	public static final String encodeMarkup(String strSrc) {
		int nLen;
		if (strSrc == null || (nLen = strSrc.length()) <= 0)
			return "";

		StringBuffer sbEnc = new StringBuffer(nLen * 2);

		for (int i = 0; i < nLen; i++) {
			char c;
			switch (c = strSrc.charAt(i)) {
			case '<':
				sbEnc.append("&lt;");
				break;
			case '>':
				sbEnc.append("&gt;");
				break;
			// case '&':
			// sbEnc.append("&amp;");
			// break;
			// case '"':
			// sbEnc.append("&quot;");
			// break;
			// case '\'':
			// sbEnc.append("&#39;");
			// break;
			default:
				sbEnc.append(c);
				break;
			}
		}

		return sbEnc.toString();
	}
}
