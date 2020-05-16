<?php

$id_usuario = $_POST["id_usuario"];
$usuario = $_POST["usuario"];
$contraseña = $_POST["contraseña"];
$correo = $_POST["correo"];
$nombre = $_POST["nombre"];
$apellidos = $_POST["apellidos"];
$edad = $_POST["edad"];
$tipo = $_POST["tipo"];

$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

$statement = mysqli_prepare($con,"UPDATE usuarios SET usuario = ? ,contraseña = ?,correo = ?,nombre = ?,apellidos = ?,edad = ? WHERE  id_usuario = ? AND tipo = ? ");

mysqli_stmt_bind_param($statement,"sssssiis",$usuario,$contraseña,$correo,$nombre,$apellidos,$edad,$id_usuario,$tipo);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);
?>

