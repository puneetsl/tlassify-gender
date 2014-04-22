my $old_fh = select(STDOUT);
$| = 1;
select($old_fh);
my $file = '10000_celeb_100line.csv';
open my $info, $file or die "Could not open $file: $!";
$k=1;
while( my $line = <$info>)  { 
	chomp $line;  
    # print $line ;
    # $uid = @lineArr[0];
    # $screen_name = @lineArr[1];
    $run =  "./tweets.py $line";
    $output = `$run`;
    print $output."\n";
    # $k++;
}

close $info;


