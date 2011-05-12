package com.ecs.fusiontables.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import com.google.api.client.http.HttpParser;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;

/**
 * HTTP parser for Google response to an Authorization request.
 * 
 * @since 1.0
 * @author Yaniv Inbar
 */
public final class CsvParser implements HttpParser {

	/** Singleton instance. */
	public static final CsvParser INSTANCE = new CsvParser();

	private static final Pattern CSV_VALUE_PATTERN = Pattern
			.compile("([^,\\r\\n\"]*|\"(([^\"]*\"\")*[^\"]*)\")(,|\\r?\\n)");

	public String getContentType() {
		return "text/plain";
	}

	public <T> T parse(HttpResponse response, Class<T> dataClass)
			throws IOException {
		T newInstance = ClassInfo.newInstance(dataClass);
		ClassInfo classInfo = ClassInfo.of(dataClass);
		response.disableContentLogging = true;
		InputStream content = response.getContent();
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					content));
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				} else {
					sb.append(line);
					sb.append("\n");
				}
			}

			try {
				//System.out.println("Response = " + sb.toString());
				List<Map<String, String>> parseResponse = parseResponse(sb
						.toString());

				Field field = classInfo.getField("records");
				Collection<Object> col = ClassInfo.newCollectionInstance(field
						.getType());
				ParameterizedType t = (ParameterizedType) field
						.getGenericType();
				Class type = (Class) t.getActualTypeArguments()[0];

				for (Map<String, String> map : parseResponse) {
					Set<String> keySet = map.keySet();

					Object o = ClassInfo.newInstance(type);
					ClassInfo childClassInfo = ClassInfo.of(type);
					for (String key : keySet) {
						o = fillCsvRecord(key, map.get(key), childClassInfo, o,
								type);
					}
					java.lang.reflect.Method add = List.class
							.getDeclaredMethod("add", Object.class);
					add.invoke(col, o);
				}

				FieldInfo.setFieldValue(field, newInstance, col);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} finally {
			content.close();
		}
		return newInstance;
	}

	public Object fillCsvRecord(String key, String value, ClassInfo classInfo,
			Object newInstance, Class dataClass) {
		Field field = classInfo.getField(key);
		if (field != null) {
			Class<?> fieldClass = field.getType();
			Object fieldValue;
			if (fieldClass == boolean.class || fieldClass == Boolean.class) {
				fieldValue = Boolean.valueOf(value);
			} else {
				fieldValue = value;
			}
			FieldInfo.setFieldValue(field, newInstance, fieldValue);
		} else if (GenericData.class.isAssignableFrom(dataClass)) {
			GenericData data = (GenericData) newInstance;
			data.set(key, value);
		} else if (Map.class.isAssignableFrom(dataClass)) {
			@SuppressWarnings("unchecked")
			Map<Object, Object> map = (Map<Object, Object>) newInstance;
			map.put(key, value);
		}
		return newInstance;
	}

	private List<Map<String, String>> parseResponse(String response) {
		Scanner scanner = new Scanner(response);
		boolean foundHeader = false;
		List<String> keys = new ArrayList<String>();
		List<Map<String, String>> entries = new ArrayList<Map<String, String>>();
		Map<String, String> lineItem = new HashMap<String, String>();

		int i = 0;
		while (scanner.hasNextLine()) {
			i++;
			scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
			MatchResult match = scanner.match();
			String quotedString = match.group(2);
			String decoded = quotedString == null ? match.group(1)
					: quotedString.replaceAll("\"\"", "\"");
			if (!foundHeader) {
				keys.add(decoded);
			} else {
				lineItem.put(keys.get(i - 1), decoded);
			}
			if (!match.group(4).equals(",")) {
				i = 0;
				if (lineItem.size() != 0) {
					entries.add(lineItem);
				}
				lineItem = new HashMap<String, String>();
				foundHeader = true;
			}
		}
		return entries;
	}

}
