package com.pyrobits.tlassify.gender.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import psl.shortcleaner.ShortCleaner;
import psl.shortcleaner.tokenizer.SimpleTokenizer;

import com.pyrobits.tlassify.gender.generator.BioFeatureGenerator;
import com.pyrobits.tlassify.gender.generator.NameFeatureGenerator;
import com.pyrobits.tlassify.gender.loader.LIWCLoader;
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
		ShortCleaner sc = new ShortCleaner();
		LIWCLoader liwcLoader = new LIWCLoader();
		/*
		 * TODO: Have to do this using enum in future
		 */
		File file = new File("output/BioText.csv");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
//		bw.write("UID,nameFeature,");
////		bw.write("TweetNo,Followers,Following,");
//		bw.write("bioFeatures,");
//		bw.write("label\n");
		bw.write("UID#");
		for (int i = 0; i < 64; i++) {
			bw.write("F"+(i+101)+",");
		}
		bw.write("F165,Label\n");
		for (int i = 0; i < udl.getUserBio().size(); i++) {
			int[] featureCount = new int[65];
//			System.out.println(udl.getUserBio().get(i).getUID()+","+udl.getUserBio().get(i).getScreenName());
			String text = sc.cleanEverything(udl.getUserBio().get(i).getBioText(), false);
			String[] tokens = SimpleTokenizer.tokenize(text);
			bw.write(udl.getUserBio().get(i).getUID()+"#");
			for (int k = 0; k < tokens.length; k++) {
				String liwcVals = liwcLoader.getDictionaryValue(tokens[k]);
				String[] liwcArr = liwcVals.split(",");
				for (int j = 0; j < liwcArr.length; j++) {
					featureCount[liwcToArrayNumber(liwcArr[j])]++;
				}
			}
			for (int k = 0; k < featureCount.length; k++) {
				bw.write(featureCount[k]+",");
			}
			bw.write(udl.getUserBio().get(i).getLabel()+"\n");
			
			for (int j = 0; j < 1000000000; j++) {}
			System.out.println(udl.getUserBio().get(i).getUID());
//			bw.write(udl.getUserBio().get(i).getUID()+","+nfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",");
//			bw.write(udl.getUserBio().get(i).getTweets()+","+udl.getUserBio().get(i).getFollowers()+","+udl.getUserBio().get(i).getFollowing()+",");
//			bw.write(bfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",");
//			bw.write(udl.getUserBio().get(i).getLabel()+"\n");
//			if(udl.getUserBio().get(i).getLabel().equals("male"))
//				bw.write(udl.getUserBio().get(i).getUID()+","+bfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",1"+"\n");
//			if(udl.getUserBio().get(i).getLabel().equals("female"))
//				bw.write(udl.getUserBio().get(i).getUID()+","+bfg.getFeatureValue(udl.getUserBio().get(i).getUID())+",0"+"\n");
		}	
		bw.close();
	}
	private static int liwcToArrayNumber(String liwcValue)
	{
		int liwc = Integer.parseInt(liwcValue);
		if(liwc<100)
			liwc = liwc+0;
		else if(liwc<200)
			liwc = liwc-119;
		else if(liwc<300)
			liwc = liwc-199;
		else if(liwc<400)
			liwc = liwc-299;
		else if(liwc<500)
			liwc = liwc-400;
		System.out.println(liwc);
		return liwc;
	}
}
