<?php
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");
$sql = "select * from partidas order by puntuacion DESC limit 10";
$datos = Array();
$resul = mysqli_query($con , $sql);
while($row = mysqli_fetch_object($resul)){
	$datos[] = $row ;
}
echo json_encode($datos);
mysqli_close($con);

?>