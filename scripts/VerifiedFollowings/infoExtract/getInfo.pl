# /home/puneet/Development/Freebase/freebase/getPersonInfo.py
my $file = 'celebsWithmoreThan10000Followers.csv';
open my $info, $file or die "Could not open $file: $!";

while( my $line = <$info>)  {   
   chomp($line);
   @vals = split(",",$line);
   $name = @vals[2];
   $age_sex = `python getWikiData.py "$name"`;
   chomp($age_sex);
   $Profession = `python /home/puneet/Development/Freebase/freebase/getPersonInfo.py "$name"`;
   chomp($Profession);
   print @vals[0].",".@vals[1].",".@vals[2].",".@vals[3].",".@vals[4].",".$age_sex.",".$Profession."\n";
}

close $info;
