<?php
//BUSCAR USUARIO

require_once('basededatos.php');

$username = $_REQUEST['username'];
$query = "SELECT * FROM usuario WHERE username='$username'";
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
