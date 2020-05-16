<?php
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

$usuario = $_POST["usuario"];
$fecha = $_POST["fecha"];
$nivel = $_POST["nivel"];
$estado = $_POST["estado"];
$puntuacion = $_POST["puntuacion"];
$errores = $_POST["errores"];
$aciertos = $_POST["aciertos"];

$statement = mysqli_prepare($con,"INSERT INTO partidas(usuario,fecha,nivel,estado,puntuacion,errores,aciertos) VALUES(?,?,?,?,?,?,?)");
mysqli_stmt_bind_param($statement,"ssisdii",$usuario,$fecha,$nivel,$estado,$puntuacion,$errores,$aciertos);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);
?>