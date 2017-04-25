<?php
	include "koneksi.php";

	$id 	= $_POST['id'];

	class emp{}

	if (empty($id)) {
		$response = new emp();
		$response->success = 0;
		$response->message = "Error Mengambil Data";
		die(json_encode($response));
	} else {
		$query 	= mysql_query("SELECT * FROM tbl_mhas WHERE id='".$id."'");
		$row 	= mysql_fetch_array($query);

		if (!empty($row)) {
			$response = new emp();
			$response->success = 1;
			$response->id = $row["id"];
			$response->nama = $row["nama"];
			$response->nim = $row["nim"];
			die(json_encode($response));
		} else{
			$response = new emp();
			$response->success = 0;
			$response->message = "Error Mengambil Data";
			die(json_encode($response));
		}
	}
?>
