<?php
//BUSCAR fase

require_once('basededatos.php');

$id_partida = $_REQUEST['id_partida'];
$num_fase = $_REQUEST['num_partida'];
$query = "SELECT * FROM fase WHERE id_partida = $id_partida and num_fase = $num_fase";
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
