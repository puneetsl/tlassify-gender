package com.pyrobits.tlassify.gender.loader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import org.json.JSONObject;

import com.pyrobits.tlassify.gender.beans.UserBioBean;

/**
 * Class to load user data in JSON to 
 * @author puneet
 *
 */
public class UserDataLoader {
	private InputStream input = null;
	private Properties prop = new Properties();
	private ArrayList<UserBioBean> userBio = new ArrayList<>();
	public UserDataLoader(){
		try{
			input = new FileInputStream("properties/basic.properties");
			prop.load(input);
			BufferedReader br = new BufferedReader(new FileReader(prop.getProperty("userJSON")));
			String JSON = "",line;
			while ((line = br.readLine()) != null) {
				JSON += line+"\n";
			}
			br.close();
			JSONObject obj = new JSONObject(JSON);
			Iterator<?> it = obj.keys();
			while(it.hasNext())
			{
				String tmpKey = (String) it.next();
				JSONObject temp= obj.getJSONObject(tmpKey);
				if(temp.has("Name"))
				{
					UserBioBean ubbTemp = new UserBioBean();
					ubbTemp.setName(temp.getString("Name"));
					ubbTemp.setUID(Integer.parseInt(temp.getString("UID")));
					ubbTemp.setScreenName(temp.getString("Screen_Name"));
					ubbTemp.setBioText(temp.getString("Bio"));
					ubbTemp.setLink(temp.getString("Link"));
					ubbTemp.setLabel(temp.getString("Label"));
					ubbTemp.setTweets(Integer.parseInt(temp.getString("Tweets")));
					ubbTemp.setFollowers(Integer.parseInt(temp.getString("Followers")));
					ubbTemp.setFollowing(Integer.parseInt(temp.getString("Following")));
					ubbTemp.checkBeanData();
					userBio.add(ubbTemp);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public ArrayList<UserBioBean> getUserBio() {
		return userBio;
	}
}
