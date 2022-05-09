<?php
	date_default_timezone_set("Asia/Makassar");
	$dateserver = date('Y-m-d');

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
	
	if($cek == $auth){
		if($date == "ongoing" || $date == "comingsoon"){	
			if ($date == "ongoing"){
				$sth = mysqli_query($conn, "SELECT diklat.judul, diklat.tempat, diklat.desc, diklat.tgl_mulai, diklat.tgl_akhir, diklat.organizer
											FROM diklat
											WHERE tgl_mulai <= '$dateserver' AND tgl_akhir >= '$dateserver';");
				$rows = array();
				while($r = mysqli_fetch_assoc($sth)) {
					$rows[] = $r;
				}
			}
			else if ($date == "comingsoon"){
				$sth = mysqli_query($conn, "SELECT diklat.judul, diklat.tempat, diklat.desc, diklat.tgl_mulai, diklat.tgl_akhir, diklat.organizer
											FROM diklat
											WHERE tgl_mulai > '$dateserver';");
				$rows = array();
				while($r = mysqli_fetch_assoc($sth)) {
					$rows[] = $r;
				}
			}
			else{
				$rows["stt"] = 1;
			}	
		}
		else{
			$sth = mysqli_query($conn, "SELECT diklat.judul, diklat.tempat, diklat.desc, diklat.tgl_mulai, diklat.tgl_akhir, diklat.organizer FROM diklat 
									INNER JOIN history_diklat ON diklat.id_diklat = history_diklat.f_id_diklat 
									INNER JOIN staff ON history_diklat.f_id_user = staff.idcard 
									WHERE staff.pname = '$pname' AND staff.token = '$token';");
			$rows = array();
			while($r = mysqli_fetch_assoc($sth)) {
				$rows[] = $r;
			}
		}
	}
	else{
			$rows["stt"] = 1;
	}
	
	print json_encode($rows);
	
?>
