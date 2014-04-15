package com.pyrobits.tlassify.gender.utils;

import java.util.ArrayList;
/**
 * This class is used to generate N-grams of the string
 * @author rahultejwani
 *
 */
public class Ngrams {

	private String sentence;
	private String[] WordSplit;
	/**
	 * Constructor that takes in the string to be broken into N-grams
	 * @param string
	 */
	public Ngrams(String string)
	{
		this.setSentence(string);
		this.setWordSplit(string.split("\\s"));
	}
	/**
	 * Ex. Input String in the constructor is "I like java"
	 * and user calls getNgram(2);
	 * output: {"i_like","like_java"}
	 * @param n (Size of the N)
	 * @return Array List of n-grams of string in order
	 */
	public ArrayList<String>  getNgrams(int n)
	{
		ArrayList<String> ngrams = new ArrayList<>();
		String ngram= "";
		for (int i = 0; i < WordSplit.length-(n-1); i++) {
			for (int j = 0; j < n; j++) {
				ngram+= WordSplit[i+j] + " ";
			}
			if(ngram.endsWith(" "))
				ngram= ngram.substring(0, ngram.length()-1);
			ngrams.add(ngram.toLowerCase());
			ngram = "";
		}
		return ngrams;		
	}

	public String[] getWordSplit() {
		return WordSplit;
	}
	private void setWordSplit(String[] wordSplit) {
		WordSplit = wordSplit;
	}
	public String getSentence() {
		return sentence;
	}
	private void setSentence(String sentence) {
		this.sentence = sentence;
	}


}
