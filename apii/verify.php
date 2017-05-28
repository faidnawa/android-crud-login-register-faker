<?php
	include "koneksi.php";

	if(empty($_GET['code']))
	{
header("Location: /");
	}
$statusY = "Y";
$statusN = "N";
$a= $_GET["code"];
	$tampil= ("SELECT userStatus, token FROM users where token = '".$a."'");
	$sql = mysql_query($tampil);
	while($data = mysql_fetch_array($sql))
	
		if($data['userStatus']==$statusN)
		{
		$query = mysql_query("UPDATE users SET userStatus= '".$statusY."' where token = '".$a."'");
		echo "selamat anda sudah ter aktifasi silahkan login";

		}
		else
		{
			echo "akun anda sudah aktif silahkan login";
		}