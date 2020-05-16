<?php
$id_usuario = $_REQUEST["id_usuario"];
$tipo = $_REQUEST["tipo"];
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");
$sql = "select * from usuarios where id_usuario = $id_usuario and tipo = '$tipo'";
$datos = Array();
$resul = mysqli_query($con,$sql);
while($row = mysqli_fetch_object($resul)){
	$datos[] = $row;
}

echo json_encode($datos);
mysqli_close($con);
?>