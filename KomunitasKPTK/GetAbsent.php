<?php

$servername = "localhost";
$username = "root";
$password = '';
$dbname = "test4";
	
    $conn = new mysqli($servername, $username, $password, $dbname);
    
	$pname = $_GET["pname"];
	$token = $_GET["token"];
	$unique = $_GET["unique"];
	$cek = $_GET["cek"];
	$date = $_GET["date"];

	$auth = sha1($pname.$token.$unique.sha1($pname.$token.$unique));
	$cek = sha1($pname.$token.$unique.$cek);

	if ($cek == $auth) {
		$sth = mysqli_query($conn, "SELECT MIN(log_absent.time) as min, MAX(log_absent.time) as max FROM log_absent 
									INNER JOIN staff ON log_absent.f_id_user = staff.idcard 
									WHERE staff.pname = '$pname' AND staff.token = '$token' AND log_absent.date = '$date'
									GROUP BY log_absent.f_id_user ;");
		$rows = array();
		while($r = mysqli_fetch_assoc($sth)) {
			$rows[] = $r;
		}
	}
	else{
		$rows["stt"] = 1;
	}
	
	print json_encode($rows);
	
?>
