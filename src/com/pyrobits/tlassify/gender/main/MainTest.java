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
	public static void main(String[] args) throws IOException, JSONException {
		//InputStream input = null;
		NameDictionaryLoader udl = new NameDictionaryLoader();
		System.out.println(udl.getFemaleNameIndex("sofia"));
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
				String Name = temp.getString("Name");
				String Label = temp.getString("Label");
				String myLabel="";
				
				String nameTokens[] = Name.split(" ");
				float maleProb=0,femaleProb=0;
				for (String nameToken : nameTokens) {
					if(udl.getMaleNameIndex(nameToken) !=0)
						maleProb += (1000-udl.getMaleNameIndex(nameToken));
					if(udl.getFemaleNameIndex(nameToken) !=0)
						femaleProb += (1000-udl.getFemaleNameIndex(nameToken));
				}
				if(maleProb>femaleProb)
				{
					myLabel = "male"; 
				}
				else if(maleProb<femaleProb)
				{
					myLabel = "female";
				}
				if(Label.equals("male"))
				{
					totalMale++;
					if(myLabel.equals("male"))
						rightMale++;
					if(myLabel.equals("female"))
						wrongMale++;
				}
				if(Label.equals("female"))
				{
					totalFemale++;
					if(myLabel.equals("female"))
						rightFemale++;
					if(myLabel.equals("male"))
						wrongFemale++;
				}
				
			}
		}
		System.out.println("Total Male:"+totalMale);
		System.out.println("Right Male:"+rightMale);
		System.out.println("Wrong Male:"+wrongMale);
		System.out.println("Not Classified Male:"+(totalMale-wrongMale-rightMale));
		System.out.println("Total Female:"+totalFemale);
		System.out.println("Right Female:"+rightFemale);
		System.out.println("Wrong Female:"+wrongFemale);
		System.out.println("Not Classified Female:"+(totalFemale-wrongFemale-rightFemale));
	}
}
