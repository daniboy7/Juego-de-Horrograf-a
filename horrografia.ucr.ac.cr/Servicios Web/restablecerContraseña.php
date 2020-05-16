<?php
$email = $_REQUEST["email"];
$password = $_REQUEST["password"];

$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");
$sql = "UPDATE usuarios SET contraseña = '$password' WHERE correo = '$email'";
$resul = mysqli_query($con,$sql);
echo $resul;
mysqli_close($con);
?>