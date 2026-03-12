<?php
$host = "localhost";
$port = 1521;
$service_name = "ClinicaEsteticaRVR";
$user = "system";
$password = "12Ac45RJ%";

try {
    // Conexión
    $dsn = "oci:dbname=//$host:$port/$service_name;charset=AL32UTF8";
    $conexion = new PDO($dsn, $user, $password);
    $conexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    die("Error de conexión: " . $e->getMessage());
}
?>