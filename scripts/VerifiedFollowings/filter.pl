my $file = '10000_celebs_out2.csv';
open my $info, $file or die "Could not open $file: $!";
$k=1;
@outArr;
while( my $line = <$info>)  { 
	chomp $line;  
	@values = split(",",$line);
	if(@values[4]>10000 or @values[3] eq "True")
	{
		$prnt = join(",",@values);
		print $prnt."\n";	
	}
}

close $info;
