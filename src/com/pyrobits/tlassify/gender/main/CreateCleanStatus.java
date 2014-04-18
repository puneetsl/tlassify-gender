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
		final File folder = new File("userdata/raw_status_1000");
		listFilesForFolder(folder, 1,sc,0);
	}
	public static void listFilesForFolder(final File folder,int label, ShortCleaner sc,int flag) {
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,label,sc,flag);
	        } else {
//	            System.out.println(fileEntry.getName());
	            try {    
//	            	if(fileEntry.getName().equals("243319382"))
	            		flag=1;
	            	if(flag==1)
	            	{
	            		String text = readFileAsString(fileEntry.getAbsolutePath());
						String[] lines = text.split("\n");
//						for (int j = 0; j < 100000000; j++) {}
						File file = new File("userdata/1000/clean_status_without_spelling_correction/"+fileEntry.getName());
						for (int j = 0; j < 100000000; j++) {}
						System.out.println("Writing file: "+fileEntry.getName());
						// if file doesnt exists, then create it
						if (!file.exists()) {
							file.createNewFile();
							for (int j = 0; j < 100000000; j++) {}
						}
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						for (int i = 0; i < lines.length; i++) {
//							System.out.println("Writing line: "+i);
							String string = lines[i];
							String[] vals = string.split(",",3);
							if(vals.length>1)
							{
								String toWrite = sc.cleanEverything(vals[1], false);
								bw.write(toWrite+"\n");
							}
//							for (int j = 0; j < 100000000; j++) {}
							
							//System.out.println(sc.cleanEverything(vals[1], true)); 
						}
						System.gc();
						bw.close();
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
