package com.pyrobits.tlassify.gender.maintest;

import psl.shortcleaner.ShortCleaner;

public class ShortCleanerTest {
	public static void main(String[] args) {
		ShortCleaner sc = new ShortCleaner();
		System.out.println(sc.cleanEverything("I am so goud that I cen be heppy", true));
	}
}
