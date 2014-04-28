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


try:
	celeb = str(sys.argv[1:][0])
	celeb = wikipedia.suggest(celeb)
	if(str(celeb)=="None"):
		try:
			sentence =  wikipedia.summary(str(sys.argv[1:][0]), sentences=1)
			total_summary = wikipedia.page(str(sys.argv[1:][0])).content
		except:
			print "NULL,NULL"
			sys.exit(0)
	else:
		try:
			sentence =  wikipedia.summary(celeb, sentences=1)
			total_summary = wikipedia.page(celeb).content
		except:
			print "NULL,NULL"
			sys.exit(0)
except:
	print "NULL,NULL"
	sys.exit(0)
he_cnt = total_summary.count(' he ')
he_cnt += total_summary.count(' him ')
he_cnt += total_summary.count(' his ')
he_cnt += total_summary.count('.He ')

she_cnt = total_summary.count(' she ')
she_cnt = total_summary.count(' her ')
she_cnt += total_summary.count('.She ')


sentence = sentence.replace(",","")
person = sentence.count('born')
person += sentence.count('(')

if(person>0):
	hasSemiColon = sentence.count(';')
	if(hasSemiColon>0):
		try:
			vals = sentence.split(";")
			text = vals[len(vals)-1]
			text = text.split(")")[0]
		except:
			print "NULL,"+"NULL"
			sys.exit(0)
		try:
			prog = re.search("[\d]+ [A-Za-z]+ \d\d\d\d|[A-Za-z]+ [\d]+ \d\d\d\d",text,re.M|re.I)
			text = prog.group()
		except:
			text = sentence
		sentence = (text)
	else:
		try:
			text = (sentence.split("("))[1].split(")")[0];
			prog = re.search("\d\d [A-Za-z]+ \d\d\d\d|[A-Za-z]+ [\d]+ \d\d\d\d",text,re.M|re.I)
			text = prog.group()
		except:
			text = sentence
		sentence = (text)
	try:
		datey = dparser.parse(sentence,fuzzy=True)
		age = days_between(str(dparser.parse("")),str(datey))/365
	except:
		print "NULL,"+str(gender(he_cnt,she_cnt))
		sys.exit(0)
	
	print str(age_level(age))+","+str(gender(he_cnt,she_cnt))
else:
	print "ORG,ORG"


