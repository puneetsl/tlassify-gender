#!/bin/bash
for i in `ls USERS`
{
	echo $i;
	`pig -x local -param user=USERS/$i -f joinFollowers.pig`;
}