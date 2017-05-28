<?php

	$server		= "localhost"; //sesuaikan dengan nama server
	$user		= "id1567428_root"; //sesuaikan username
	$password	= "123456"; //sesuaikan password
	$database	= "id1567428_latihancrud"; //sesuaikan target databese

	$connect = mysql_connect($server, $user, $password) or die ("Koneksi gagal!");
	mysql_select_db($database) or die ("Database belum siap!");
?>