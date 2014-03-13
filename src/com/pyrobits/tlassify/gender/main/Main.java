package com.pyrobits.tlassify.gender.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.pyrobits.tlassify.gender.generator.BioFeatureGenerator;
import com.pyrobits.tlassify.gender.generator.NameFeatureGenerator;
import com.pyrobits.tlassify.gender.loader.NameDictionaryLoader;
import com.pyrobits.tlassify.gender.loader.UserDataLoader;


public class Main {
	public static void main(String[] args) throws IOException {
		UserDataLoader udl = new UserDataLoader();
		NameDictionaryLoader ndl = new NameDictionaryLoader();
		NameFeatureGenerator nfg = new NameFeatureGenerator();
		BioFeatureGenerator bfg = new BioFeatureGenerator();
		nfg.generateFeatures(ndl, udl);
		bfg.generateFeatures(ndl, udl);
		/*
		 * TODO: Have to do this using enum in future
		 */
		File file = new File("output/features.csv");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("UID,nameFeature,");
//		bw.write("TweetNo,Followers,Following,");
		bw.write("bioFeatures,");
		bw.write("label\n");
		for (int i = 0; i < udl.getUserBio().size(); i++) {
			bw.write(udl.getUserBio().get(i).getUID()+","+nfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",");
//			bw.write(udl.getUserBio().get(i).getTweets()+","+udl.getUserBio().get(i).getFollowers()+","+udl.getUserBio().get(i).getFollowing()+",");
			bw.write(bfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",");
			bw.write(udl.getUserBio().get(i).getLabel()+"\n");
//			if(udl.getUserBio().get(i).getLabel().equals("male"))
//				bw.write(udl.getUserBio().get(i).getUID()+","+bfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",1"+"\n");
//			if(udl.getUserBio().get(i).getLabel().equals("female"))
//				bw.write(udl.getUserBio().get(i).getUID()+","+bfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",0"+"\n");
		}	
		bw.close();
	}
}
