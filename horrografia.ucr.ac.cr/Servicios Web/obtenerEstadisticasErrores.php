<?php

$con = mysqli_connect("localhost","horrografia.ucr.ac.cr","dBITSb4iQrpqFPQz","horrografia.ucr.ac.cr");

if($con)
{
    $sql = "SELECT niveles.descripcion AS descripcion_nivel , SUM(partidas.errores) AS total_errores FROM partidas INNER JOIN niveles ON partidas.nivel = niveles.id_nivel WHERE nivel = 1 UNION ALL SELECT niveles.descripcion AS descripcion_nivel , SUM(partidas.errores) AS total_errores FROM partidas INNER JOIN niveles ON partidas.nivel = niveles.id_nivel WHERE nivel = 2 UNION ALL SELECT niveles.descripcion AS descripcion_nivel , SUM(partidas.errores) AS total_errores FROM partidas INNER JOIN niveles ON partidas.nivel = niveles.id_nivel WHERE nivel = 3 UNION ALL 
SELECT niveles.descripcion AS descripcion_nivel , SUM(partidas.errores) AS total_errores FROM partidas INNER JOIN niveles ON partidas.nivel = niveles.id_nivel WHERE nivel = 4";

$result = mysqli_query($con,$sql);

$response = array();

while($row = mysqli_fetch_assoc($result))
{
	array_push($response,$row);
}

    echo json_encode($response);
}

mysqli_close($con);	



?>