package com.pyrobits.tlassify.gender.loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class NameDictionaryLoader {
	private InputStream input = null;
	private Properties prop = new Properties();
	private HashMap<String, Integer> maleNames = new HashMap<>();
	private HashMap<String, Integer> femaleNames = new HashMap<>();
	public NameDictionaryLoader() {
		try {
			input = new FileInputStream("properties/basic.properties");
			prop.load(input);
			BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("maleNames")));
			String line;
			int i=1;
			while ((line = br.readLine()) != null) {
			   maleNames.put(line.toLowerCase(), i++);
			}
			br.close();
			br = new BufferedReader(new FileReader(prop.getProperty("femaleNames")));
			i=1;
			while ((line = br.readLine()) != null) {
			   femaleNames.put(line.toLowerCase(), i++);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public int getMaleNameIndex(String name)
	{
		int retIndex;
		try{
			retIndex = maleNames.get(name.toLowerCase());
		}
		catch(Exception e)
		{
			return 0;
		}
		return retIndex;
	}
	public int getFemaleNameIndex(String name)
	{
		int retIndex;
		try{
			retIndex = femaleNames.get(name.toLowerCase());
		}
		catch(Exception e)
		{
			return 0;
		}
		return retIndex;
	}
}
