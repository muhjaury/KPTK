<?php

$servername = "localhost";
$username = "root";
$password = '';
$dbname = "test4";
	
    $conn = new mysqli($servername, $username, $password, $dbname);
    
    $pname = $_POST["pname"];
	$token = $_POST["token"];
	$unique = $_POST["unique"];
	$cek = $_POST["cek"];

	$generate = sha1($pname.$token.$unique);
	$auth = sha1($pname.$token.$unique.$generate);
	$cek = sha1($pname.$token.$unique.$cek);

	if($cek == $auth){
		$query = mysqli_query($conn, "UPDATE staff SET pkey = null, pname = null, token = null WHERE pname = '$pname' AND token = '$token';");
		$response['stt'] = 0;
		$response['generate'] = $auth;
		$response['desc'] = "Logout Berhasil";
	}
	else{
		$response['stt'] = 1;
	}

	

	
	
    echo json_encode($response);
	
?>
