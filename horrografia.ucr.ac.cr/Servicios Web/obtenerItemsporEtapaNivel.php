<?php
$etapa = $_REQUEST["etapa"];
$nivel = $_REQUEST["nivel"];
$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");
$sql = "SELECT items.id_item AS id_item , items.texto_item AS texto_item, items.forma_correcta AS forma_correcta ,items.pista AS pista, items.nivel AS id_nivel, niveles.descripcion AS descripcion_nivel , items.etapa AS id_etapa, 
etapas.descripcion AS descripcion_etapa , etapas.valor_error AS valor_error , etapas.valor_puntuacion AS valor_puntuacion, etapas.errores_no_permitidos AS errores_no_permitidos, etapas.minutos AS minutos , etapas.numero_items AS numero_items
from items 
INNER JOIN niveles on items.nivel = niveles.id_nivel 
INNER JOIN etapas on items.etapa = etapas.id_etapa
where items.etapa = $etapa and items.nivel = $nivel";
$datos = Array();
$resul = mysqli_query($con , $sql);
while($row = mysqli_fetch_object($resul)){
	$datos[] = $row ;
}
echo json_encode($datos);
mysqli_close($con);

?>