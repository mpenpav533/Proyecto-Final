<?php
//INSERTAR PARTIDA

require_once("basededatos.php");

$id_usuario = $_REQUEST['id_usuario'];
$resultadoP = $_REQUEST['resultadoP'];


$mysql->begin_transaction();

$resultado = $mysql->query("SELECT EXISTS (SELECT * FROM partida WHERE id_usuario=$id_usuario);");
$row = mysqli_fetch_row($resultado);
try {
    if ($row[0] <= 0) {
        $query = "INSERT INTO partida (id_usuario, resultado, num_partida)
        VALUES ($id_usuario,'$resultadoP',1);";
        $re = $mysql->query($query);

        if ($re == null) {
            echo json_encode([
                "error" => "Error al crear la partida."
            ]);
        } else {
            $query = "SELECT * FROM partida WHERE id_usuario ='$id_usuario' AND num_partida = 1";
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
    } else {
        $num = 0;
        //comprobamos el numero mas alto
        $stmt = $mysql->query("SELECT MAX(num_partida) as max_num FROM partida where id_usuario=$id_usuario");

        $row = $stmt->fetch_assoc();
        $num = $row["max_num"];
        $num = $num + 1;

        //hacemos la  insercion de datos
        $stmt = $mysql->query("INSERT INTO partida ( id_usuario,resultado,num_partida) VALUES ($id_usuario,'$resultadoP',$num)");
        if ($stmt == null) {
            echo json_encode([
                "error" => "Error al insertar la partida."
            ]);
        } else {
            $query = "SELECT * FROM partida WHERE id_usuario ='$id_usuario' AND num_partida = $num";
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
