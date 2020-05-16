<?php
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

$texto_item = $_REQUEST["texto_item"];
$forma_correcta = $_REQUEST["forma_correcta"];
$pista = $_REQUEST["pista"];
$etapa = $_REQUEST["etapa"];
$nivel = $_REQUEST["nivel"];

$statement = mysqli_prepare($con,"INSERT INTO items (texto_item, forma_correcta,pista, etapa, nivel) VALUES(?,?,?,?,?)");
mysqli_stmt_bind_param($statement,"sssii",$texto_item, $forma_correcta, $pista , $etapa ,$nivel);
mysqli_stmt_execute($statement);

$response = array();
$response["success"] = true;

echo json_encode($response);
?>