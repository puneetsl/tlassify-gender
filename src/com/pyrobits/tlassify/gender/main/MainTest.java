package com.pyrobits.tlassify.gender.main;

import com.pyrobits.tlassify.gender.loader.NameDictionaryLoader;

public class MainTest {
	public static void main(String[] args) {
		NameDictionaryLoader udl = new NameDictionaryLoader();
		System.out.println(udl.getFemaleNameIndex("sofia"));
		
	}
}
