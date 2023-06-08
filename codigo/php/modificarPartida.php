<?php
//INSERTAR PARTIDA

require_once("basededatos.php");

$id_usuario = $_REQUEST['id_usuario'];
$num_partida = $_REQUEST['num_partida'];
$resultado = $_REQUEST['resultado'];
     
        //hacemos la modificacion del campo resultado
        $stmt = $mysql->query("UPDATE partida SET resultado = '$resultado' WHERE id_usuario = $id_usuario and num_partida = $num_partida");
        if ($stmt == null) {
            echo json_encode([
                "error" => "Error al modificar la partida."
            ]);
        } else {
            echo json_encode([
                "insertado" => "resultado"
            ]);
        }
    
