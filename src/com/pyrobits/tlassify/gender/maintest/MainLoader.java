package com.pyrobits.tlassify.gender.maintest;


import com.pyrobits.tlassify.gender.loader.UserDataLoader;

public class MainLoader {
	public static void main(String[] args) {
		UserDataLoader udl = new UserDataLoader();
		for (int i = 0; i < udl.getUserBio().size(); i++) {
			System.out.println(udl.getUserBio().get(i).getName());
		}		
	}
}
