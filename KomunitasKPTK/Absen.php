<?php
	date_default_timezone_set("Asia/Makassar");

	$servername = "localhost";
	$username = "root";
	$password = '';
	$dbname = "test4";
	
    $conn = new mysqli($servername, $username, $password, $dbname);
    
	$pname = $_POST["pname"];
	$bssid = $_POST["bssid"];
	$token = $_POST["token"];
	$unique = $_POST["unique"];
	$cek = $_POST["cek"];

	
	$generate = sha1($pname.$token.$unique.$bssid);
	$auth = sha1($pname.$token.$unique.$generate);
	$cek = sha1($pname.$token.$unique.$cek);

	if ($cek == $auth) {
		$query = mysqli_query($conn, "SELECT * FROM info_ap WHERE mac_ap = '$bssid' LIMIT 1;");
		$row = mysqli_fetch_array($query, MYSQLI_ASSOC);

		$response = array();

		if (!empty($row)){
			$response['stt'] = 0;
			$response['generate'] = $auth;
			date_default_timezone_set("Asia/Makassar");
			$date = date('Y-m-d');
			$time = date('H:i:s');
			$response['desc'] = "Absen Berhasil";
			$response['clock'] = $time;
			$response['date'] = $date;

			$query = mysqli_query($conn, "INSERT INTO log_absent (f_id_user, date, time, iap_id_ap)
										  SELECT u.idcard, '$date', '$time', (SELECT nama_ap FROM info_ap WHERE mac_ap = '$bssid')
										  FROM staff u
										  WHERE u.pname = '$pname'");
		}
		else { 
			$response["stt"] = 1;
		}	
	}
	else { 
		$response["stt"] = 1;
	}		
	
	
	
	
	
    echo json_encode($response);
	
?>
