# -*- coding: utf-8 -*-
import wikipedia
import sys
import codecs
import re
import dateutil.parser as dparser
from datetime import datetime
def days_between(d1, d2):
    d1 = datetime.strptime(d1, "%Y-%m-%d %H:%M:%S")
    d2 = datetime.strptime(d2, "%Y-%m-%d %H:%M:%S")
    return abs((d2 - d1).days)
def age_level(age):
	if(age<23):
		return 1
	if(age<30):
		return 2
	if(age<40):
		return 3
	if(age<50):
		return 4
	else:
		return 5
def gender(he,she):
	if(he>she):
		return "1"
	if(she>he):
		return "0"
	else:
		return "2"



celeb = str(sys.argv[1:][0])
celeb = wikipedia.suggest(celeb)
if(str(celeb)=="None"):
	sentence =  wikipedia.summary(str(sys.argv[1:][0]), sentences=1)
	total_summary = wikipedia.page(str(sys.argv[1:][0])).content
else:
	sentence =  wikipedia.summary(celeb, sentences=1)
	total_summary = wikipedia.page(celeb).content
he_cnt = total_summary.count(' he ')
he_cnt += total_summary.count(' him ')
he_cnt += total_summary.count('.He ')

she_cnt = total_summary.count(' she ')
she_cnt = total_summary.count(' her ')
she_cnt += total_summary.count('.She ')
Music = total_summary.count(' music ')
Music += total_summary.count('Music')
Politics = total_summary.count(' politics ')
Politics += total_summary.count('Politics')
Comedy = total_summary.count(' comedy ')
Comedy += total_summary.count('Comedy')

sentence = sentence.replace(",","")
person = sentence.count('born')
person += sentence.count('(')

if(person>0):
	hasSemiColon = sentence.count(';')
	if(hasSemiColon>0):
		vals = sentence.split(";")
		text = vals[len(vals)-1]
		text = text.split(")")[0]
		prog = re.search("\d\d [A-Za-z]+ \d\d\d\d",text,re.M|re.I)
		text = prog.group()
		sentence = (text)
	else:
		text = (sentence.split("("))[1].split(")")[0];
		prog = re.search("\d\d [A-Za-z]+ \d\d\d\d",text,re.M|re.I)
		text = prog.group()
		sentence = (text)
	datey = dparser.parse(sentence,fuzzy=True)
	print datey
	print days_between(str(dparser.parse("")),str(datey))/365
	print "Music: "+str(Music)
	print "Politics: "+str(Politics)
	print "Comedy: "+str(Comedy)

	
else:
	print "It's and organization"


