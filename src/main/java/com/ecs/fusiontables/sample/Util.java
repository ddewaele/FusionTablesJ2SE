package com.ecs.fusiontables.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Util {

	private static final Pattern CSV_VALUE_PATTERN = Pattern
			.compile("([^,\\r\\n\"]*|\"(([^\"]*\"\")*[^\"]*)\")(,|\\r?\\n)");

//	public static List<Map<String, String>> parseResponse(String response) {
//		Scanner scanner = new Scanner(response);
//		boolean foundHeader = false;
//		List<String> keys = new ArrayList<String>();
//		List<Map<String, String>> entries = new ArrayList<Map<String, String>>();
//		Map<String, String> lineItem = new HashMap<String, String>();
//
//		int i = 0;
//		while (scanner.hasNextLine()) {
//			i++;
//			scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
//			MatchResult match = scanner.match();
//			String quotedString = match.group(2);
//			String decoded = quotedString == null ? match.group(1)
//					: quotedString.replaceAll("\"\"", "\"");
//			if (!foundHeader) {
//				keys.add(decoded);
//			} else {
//				lineItem.put(keys.get(i - 1), decoded);
//			}
//			if (!match.group(4).equals(",")) {
//				i = 0;
//				if (lineItem != null) {
//					entries.add(lineItem);
//				}
//				lineItem = new HashMap<String, String>();
//				foundHeader = true;
//			}
//		}
//		return entries;
//	}
	
	
	public static List<Map<String,String>> parseResponse(String response) {
		    Scanner scanner = new Scanner(response);
		    boolean foundHeader=false;
		    List<String> keys = new ArrayList<String>();
		    List<Map<String,String>> entries = new ArrayList<Map<String, String>>();
		    Map<String,String> lineItem = new HashMap<String, String>();
		    
		    int i=0;
		    while (scanner.hasNextLine()) {
		      i++;
		      scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
		      MatchResult match = scanner.match();
		      String quotedString = match.group(2);
		      String decoded = quotedString == null ? match.group(1) : quotedString.replaceAll("\"\"", "\"");
		      if (!foundHeader) {
		        keys.add(decoded);
		      } else {
		        lineItem.put(keys.get(i-1), decoded);
		      }
		      if (!match.group(4).equals(",")) {
		        i=0;
		        entries.add(lineItem);
		        lineItem = new HashMap<String, String>();
		        foundHeader=true;
		      }
		    }
		    return entries;
		  }
}
