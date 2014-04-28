A = load 'Cleaned_2046_Information.csv' using PigStorage(',') as (id:chararray,sname:chararray,name:chararray,verified:chararray,followers:chararray,age:chararray,gender:chararray,oid:chararray,occupationa:chararray);
B = load 'Groups.csv' using PigStorage(',') as (occupationb:chararray,category:chararray,agecategory:chararray);
