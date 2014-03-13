package com.pyrobits.tlassify.gender.generator;


import com.pyrobits.tlassify.gender.cleaner.SimpleCleaner;
import com.pyrobits.tlassify.gender.loader.NameDictionaryLoader;
import com.pyrobits.tlassify.gender.loader.UserDataLoader;

public class BioFeatureGenerator extends FloatFeatureGenerator{
	/**
	 * I would get loaded names and userData in my class all I have to do is use them to iterate over and generate features
	 */
	public void generateFeatures(NameDictionaryLoader ndl, UserDataLoader udl)
	{
		for (int i = 0; i < udl.getUserBio().size(); i++) {
			/*
			 * Right now using only name and screen name to generate the features 
			 */
			String Bio = udl.getUserBio().get(i).getBioText();
			
			Bio = SimpleCleaner.cleanAnythingElseThanCharachters(Bio);
			Bio = SimpleCleaner.cleanCamelCasing(Bio);
			Bio = SimpleCleaner.cleanRepeatingText(Bio);
			String text = Bio;
			String[] tokenText = text.split(" ");
			float maleProb=0,femaleProb=0;
			float weightP=4f;
			int k=1;//constant
			for (String nameToken : tokenText) {
				weightP=weightP-0.2f;//giving weight to first name
				if(ndl.getMaleNameIndex(nameToken) !=0)
					maleProb += ((1000-ndl.getMaleNameIndex(nameToken))/1000f)*(weightP+k);
				if(ndl.getFemaleNameIndex(nameToken) !=0)
					femaleProb += ((1000-ndl.getFemaleNameIndex(nameToken))/1000f)*(weightP+k);
				k=0;//just to give extreme weight to first name
			}
			Float nameFeature = maleProb - femaleProb; //-ve for female +ve for male
			setFeatureValue(udl.getUserBio().get(i).getUID(), nameFeature);
		}	
	}
}
