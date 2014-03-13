package com.pyrobits.tlassify.gender.beans;

public class UserBioBean {
	private int UID;  
	private String screenName;
	private String Name;
	private String BioText;
	private String Link;
	private int Tweets;
	private int Followers;
	private int Following;
	private String Label;

	
	public int getUID() {
		return UID;
	}
	public void setUID(int uID) {
		UID = uID;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getBioText() {
		return BioText;
	}
	public void setBioText(String bioText) {
		BioText = bioText;
	}
	public String getLink() {
		return Link;
	}
	public void setLink(String link) {
		Link = link;
	}
	public int getTweets() {
		return Tweets;
	}
	public void setTweets(int tweets) {
		Tweets = tweets;
	}
	public int getFollowers() {
		return Followers;
	}
	public void setFollowers(int followers) {
		Followers = followers;
	}
	public int getFollowing() {
		return Following;
	}
	public void setFollowing(int following) {
		Following = following;
	}
	public String getLabel() {
		return Label;
	}
	public void setLabel(String label) {
		Label = label;
	}
	/**
	 * Function to check if the data is correctly inserted of not
	 * i.e. UID,Name and Label must be present for all the tuples
	 * @return true if everything is fine
	 */
	public boolean checkBeanData()
	{
		if(UID == 0||this.screenName == null||this.Name==null||this.screenName.equals(""))
		{
			System.err.println("UID or Screen Name or Name is not properly inserted!!");
			throw new RuntimeException();
		}
		return true;
	}
}
