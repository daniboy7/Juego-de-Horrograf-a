<?php
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

$descripcion = $_POST["descripcion"];
$valor_error = $_POST["valor_error"];
$valor_puntuacion = $_POST["valor_puntuacion"];
$errores_no_permitidos = $_POST["errores_no_permitidos"];
$minutos = $_POST["minutos"];
$numero_items = $_POST["numero_items"];

$statement = mysqli_prepare($con,"INSERT INTO etapas (descripcion, valor_error ,valor_puntuacion,errores_no_permitidos,minutos,numero_items) 
VALUES(?,?,?,?,?,?)");
mysqli_stmt_bind_param($statement,"sddiii",$descripcion,$valor_error,$valor_puntuacion,$errores_no_permitidos,$minutos,$numero_items);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);
?>