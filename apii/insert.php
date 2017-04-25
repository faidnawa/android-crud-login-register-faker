<?php
	include "koneksi.php";

	$nama 	= $_POST['nama'];
	$nim = $_POST['nim'];

	class emp{}

	if (empty($nama) || empty($nim)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Kolom isian tidak boleh kosong";
		die(json_encode($response));
	} else {
		$query = mysql_query("INSERT INTO tbl_mhas (id,nama,nim) VALUES(0,'".$nama."','".$nim."')");

		if ($query) {
			$response = new emp();
			$response->success = 1;
			$response->message = "Data berhasil di simpan";
			die(json_encode($response));
		} else{
			$response = new emp();
			$response->success = 0;
			$response->message = "Error simpan Data";
			die(json_encode($response));
		}
	}
?>
