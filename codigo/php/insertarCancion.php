<?php
//INSERTAR USUARIO

require_once("basededatos.php");

$username = $_REQUEST['username'];;
$nombre = $_POST['nombre'];
$edad = $_POST['edad'];

$mysql->begin_transaction();

$resultado = $mysql->query("SELECT EXISTS (SELECT * FROM usuario WHERE username='$username');");
$row = mysqli_fetch_row($resultado);
try {
    if ($row[0] == "1") {

        echo json_encode([
            "error" => "El usuario ya existe."
        ]);
    } else {
        $query = "INSERT INTO usuario (username, nombre, edad)
        VALUES ('$username','$nombre',$edad);";

        $result = $mysql->query($query);

        if ($result == null) {
            echo json_encode([
                "error" => "Error al insertar usuario."
            ]);
        } else {
            echo json_encode([
                "insertado" => $username
            ]);
        }
       
    }
    
    $mysql->commit();
    
} catch (Exception $ex) {
    //informcion del error
    echo "FALLO EN LA INSERCCION<br>";
    echo $ex->getMessage();
    $mysqlh->rollBack();
}


