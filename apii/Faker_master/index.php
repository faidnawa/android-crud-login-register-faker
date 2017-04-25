  <?php

  require_once 'src/autoload.php';

$db = new mysqli('localhost','root','','latihancrud');

  $faker = Faker\Factory::create();


  for ($i=0; $i < 20; $i++) {

  $name = $faker->name(20);
  $nim = $faker->creditCardNumber;

    echo "<p>" . $faker->name . "</p>";
    echo "<p>" . $faker->creditCardNumber . "</p>";

  $db->QUERY("INSERT INTO tbl_mhas (nama, nim)
  			  VALUES ('{$name}',{$nim})

  	");

  }


  ?>
