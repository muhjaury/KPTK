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

	$auth = sha1($pname.$token.$unique.sha1($pname.$token.$unique));
	$cek = sha1($pname.$token.$unique.$cek);

	if ($cek == $auth) {
		$sth = mysqli_query($conn, "SELECT diklat.judul, history_diklat.certificate_url FROM diklat 
								INNER JOIN history_diklat ON diklat.id_diklat = history_diklat.f_id_diklat 
								INNER JOIN staff ON history_diklat.f_id_user = staff.idcard 
								WHERE staff.pname = '$pname' AND staff.token = '$token'");
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
