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
		$sth = mysqli_query($conn, "SELECT log_user_absent.time, log_user_absent.status, log_user_absent.iap_id_ap FROM log_user_absent
									INNER JOIN staff ON log_user_absent.f_id_user = staff.idcard  
									WHERE staff.pname = '$pname' AND staff.token = '$token' AND log_user_absent.date = '$date';");

        $result = array();
            
        while($row = mysqli_fetch_assoc($sth)){
            $temp = array();
            $temp['time'] = $row['time'];
            $temp['ap'] = $row['iap_id_ap'];
            array_push($result, $temp);
        }

	}
	else{
		$result["stt"] = 1;
	}
	
	print json_encode($result);
	
?>
