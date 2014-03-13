package com.pyrobits.tlassify.gender.main;

import com.pyrobits.tlassify.gender.generator.BioFeatureGenerator;
import com.pyrobits.tlassify.gender.loader.NameDictionaryLoader;
import com.pyrobits.tlassify.gender.loader.UserDataLoader;

public class Main {
	public static void main(String[] args) {
		UserDataLoader udl = new UserDataLoader();
		NameDictionaryLoader ndl = new NameDictionaryLoader();
		BioFeatureGenerator bfg = new BioFeatureGenerator();
		bfg.generateFeatures(ndl, udl);
		/*
		 * TODO: Have to do this using enum in future
		 */
		for (int i = 0; i < udl.getUserBio().size(); i++) {
			System.out.println(udl.getUserBio().get(i).getName()+","+bfg.getFeatureValue(udl.getUserBio().get(i).getUID())+","+udl.getUserBio().get(i).getLabel());
		}	
	}
}
