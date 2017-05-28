<?php

require_once 'DB_Functions.php';
include 'token.php';
 $token = get_token(5);
 // panjang 15 karakter
 //echo get_token(5);
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['email'])) {

    // receiving the post params
    $email = $_POST['email'];
    // get the user by email and password
    $user = $db->cekemail($email,$token);

    if ($user != false) {
        // use is found
        $response["error"] = FALSE;
        $response["user"]["email"] = $user["email"];
        echo json_encode($response);
			if ($response){
				$message = "
						Hello $email,
						<br /><br />
						masukkan kode { $token } untu reset password  
						<br /><br />
						ingat jangan lupa lagi :D,";

			$subject = "reset password";
			$db->send_mail($email,$message,$subject);
			}
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "maaf email tidak di temukan ";
        echo json_encode($response);
    }
} else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Email Salah!";
    echo json_encode($response);
}
?>