<?php

//CONEXION A LA BASE DE DATOS
$mysql = new mysqli(
    "localhost",
    "prueba",
    "123456",
    "SongOlé"
);
if($mysql->connect_error){
    die(json_encode([
        "error" => "La conexion fallo".$mysql->connect_error
    ]));
}
