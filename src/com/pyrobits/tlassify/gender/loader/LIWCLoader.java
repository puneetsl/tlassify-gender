package com.pyrobits.tlassify.gender.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.pyrobits.tlassify.gender.utils.SimpleDictionaryLoader;

public class LIWCLoader {


private static SimpleDictionaryLoader liwc_abs = null;
private static SimpleDictionaryLoader liwc_stem = null;
private InputStream input = null;
	private void loadDictionary()
	{
		try {
			liwc_abs = new SimpleDictionaryLoader();
			liwc_stem = new SimpleDictionaryLoader();
			input = new FileInputStream("dictionaries/LIWC/liwc2007_absolute.db");
			liwc_abs.load(input);
			input = new FileInputStream("dictionaries/LIWC/liwc2007_withstem.db");
			liwc_stem.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getDictionaryValue(String text) {
		if(liwc_abs == null || liwc_stem ==null)
		{
			loadDictionary();
		}
		String dictValue = liwc_abs.getDictionaryValue(text.toLowerCase());
		
		if(dictValue !=null)
			return dictValue;
		else
		{
			for(int i=1;i<text.length()-1;i++)
			{
				String tempText = text.substring(0, text.length()-i);
				dictValue = liwc_stem.getDictionaryValue(tempText.toLowerCase());
				if(dictValue !=null)
					return dictValue;
			}
		}
		
		return "0";
	}
}
