use LWP::Simple;
use HTML::Restrict;
use HTML::Entities;

my $file = 'UID_full.csv';
open my $info, $file or die "Could not open $file: $!";
$i=1;
while( my $line = <$info>)  { 
	chomp $line;  
    @lineArr = split(',',$line);
    $uid = @lineArr[0];
    $screen_name = @lineArr[1];
    $run = "cp Users/$screen_name".".csv raw_data_1000/$uid";
    #print $run;
    $ggg = `$run`;

}

close $info;