#!/usr/bin/env python
# encoding: utf-8
# Thanks https://github.com/yanofsky for making this script
import tweepy #https://github.com/tweepy/tweepy
import csv
import sys
 
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
	new_tweets = api.user_timeline(screen_name = screen_name,count=200)
	
	#save most recent tweets
	alltweets.extend(new_tweets)
	
	#save the id of the oldest tweet less one
	oldest = alltweets[-1].id - 1
	
	#keep grabbing tweets until there are no tweets left to grab
	while len(new_tweets) > 0:
		print "getting tweets before %s" % (oldest)
		
		#all subsiquent requests use the max_id param to prevent duplicates
		new_tweets = api.user_timeline(screen_name = screen_name,count=200,max_id=oldest)
		
		#save most recent tweets
		alltweets.extend(new_tweets)
		
		#update the id of the oldest tweet less one
		oldest = alltweets[-1].id - 1
		
		print "...%s tweets downloaded so far" % (len(alltweets))
		if len(alltweets)>1000:
			break
	#transform the tweepy tweets into a 2D array that will populate the csv	
	outtweets = [[tweet.id_str, tweet.created_at, tweet.text.encode("utf-8")] for tweet in alltweets]
	
	#write the csv	
	with open('Users/%s.csv' % ID, 'wb') as f:
		writer = csv.writer(f)
		writer.writerow(["id","created_at","text"])
		writer.writerows(outtweets)
	
	pass
 
 
if __name__ == '__main__':
	#pass in the username of the account you want to download
	print "Doing it for %s" % str(sys.argv[1:][0])
	get_all_tweets(str(sys.argv[1:][0]),str(sys.argv[1:][0]))