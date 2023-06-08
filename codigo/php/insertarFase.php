<?php
//INSERTAR USUARIO

require_once("basededatos.php");

$id_cancion = $_REQUEST['id_cancion'];
$id_partida = $_REQUEST['id_partida'];
$puntos = $_REQUEST['puntos'];

$mysql->begin_transaction();

$resultado = $mysql->query("SELECT EXISTS (SELECT * FROM fase WHERE id_partida=$id_partida);");
$row = mysqli_fetch_row($resultado);
try {
    if ($row[0] <= 0) {

        $stmt = $mysql->query("INSERT INTO fase ( id_cancion,id_partida,puntos,num_fase) VALUES ($id_cancion,$id_partida,$puntos,1)");
        if ($stmt == null) {
            echo json_encode([
                "error" => "Error al crear la fase."
            ]);
        } else {
            echo json_encode([
                "insertado" => 1
            ]);
        }
    } else {
        $num = 0;
        //comprobamos el numero mas alto
        $stmt = $mysql->query("SELECT MAX(num_fase) as max_num FROM fase where id_partida=$id_partida");
        
        $row = $stmt->fetch_assoc();
        $num = $row["max_num"];
        $num = $num + 1 ;
        
        //hacemos la  insercion de datos
        $stmt = $mysql->query("INSERT INTO fase ( id_cancion,id_partida,puntos,num_fase) VALUES ($id_cancion,$id_partida,$puntos,$num)");
        if ($stmt == null) {
            echo json_encode([
                "error" => "Error al insertar fase."
            ]);
        } else {
            echo json_encode([
                "insertado" => $num
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


