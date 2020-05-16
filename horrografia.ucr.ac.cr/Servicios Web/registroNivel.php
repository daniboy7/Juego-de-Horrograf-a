<?php
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

$descripcion = $_POST["descripcion"];

$statement = mysqli_prepare($con,"INSERT INTO niveles (descripcion) VALUES(?)");
mysqli_stmt_bind_param($statement,"s",$descripcion);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);
?>