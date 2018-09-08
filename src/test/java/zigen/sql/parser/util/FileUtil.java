/*
 * Copyright (c) 2007 - 2009 ZIGEN
 * Eclipse Public License - v 1.0
 * http://www.eclipse.org/legal/epl-v10.html
 */
package zigen.sql.parser.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class FileUtil {

	public static String BASE = "test_result";

	public static void writeXml(String fileName, String content) {
		try {
			write(new FileOutputStream(new File(BASE + File.separator + fileName + ".xml")), "UTF-8", content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String read(InputStream inputStream, String charsetName) throws IOException {
		String out = null;
		InputStreamReader br = null;
		StringWriter sw = null;
		char[] buf = new char[1024];
		int i;
		try {
			br = new InputStreamReader(inputStream, charsetName);
			sw = new StringWriter();
			while ((i = br.read(buf, 0, buf.length)) != -1) {
				sw.write(buf, 0, i);
			}
			sw.flush();
			sw.close();

			out = sw.toString();
		} catch (IOException e) {
			throw e;
		} finally {
			if (br != null)
				br.close();

		}
		return out;

	}

	public static void write(FileOutputStream fos, String charsetName, String content) throws IOException {
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(fos, charsetName);
			osw.write(content);
		} catch (IOException e) {
			throw e;
		} finally {
			if (osw != null)
				osw.close();

		}
	}

}
