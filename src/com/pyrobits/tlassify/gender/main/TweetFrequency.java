package com.pyrobits.tlassify.gender.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import com.pyrobits.tlassify.gender.loader.LIWCLoader;

import psl.shortcleaner.ShortCleaner;
import psl.shortcleaner.tokenizer.SimpleTokenizer;

public class TweetFrequency {
	public static void main(String[] args) throws IOException {
		Properties prop = new Properties();  
		InputStream input = new FileInputStream("properties/labels.properties");
		prop.load(input);
		try {
			File file = new File("output/TweetFreqFeature.csv");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("File,");
			bw.write("Frequency,Label\n");
			final File folder = new File("userdata/raw_status");
			listFilesForFolder(folder,bw,prop);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void listFilesForFolder(final File folder,BufferedWriter bw, Properties prop) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry,bw,prop);
			} else {
				try {
					int[] featureCount = new int[65];
					String text = readFileAsString(fileEntry.getAbsolutePath());
					
					if(!text.equals(""))
					{
						
						String[] lines = text.split("\n");
						if(lines.length>0)
						{
							bw.write(fileEntry.getName()+",");
							String[] vals = lines[0].split("#");
							String first = vals[0];
							vals = lines[lines.length-1].split("#");
							String last = vals[0];
							//System.out.println(first+","+last);
							//--------------------------------
							String[] time1Str = first.split(" ");
							String[] time2Str = last.split(" ");
							Date date1,date2;
							try {
								date1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(first);
								date2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(last);
								int diffInDays = (int)( (date1.getTime() - date2.getTime()) 
						                 / (1000 * 60 * 60 * 24) );
								bw.write(diffInDays+",");
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							 // Sat Jan 02 00:00:00 BOT 2010
							bw.write(prop.getProperty(fileEntry.getName())+"\n");
						}
					}
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
}
