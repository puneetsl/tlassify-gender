package com.pyrobits.tlassify.gender.main;
/*
 * My class to test stuff, will  not use this ultimately
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import com.pyrobits.tlassify.gender.loader.NameDictionaryLoader;
public class MainTest {
	public static String cleanName(String input)
	{
		return input.replaceAll("(.)\\1{2,}+","$1");
	}

	public static void main(String[] args) throws IOException, JSONException {
		//InputStream input = null;
		NameDictionaryLoader udl = new NameDictionaryLoader();
//		System.out.println(udl.getFemaleNameIndex("Maria"));
		BufferedReader br = new BufferedReader(new FileReader("userdata/gender.user.json"));
		String line;
		String str = "";
		int rightMale=0,totalMale=0,wrongMale=0;
		int rightFemale=0,totalFemale=0,wrongFemale=0;
		while ((line = br.readLine()) != null) {
		   str += line+"\n";
		}
		br.close();
		JSONObject obj = new JSONObject(str);
		//@SuppressWarnings("unchecked")
		Iterator<?> it = obj.keys();
		 
		while(it.hasNext())
		{
			String tmpKey = (String) it.next();
			JSONObject temp= obj.getJSONObject(tmpKey);
			if(temp.has("Name"))
			{
				String Name = temp.getString("Name").replaceAll("[^a-zA-Z ]", " ");
				String ScreenName = temp.getString("Screen_Name").replaceAll("[^a-zA-Z ]", " ");
				Name = Name.replaceAll("([a-z])([A-Z])", "$1 $2");//CamelCaseRemove
				Name = cleanName(Name);
				ScreenName = ScreenName.replaceAll("([a-z])([A-Z])", "$1 $2");//CamelCaseRemove
				ScreenName = cleanName(ScreenName);
				String Bio = temp.getString("Bio").replaceAll("[^a-zA-Z ]", " ");
				Bio = cleanName(Bio);
				String Label = temp.getString("Label");
				String myLabel="";
				Name = Name +" " +ScreenName;
				Name =Name +" "+ Bio;
				String nameTokens[] = Name.split(" ");
				float maleProb=0,femaleProb=0;
				float i=4f;
				int k=1;
				for (String nameToken : nameTokens) {
					i=i-0.2f;//giving weight to first name
					if(udl.getMaleNameIndex(nameToken) !=0)
						maleProb += ((1000-udl.getMaleNameIndex(nameToken))/1000f)*(i+k);
					if(udl.getFemaleNameIndex(nameToken) !=0)
						femaleProb += ((1000-udl.getFemaleNameIndex(nameToken))/1000f)*(i+k);
					k=0;
				}
				
				if(maleProb>femaleProb)
				{
					System.out.println(maleProb+"=>"+femaleProb);
					myLabel = "male"; 
				}
				else if(maleProb<femaleProb)
				{
					myLabel = "female";
				}
				else
				{
					
//					System.out.println(Name);
				}
				if(Label.equals("male"))
				{
					totalMale++;
//					System.out.println(Bio);
					if(myLabel.equals("male"))
						rightMale++;
					if(myLabel.equals("female"))
					{
						wrongMale++;
//						System.out.println("wrongly classified as female:"+Name);
					}
					
				}
				if(Label.equals("female"))
				{
					totalFemale++;
//					System.out.println(Bio);	
					if(myLabel.equals("female"))
						rightFemale++;
					if(myLabel.equals("male"))
					{
						wrongFemale++;
//						System.out.println("wrongly classified as male:"+Name);
					}
				}
				
			}
		}
		System.out.println("Total Male:"+totalMale);
		System.out.println("Right Male:"+rightMale);
		System.out.println("wrongly classified as female:"+wrongMale);
		System.out.println("Not Classified Male:"+(totalMale-wrongMale-rightMale));
		System.out.println("Total Female:"+totalFemale);
		System.out.println("Right Female:"+rightFemale);
		System.out.println("wrongly classified as male:"+wrongFemale);
		System.out.println("Not Classified Female:"+(totalFemale-wrongFemale-rightFemale));
	}
}
