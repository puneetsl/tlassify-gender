/*
Pig script to join occupation categories to the cleaned data
*/
A = load 'Cleaned_2046_Information.csv' using PigStorage(',') as (id:chararray,sname:chararray,name:chararray,verified:chararray,followers:chararray,age:chararray,gender:chararray,oid:chararray,occupationa:chararray);
B = load 'Groups.csv' using PigStorage(',') as (occupationb:chararray,category:chararray,agecategory:chararray);
joinedAB = join A by occupationa left, B by occupationb;

C = foreach joinedAB generate A::id, A::sname, A::name, A::verified, A::followers, A::age, A::gender,A::oid,A::occupationa,B::category,B::agecategory;
store C into 'joinedData' using PigStorage(',');