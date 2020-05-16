<?php
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

$usuario = $_POST["usuario"];
$contraseña = $_POST["contraseña"];
$correo = $_POST["correo"];
$nombre = $_POST["nombre"];
$apellidos = $_POST["apellidos"];
$edad = $_POST["edad"];
$tipo = $_POST["tipo"];
$statement = mysqli_prepare($con,"INSERT INTO usuarios (usuario,contraseña,correo,nombre,apellidos,edad,tipo) VALUES(?,?,?,?,?,?,?)");
mysqli_stmt_bind_param($statement,"sssssis",$usuario,$contraseña,$correo,$nombre,$apellidos,$edad,$tipo);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);
?>