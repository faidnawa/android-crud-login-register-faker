<?php
 function get_token($panjang){
  $token = array(
   range(1,9),
   range('a','z'),
   range('A','Z')
  );

  $karakter = array();
  foreach($token as $key=>$val){
   foreach($val as $k=>$v){
    $karakter[] = $v;
   }
  }

  $token = null;
  for($i=1; $i<=$panjang; $i++){
   // mengambil array secara acak
   $token .= $karakter[rand($i, count($karakter) - 1)];
  }

  return $token;
 }


