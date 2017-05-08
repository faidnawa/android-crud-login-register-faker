<?php



require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['name']) && isset($_POST['email']) && isset($_POST['password'])) {

    // receiving the post params
    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
	$uuid = uniqid('', true);
	$code = md5(uniqid(rand()));

    // check if user is already existed with the same email
    if ($db->isUserExisted($email)) {
        // user already existed
        $response["error"] = TRUE;
        $response["error_msg"] = " silahkan cek email kamu dan login " . $email;
        echo json_encode($response);
    } else {
        // create a new user
        $user = $db->storeUser($name, $email, $password, $uuid, $code);
        if ($user) {
            // user stored successfully
			
            $response["error"] = FALSE;
            $response["uid"] = $user["unique_id"];
            $response["user"]["name"] = $user["name"];
            $response["user"]["email"] = $user["email"];
            $response["user"]["created_at"] = $user["created_at"];
            echo json_encode($response);
			if ($response){
				$message = "
						Hello $name,
						<br /><br />
						Welcome to Javice!<br/>
						email : $email <br>
						password : $password
						To complete your registration  please , just click following link<br/>
						<br /><br />
						<a href='http://localhost/apiii/apii/verify.php?code=$code'>Click HERE to Activate :)</a>
						<br /><br />
						Thanks,";

			$subject = "Confirm Registration";
				$db->send_mail($email,$message,$subject);
			}
			
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "terjadi erro periksa koneksi !";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}
?>
