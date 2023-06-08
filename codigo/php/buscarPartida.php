<?php
//BUSCAR USUARIO

require_once('basededatos.php');

$usuario = $_REQUEST['id_usuario'];
$num_partida = $_REQUEST['num_partida'];

$query = "SELECT * FROM partida WHERE id_usuario ='$usuario' AND num_partida = $num_partida";
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
