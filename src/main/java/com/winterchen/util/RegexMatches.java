package com.winterchen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {

    public static void main(String args[]) {
		String str = "a111ab222b";
		String pattern = "a(.*)ab(.*)b";

		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(str);
		System.out.println(m.matches());


		System.out.println(str.replaceAll("a(.*)ab(.*)b","a$2ab$1b"));

	}

}