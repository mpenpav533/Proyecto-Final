<?php
//BUSCAR USUARIO

require_once('basededatos.php');

$id_usuario = $_REQUEST['id_usuario'];
$query = "SELECT * FROM partida WHERE id_usuario='$id_usuario'";
$result = $mysql->query($query);

if ($mysql->affected_rows > 0) {
    
    echo json_encode($result->fetch_all(MYSQLI_ASSOC));
} else {
    echo json_encode([
        "error" => "No hay datos en la consulta."
    ]);
}
$result->close();
$mysql->close();
