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
		$query = mysqli_query($conn, "SELECT * FROM staff WHERE pname = '$pname' AND token = '$token' LIMIT 1;");
		$row = mysqli_fetch_array($query, MYSQLI_ASSOC);
	}
			
	$response = array();
	
	if (!empty($row)){		
		$response['stt'] = 0;
		$response['generate'] = $auth;
		$response['type'] = $row['type'];
	} else { 
		$response["stt"] = 1;
	}	
	
    echo json_encode($response);
	
?>
