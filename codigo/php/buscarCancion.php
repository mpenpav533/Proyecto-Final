<?php
//BUSCAR cancion

require_once('basededatos.php');

$id_cancion = $_REQUEST['id_cancion'];
$query = "SELECT * FROM canciones WHERE id_cancion='$id_cancion'";
$result = $mysql->query($query);

if ($mysql->affected_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $array = $row;
    }
    echo json_encode($array);
} else {
    echo json_encode([
        "error" => "No hay datos en la consulta."
    ]);
}
$result->close();
$mysql->close();
