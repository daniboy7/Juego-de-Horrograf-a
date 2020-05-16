<?php
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

$username = $_POST["usuario"];
$password = $_POST["contraseña"];

$statement = mysqli_prepare($con,"SELECT * FROM usuarios WHERE usuario = ? AND contraseña = ?");
mysqli_stmt_bind_param($statement, "ss", $username , $password);
mysqli_stmt_execute($statement);

mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement,$idusuario,$usuario,$contraseña,$correo,$nombre,$apellidos,$edad,$tipo);

$response = array();
$response["success"] = false;

while(mysqli_stmt_fetch($statement)){
	$response["success"] = true;
	$response["usuario"] = $usuario;
	$response["contraseña"] = $contraseña;
	$response["correo"] = $correo;
	$response["nombre"] = $nombre;
	$response["apellidos"] = $apellidos;
	$response["edad"] = $edad;
	$response["tipo"] = $tipo;
}

echo json_encode($response);


?>