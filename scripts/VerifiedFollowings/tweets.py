#!/usr/bin/env python
# encoding: utf-8
# Thanks https://github.com/yanofsky for making this script
import tweepy #https://github.com/tweepy/tweepy
import csv
import sys
import time
 
#Twitter API credentials
consumer_key = "pIfE5YWhKCjov9XgywfxQ"
consumer_secret = "4jqicCFdrT0MAS5Pypj8cT0HYjfAnjHDQXnQIRc"
access_key = "57079268-9BBsuD0VO2nPtF3DASPIwQob94NhChdDy4Pbg83LB"
access_secret = "mh1vibhC4wv7vrXQ25qgTV0KCxzmz6Kr6FjOpSfN0KotN"
 
 
def get_all_tweets(screen_name,ID):
	#Twitter only allows access to a users most recent 3240 tweets with this method
	
	#authorize twitter, initialize tweepy
	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_key, access_secret)
	api = tweepy.API(auth)
	
	#initialize a list to hold all the tweepy Tweets
	alltweets = []	
	
	#make initial request for most recent tweets (200 is the maximum allowed count)
	friends = api.lookup_users(user_ids=screen_name)
	
	time.sleep(120)
	i=0
	for friend in friends:
		print screen_name[i]+","+friend.screen_name.encode("utf-8")+","+friend.name.encode("utf-8")+","+str(friend.verified)+","+str(friend.followers_count)
		i=i+1

 
 
if __name__ == '__main__':
	string = str(sys.argv[1:][0])
	text = string.split(',')
	# text = ['268414482','27195114','15846407','25365536','17919972','180505807','79293791','20322929','27260086','116362700']
	get_all_tweets(text,"")