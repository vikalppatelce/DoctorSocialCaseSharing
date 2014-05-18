/* HISTORY
 * CATEGORY 		:- ACTIVITY
 * DEVELOPER		:- VIKALP PATEL
 * AIM			    :- ADD IPD ACTIVITY
 * DESCRIPTION 		:- SAVE IPD
 * VOWELS ARE REMOVED -> SAME LETTER NEXT TO EACH OTHER ARE REMOVED 
 * E.G UJJAWAL PATEL -> 1. JJWLPTL -> 2. JWLPTL
 * USE: NEEL & NIL BOTH ARE SAME --> NL & NL AFTER PASSING THROUGH SEARCHALGO 
 * DESCRIPTION :- SMART SEARCH.
 * 
 * S - START E- END  C- COMMENTED  U -EDITED A -ADDED
 * --------------------------------------------------------------------------------------------------------------------
 * INDEX       DEVELOPER		DATE			FUNCTION		DESCRIPTION
 * --------------------------------------------------------------------------------------------------------------------
 * --------------------------------------------------------------------------------------------------------------------
 */

package com.netdoers.com.utils;

import android.util.Log;

public class SearchAlgo {

	public static String getNameSearchAlgo(String name) {
		StringBuffer sb = new StringBuffer();
		String str = "";
		try {

			if (name.toString().length() > 0) {
				name = removeVowels(name.replaceAll("\\s+", "").toLowerCase().trim());
				name = name.replaceAll("[^\\w\\s]","");
				char current, next;
				for (int i = 0; i < name.length() - 1; i++) {
					current = name.charAt(i);
					next = name.charAt(i + 1);
					if (current != next) {
						sb.append(current);
						if (i == name.length() - 2) {
							if (current != next) {
								sb.append(next);
							}
						}
					}
				}
				if (sb.toString().length() > 0) {
					str = sb.toString();
				} else {
					str = name.toString();
				}
			} else {
				str = name.toString();
			}
		} catch (Exception e) {
			Log.e("SearchAlgo", e.toString());
			str = name;
		}
		return str;
	}

	public static String removeVowels(String str) {
		// base cases
		try {
			if (str == null) {
				return null;
			}
			if (str.equals("")) {
				return "";
			}

			// recursive case

			// Make a recursive call to remove vowels from the
			// rest of the string.
			String removedFromRest = removeVowels(str.substring(1));

			// If the first character in str is a vowel,
			// we don't include it in the return value.
			// If it isn't a vowel, we do include it.
			char first = str.charAt(0);
			if (first == 'a' || first == 'e' || first == 'i' || first == 'o'
					|| first == 'u') {
				return removedFromRest;
			} else {
				return first + removedFromRest;
			}
		} catch (Exception e) {
			Log.e("SearchAlgo", e.toString());
			return str;
		}
	}
}
