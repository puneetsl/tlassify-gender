use LWP::Simple;
use HTML::Restrict;
use HTML::Entities;

my $file = 'UID.csv';
open my $info, $file or die "Could not open $file: $!";
$i=1;
while( my $line = <$info>)  { 
	chomp $line;  
    @lineArr = split(',',$line);
    $uid = @lineArr[0];
    $screen_name = @lineArr[1];
    $run =  "./tweets.py $screen_name $uid";
    $output = system($run);
    print "waiting...\n";
    if($i%5==0)
    {
    	sleep(90);
    }
    $i++;

}

close $info;



sub getScreenName
{
 
	$user_id=$_[0];
	my $url = "https://twitter.com/intent/user?user_id=$user_id";
	my $content = get $url;
	die "Couldn't get $url" unless defined $content;
	$profile_HTML = inbetween($content,"profile summary","related recent-tweets");
	$user_info = inbetween($profile_HTML,"<h2>","</h2>");
	#$user_name = inbetween(afterfirstoccurance($user_info,"height="),">","</a>");
	$screen_name = inbetween(afterfirstoccurance($user_info,"class=\"nickname"),">","</span>");
	#$user_name =~ s/\s\s+//g;
	$screen_name =~ s/[\s@]//g;
	return $screen_name;
}
 
 
sub inbetween 
{ 
	 $str_r=$_[0];
	 $pos=index($str_r,"$_[1]");
	 $str_r=substr($str_r,$pos+length($_[1]));
	 $pos=index($str_r,"$_[2]");
	 $str_r=substr($str_r,0,$pos);
	 return $str_r;
}
sub afterfirstoccurance 
{ #without text
	 $str_r=$_[0];
	 $pos=index($str_r,"@_[1]");
	 $str_r=substr($str_r,$pos+length(@_[1]));
	 return $str_r;
}
sub beforefirstoccurance 
{ 
	 $str_r=$_[0];
	 $pos=index($str_r,"@_[1]");
	 $str_r=substr($str_r,0,$pos);
	 return $str_r;
}
sub afterfirstoccurance2 
{ #with text
	 $str_r=$_[0];
	 $pos=index($str_r,"@_[1]");
	 $str_r=substr($str_r,$pos);
	 return $str_r;
}