#!/bin/bash
for i in `ls raw_status_200`
{
	# awk 'BEGIN{FS=OFS=","}{$1="";sub(",","")}1' raw_data_1000/$i>raw_status_1000/$i;
	sed 's/#/,/g'  raw_status_200/$i> temp200/$i
}