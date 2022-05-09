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

	$generate = sha1($pname.$token.$unique);
	$auth = sha1($pname.$token.$unique.$generate);
	$cek = sha1($pname.$token.$unique.$cek);

	if($cek == $auth){
		$query = mysqli_query($conn, "SELECT nama, id FROM staff WHERE pname = '$pname' AND token = '$token' LIMIT 1;");
		$row = mysqli_fetch_array($query, MYSQLI_ASSOC);
	}
			
	$response = array();
	
	if (!empty($row)){
		$name = $row['nama'];
		$id = $row['id'];
		$query2 = mysqli_query($conn, "SELECT nama, id FROM staff WHERE nama NOT IN ('$name') AND id NOT IN ('$id');");
		while($r = mysqli_fetch_array($query2, MYSQLI_ASSOC)) {
			$response[] = $r;
		}
	} 
	
	
    echo json_encode($response);
	
?>
