<?php

$servername = "localhost";
$username = "root";
$password = '';
$dbname = "test4";
	
    $conn = new mysqli($servername, $username, $password, $dbname);
    
    $username = $_POST["username"];
    $pwd = $_POST["password"];
	$type = $_POST["type"];
	$token = $_POST["token"];
	$emanpxx = $_POST["emanp"];
	$cek = $_POST["cek"];
	
	$appsalt = "MjReaper";	
	
	$result_pkey = md5($token.$username); 
	$result_token = sha1($appsalt.$token.$result_pkey);
	
	$auth = sha1($appsalt.$token.$result_pkey.$result_token);
	$cek = sha1($appsalt.$token.$result_pkey.$cek);

	if($auth == $cek){
		$inpkey = mysqli_query($conn, "UPDATE staff SET pkey = '$result_pkey', token = '$auth', pname = '$emanpxx' WHERE id = '$username' AND password = encode('$pwd','bppmpv kptk') AND type = '$type';");
		$query = mysqli_query($conn, "SELECT * FROM staff WHERE id = '$username' AND password = encode('$pwd','bppmpv kptk') AND type = '$type' LIMIT 1;");
		$row = mysqli_fetch_array($query, MYSQLI_ASSOC);
	}
	
		
	$response = array();
	
	if (!empty($row)){
		
		$response['stt'] = 0;
		$response['name'] = $row['nama'];
		$response['type'] = $row['type'];
		$response['msg'] = $result_pkey;
		$response['token'] =  $auth;

	} else { 
		$response["stt"] = 1;
		$response['msg'] = "Username atau Password Salah, silahkan coba lagi !!! Created by KPTK 2019 | MjReaper";
		$response['token'] = $token;
	}	
	
    echo json_encode($response);
	
?>
