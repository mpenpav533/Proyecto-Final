<?php
//INSERTAR USUARIO

    require_once("basededatos.php");
    
    $username = $_REQUEST['username'];
    //$puntos = $_POST['puntos'];
    //$pJugadas = $_POST['pJugadas'];
    //$pGanadas = $_POST['pGanadas'];
    //$pPerdidas = $_POST['pPerdidas'];

    $query = "INSERT INTO usuario (username, puntos, pJugadas, pGanadas, pPerdidas)
            VALUES ('$username',0,0,0,0);";

    $result = $mysql->query($query);

    if($result == null){
        echo json_encode([
        "error" => "Error al insertar usuario."
    ]);
    }
    else{
        echo json_encode([
        "error" => "El usuario $username fue registrado en la base de datos."
    ]);
        
    }
    $result->close();
    $mysql->close();

?>