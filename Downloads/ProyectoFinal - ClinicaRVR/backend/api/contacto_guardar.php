<?php
header('Content-Type: application/json; charset=utf-8');
require_once __DIR__ . '/../config/database.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    echo json_encode(['ok' => false, 'mensaje' => 'Método no permitido']);
    exit;
}

$nombre = trim($_POST['nombre'] ?? '');
$email = trim($_POST['email'] ?? '');
$telefono = trim($_POST['telefono'] ?? '');
$asunto = trim($_POST['asunto'] ?? '');
$mensaje = trim($_POST['mensaje'] ?? '');

if ($nombre === '' || $email === '' || $asunto === '' || $mensaje === '') {
    echo json_encode(['ok' => false, 'mensaje' => 'Complete los campos obligatorios']);
    exit;
}

$sql = "INSERT INTO mensajes_contacto (nombre, email, telefono, asunto, mensaje) VALUES (?, ?, ?, ?, ?)";
$stmt = $conexion->prepare($sql);
$stmt->execute([$nombre, $email, $telefono, $asunto, $mensaje]);

echo json_encode(['ok' => true, 'mensaje' => 'Mensaje almacenado correctamente']);
?>