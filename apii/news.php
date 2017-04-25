<?php
	include "koneksi.php";
	sleep(2);

	$offset = isset($_GET['offset']) && $_GET['offset'] != '' ? $_GET['offset'] : 0;

	$all = mysql_query("SELECT * FROM tbl_mhas ORDER BY id DESC");
	$count_all = mysql_num_rows($all);

	$query = mysql_query("SELECT * FROM tbl_mhas ORDER BY id DESC LIMIT $offset,10");

	$count = mysql_num_rows($query);
	$json_kosong = 0;

	if($count<10){
		if($count==0){
			$json_kosong = 1;
		}else{
			$query = mysql_query("SELECT * FROM tbl_mhas ORDER BY id DESC LIMIT $offset,$count");
			$count = mysql_num_rows($query);
			if(empty($count)){
				$query = mysql_query("SELECT * FROM tbl_mhas ORDER BY id DESC LIMIT 0,10");
				$num = 0;
			}else{
				$num = $offset;
			}
		}
	} else{
		$num = $offset;
	}

	$json = '[';

	while ($row = mysql_fetch_array($query)){
		$num++;
		$char ='"';
		$json .= '{
			"no": '.$num.',
			"id": "'.str_replace($char,'`',strip_tags($row['id'])).'",
			"nama": "'.str_replace($char,'`',strip_tags($row['nama'])).'",
			"nim": "'.str_replace($char,'`',strip_tags($row['nim'])).'"},';
	}

	$json = substr($json,0,strlen($json)-1);


	if($json_kosong==1){
		$json = '[{ "no": "", "id": "", "name": "", "nim": ""}]';
	}else{
		$json .= ']';
	}
	echo $json;

	mysql_close($connect);
?>
