package com.pyrobits.tlassify.gender.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.pyrobits.tlassify.gender.loader.UserDataLoader;


public class Verified {

	public static void main(String[] args) throws IOException {
		UserDataLoader udl = new UserDataLoader();
		Properties prop = new Properties();  
		InputStream input = new FileInputStream("properties/labels.properties");
		prop.load(input);
		try {
			File file = new File("output/VerifiedFeatures_gender_nolog.csv");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("ID,");
			for (int i = 0; i < 1; i++) {
				bw.write("F"+(i+1)+",");
			}
			bw.write("F2,Label\n");
			final File folder = new File("/home/puneet/Development/workspace/tlassify-gender/scripts/VerifiedFollowings/infoExtract/Follower/USERS");
			listFilesForFolder(folder,udl,bw,prop);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void listFilesForFolder(final File folder,UserDataLoader udl,BufferedWriter bw, Properties prop) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry,udl,bw,prop);
			} else {
				try {
					String text = readFileAsString(fileEntry.getAbsolutePath());
					if(!text.equals(""))
					{
						fileEntry.getName();
						float age[] = new float[5];
						float gender[] = new float[3];
						float category[] = new float[13];
						float ageCategory[] = new float[3];
						if(fileEntry.getParent().contains("."))
						{	
							String user = fileEntry.getParent().split("\\.")[0];
							String[] temp = user.split("/");
							user = temp[temp.length-1];
							String file = fileEntry.getAbsolutePath(); 
							boolean flag=false;
							System.out.println("===>"+file);
							if(file.contains("part-r-00000")&&!file.contains("crc"))
							{
								try(BufferedReader br = new BufferedReader(new FileReader(file))) {
									for(String line; (line = br.readLine()) != null; ) {
										if(line.contains(","))
										{
											flag = true;
											String[] Vals = line.split(",");
											System.out.println(line);
											int followers = Integer.parseInt(Vals[4]);

											double fValue = 1.0;
											if(Vals[3].equals("True"))
											{
												fValue = Math.log(2*followers/2046);
											}
											else
											{
												fValue = Math.log(followers/2046);
											}
											age[Integer.parseInt(Vals[5])-1]+=fValue;
											gender[Integer.parseInt(Vals[6])]+=fValue;
											category[getCategory(Vals[9])]+=fValue;
											ageCategory[getAgeCategory(Vals[10])]+=fValue;
										}

									}
									// line is not visible here.
								}
								if(flag)
								{
									int index = getBioIndex(udl,user);
									if(index>=0)
									{
										int flwng = udl.getUserBio().get(index).getFollowing();
										//									int flwng =1;
										bw.write(user+",");
										//TODO: have to normalize
//										for (int i = 0; i < age.length; i++) {
//											bw.write(age[i]/flwng+",");
//										}
										for (int i = 0; i < gender.length-1; i++) {
//											if(i==gender.length-1)
//												bw.write("0"+",");
//											else
												bw.write(gender[i]/flwng+",");
										}
//										for (int i = 0; i < category.length; i++) {
////											if(i==4)
////												bw.write("0"+",");
////											else
//												bw.write(category[i]/flwng+",");
//										}
//										for (int i = 0; i < ageCategory.length; i++) {
//											bw.write(ageCategory[i]/flwng+",");
//										}
										bw.write(prop.getProperty(user)+"\n");
									}

								}
							}
						}

					}

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}
	private static int getBioIndex(UserDataLoader udl, String user) {
		for (int i = 0; i < udl.getUserBio().size(); i++) {
			if(udl.getUserBio().get(i).getUID()==Integer.parseInt(user))
				return i;
		}
		return -1;
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
	private static int getAgeCategory(String category)
	{
		switch (category) {
		case "Young":
			return 0;
		case "Old":
			return 1;
		case "General":
			return 2;
		default:
			return 2;
		}
	}
	private static int getCategory(String category)
	{
		int retInt;
		switch (category) {
		case "Acting":
			retInt = 0;
			break;
		case "Art":
			retInt = 1;
			break;
		case "Business":
			retInt = 2;
			break;
		case "Entertainment":
			retInt = 3;
			break;
		case "Misc":
			retInt = 4;
			break;
		case "Music":
			retInt = 5;
			break;
		case "Politics":
			retInt = 6;
			break;
		case "Religious":
			retInt = 7;
			break;
		case "Science":
			retInt = 8;
			break;
		case "Security":
			retInt = 9;
			break;
		case "Social":
			retInt = 10;
			break;
		case "Sports":
			retInt = 11;
			break;
		case "Writing":
			retInt = 12;
			break;
		default:
			retInt = 13;
			break;
		}
		return retInt;
	}
}
