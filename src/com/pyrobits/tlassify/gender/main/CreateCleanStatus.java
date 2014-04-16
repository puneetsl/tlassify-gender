package com.pyrobits.tlassify.gender.main;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import psl.shortcleaner.ShortCleaner;

public class CreateCleanStatus {

	public static void main(String[] args) {
		ShortCleaner sc = new ShortCleaner();
		final File folder = new File("userdata/raw_status");
		listFilesForFolder(folder, 1,sc);
	}
	public static void listFilesForFolder(final File folder,int label, ShortCleaner sc) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,label,sc);
	        } else {
//	            System.out.println(fileEntry.getName());
	            try {
					String text = readFileAsString(fileEntry.getAbsolutePath());
					String[] lines = text.split("\n");
					File file = new File("userdata/clean_status_with_spelling_correction/"+fileEntry.getName());
					System.out.println("Writing file: "+fileEntry.getName());
					// if file doesnt exists, then create it
					if (!file.exists()) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					for (int i = 0; i < lines.length; i++) {
						System.out.println("Writing line: "+i);
						String string = lines[i];
						String[] vals = string.split("#",3);
						bw.write(sc.cleanEverything(vals[1], true)+"\n");
						//System.out.println(sc.cleanEverything(vals[1], true)); 
					}
					bw.close();
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
