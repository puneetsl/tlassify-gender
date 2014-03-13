package com.pyrobits.tlassify.gender.cleaner;

public class SimpleCleaner {
	public static String cleanAnythingElseThanCharachters(String textToClean)
	{
		return textToClean.replaceAll("[^a-zA-Z ]", " ");
	}
	public static String cleanCamelCasing(String textToClean)
	{
		return textToClean.replaceAll("([a-z])([A-Z])", "$1 $2");
	}
	public static String cleanRepeatingText(String textToClean)
	{
		return textToClean.replaceAll("(.)\\1{2,}+","$1");
	}
}
