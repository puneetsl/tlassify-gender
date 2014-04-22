my $file = '10000_celeb_list';
open my $info, $file or die "Could not open $file: $!";
$k=1;
@outArr;
while( my $line = <$info>)  { 
	chomp $line;  
	@values = split(",",$line);
	push(@outArr,@values[0]);
	if($#outArr==99)
	{
		$prnt = join(",",@outArr);
		print $prnt."\n";
		undef(@outArr);
	}
}

close $info;
