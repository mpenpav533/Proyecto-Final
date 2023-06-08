<?php
//INSERTAR USUARIO

require_once("basededatos.php");

$num_fase = $_REQUEST['num_partida'];
$id_partida = $_REQUEST['id_partida'];
$puntos = $_REQUEST['puntos'];

        //hacemos la  modificacion del campo de puntos
        $stmt = $mysql->query("UPDATE fase SET puntos = '$puntos' WHERE id_partida = $id_partida and num_fase = $num_fase");
        if ($stmt == null) {
            echo json_encode([
                "error" => "Error al modificar fase."
            ]);
        } else {
            echo json_encode([
                "insertado" => $puntos
            ]);
        }
    


