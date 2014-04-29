A = load 'Cleaned_2046_Information_categories.csv' using PigStorage(',') as (id:chararray,sname:chararray,name:chararray,verified:chararray,followers:chararray,age:chararray,gender:chararray,oid:chararray,occupationa:chararray,category:chararray,agecategory:chararray);
B = load '$user' as (uid:chararray);

joinedAB = join A by id, B by uid;

C = foreach joinedAB generate A::id, A::sname, A::name, A::verified, A::followers, A::age, A::gender,A::oid,A::occupationa,A::category,A::agecategory;
store C into 'Follower/$user' using PigStorage(',');