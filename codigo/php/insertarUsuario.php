<?php
//INSERTAR USUARIO

require_once("basededatos.php");

$username = $_REQUEST['username'];;
$nombre = $_REQUEST['nombre'];
$edad = $_REQUEST['edad'];
$rol = $_REQUEST['rol'];

$mysql->begin_transaction();

$resultado = $mysql->query("SELECT EXISTS (SELECT * FROM usuario WHERE username='$username');");
$row = mysqli_fetch_row($resultado);
try {
    if ($row[0] == "1") {

        echo json_encode([
            "error" => "El usuario ya existe."
        ]);
    } else {
        $query = "INSERT INTO usuario (username, nombre, edad, rol) VALUES ('$username','$nombre',$edad, '$rol');";

        $result = $mysql->query($query);

        if ($result == null) {
            echo json_encode([
                "error" => "Error al insertar usuario."
            ]);
        } else {
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
        }
    }

    $mysql->commit();
} catch (Exception $ex) {
    //informcion del error
    echo "FALLO EN LA INSERCCION<br>";
    echo $ex->getMessage();
    $mysqlh->rollBack();
}
