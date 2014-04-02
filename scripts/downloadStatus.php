<?php
function clean($string) {
   //$string = str_replace(' ', ' ', $string); // Replaces all spaces with hyphens.
   return preg_replace('/[^A-Za-z0-9\s]/', '', $string); // Removes special chars.
}
function cleann($string) {
   $string = preg_replace('/[\n\r\s]/', ' ', $string); // Replaces all spaces with hyphens.
   return $string;
}
$consumer_key = 'pIfE5YWhKCjov9XgywfxQ';
$consumer_secret = '4jqicCFdrT0MAS5Pypj8cT0HYjfAnjHDQXnQIRc';
$token = '57079268-9BBsuD0VO2nPtF3DASPIwQob94NhChdDy4Pbg83LB';
$token_secret = 'mh1vibhC4wv7vrXQ25qgTV0KCxzmz6Kr6FjOpSfN0KotN';

// $token = 'YOUR TOKEN';
// $token_secret = 'TOKEN SECRET';
// $consumer_key = 'YOUR KEY';
// $consumer_secret = 'KEY SECRET';

$host = 'api.twitter.com';
$method = 'GET';
$path = '/1.1/statuses/user_timeline.json'; // api call path
$screen_name = $argv[1];
$query = array( // query parameters
    'screen_name' => $screen_name,
    'count' => '500'
);

$oauth = array(
    'oauth_consumer_key' => $consumer_key,
    'oauth_token' => $token,
    'oauth_nonce' => (string)mt_rand(), // a stronger nonce is recommended
    'oauth_timestamp' => time(),
    'oauth_signature_method' => 'HMAC-SHA1',
    'oauth_version' => '1.0'
);

$oauth = array_map("rawurlencode", $oauth); // must be encoded before sorting
$query = array_map("rawurlencode", $query);

$arr = array_merge($oauth, $query); // combine the values THEN sort

asort($arr); // secondary sort (value)
ksort($arr); // primary sort (key)

// http_build_query automatically encodes, but our parameters
// are already encoded, and must be by this point, so we undo
// the encoding step
$querystring = urldecode(http_build_query($arr, '', '&'));

$url = "https://$host$path";

// mash everything together for the text to hash
$base_string = $method."&".rawurlencode($url)."&".rawurlencode($querystring);

// same with the key
$key = rawurlencode($consumer_secret)."&".rawurlencode($token_secret);

// generate the hash
$signature = rawurlencode(base64_encode(hash_hmac('sha1', $base_string, $key, true)));

// this time we're using a normal GET query, and we're only encoding the query params
// (without the oauth params)
$url .= "?".http_build_query($query);
$url=str_replace("&amp;","&",$url); //Patch by @Frewuill

$oauth['oauth_signature'] = $signature; // don't want to abandon all that work!
ksort($oauth); // probably not necessary, but twitter's demo does it

// also not necessary, but twitter's demo does this too
function add_quotes($str) { return '"'.$str.'"'; }
$oauth = array_map("add_quotes", $oauth);

// this is the full value of the Authorization line
$auth = "OAuth " . urldecode(http_build_query($oauth, '', ', '));

// if you're doing post, you need to skip the GET building above
// and instead supply query parameters to CURLOPT_POSTFIELDS
$options = array( CURLOPT_HTTPHEADER => array("Authorization: $auth"),
                  //CURLOPT_POSTFIELDS => $postfields,
                  CURLOPT_HEADER => false,
                  CURLOPT_URL => $url,
                  CURLOPT_RETURNTRANSFER => true,
                  CURLOPT_SSL_VERIFYPEER => false);

// do our business
$feed = curl_init();
curl_setopt_array($feed, $options);
$json = curl_exec($feed);
curl_close($feed);
$Message = "";
$twitter_data = json_decode($json);
#print_r($twitter_data);
foreach ($twitter_data as &$value) {
    $Message .= $value->created_at."#" .cleann($value->text)."\n";        
}
//$Message  = clean($Message);
echo $Message;

?>
