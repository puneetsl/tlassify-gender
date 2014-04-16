package com.pyrobits.tlassify.gender.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.pyrobits.tlassify.gender.loader.LIWCLoader;
import psl.shortcleaner.tokenizer.SimpleTokenizer;

public class GenerateLIWCFeatures {
	public static void main(String[] args) {
		LIWCLoader liwcLoader = new LIWCLoader();

		try {
			File file = new File("output/LIWCFeatures.csv");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("File,");
			for (int i = 0; i < 64; i++) {
				bw.write("F"+(i+1)+",");
			}
			bw.write("F65\n");
			final File folder = new File("userdata/clean_status_without_spelling_correction");
			listFilesForFolder(folder,liwcLoader,bw);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void listFilesForFolder(final File folder,LIWCLoader liwcLoader,BufferedWriter bw) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry,liwcLoader,bw);
			} else {
				try {
					int[] featureCount = new int[65];
					String text = readFileAsString(fileEntry.getAbsolutePath());
					bw.write(fileEntry.getName()+",");
					String[] tokens = SimpleTokenizer.tokenize(text);
					for (int i = 0; i < tokens.length; i++) {
						String liwcVals = liwcLoader.getDictionaryValue(tokens[i]);
						String[] liwcArr = liwcVals.split(",");
						for (int j = 0; j < liwcArr.length; j++) {
							featureCount[liwcToArrayNumber(liwcArr[j])]++;
						}
					}
					for (int i = 0; i < featureCount.length-1; i++) {
						bw.write(featureCount[i]+",");
					}
					bw.write(featureCount[64]+"\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private static String readFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(
				new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead=0;
		while((numRead=reader.read(buf)) != -1){
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
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