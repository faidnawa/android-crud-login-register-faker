<?php

require_once 'DB_Functions.php';
include 'token.php';
 $token = get_token(5);
 // panjang 15 karakter
 //echo get_token(5);
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['setToken']) && isset($_POST['updatepassword'])) {

    // receiving the post params
    $to = $_POST['setToken'];
	$pas = $_POST['updatepassword'];
   
   $password=$pas;
		$salt = sha1(rand());
        $salt = substr($salt, 0, 10);
		//$salt = "3371140c6b";
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
		$hash = array("salt" => $salt, "encrypted" => $encrypted);
		$saltup=$hash['salt'];
		$pasup= $hash['encrypted'];
		$user = $db->resetpass($to,$saltup,$pasup);
   if ($user != false) {
        // use is found
        $response["error"] = FALSE;
       $response["user"]["salt"] = $user["salt"];
        echo json_encode($response);
			
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "tyuy ";
        echo json_encode($response);
    }
		
		
		
		
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Email Salah!";
    echo json_encode($response);
}
?>