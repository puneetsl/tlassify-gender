package com.pyrobits.tlassify.gender.utils;

import java.util.Properties;

public class SimpleDictionaryLoader extends Properties{

	/**
	 * created this class just for using property file format with
	 * dictionaries
	 * @author puneet
	 */
	private static final long serialVersionUID = 7456750597411778350L;
	public String getDictionaryValue(String key)
	{
		return this.getProperty(key);
	}
}
