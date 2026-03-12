<?php
header('Content-Type: application/json; charset=utf-8');
require_once __DIR__ . '/../config/database.php';

if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    echo json_encode(['ok' => false, 'mensaje' => 'Método no permitido']);
    exit;
}

$paciente = trim($_POST['paciente'] ?? '');
$correo = trim($_POST['correo'] ?? '');
$tratamiento = trim($_POST['tratamiento'] ?? '');
$fechaDeseada = trim($_POST['fecha_deseada'] ?? '');
$notas = trim($_POST['notas'] ?? '');

if ($paciente === '' || $correo === '' || $tratamiento === '') {
    echo json_encode(['ok' => false, 'mensaje' => 'Complete los campos obligatorios']);
    exit;
}

$stmtTratamiento = $conexion->prepare("SELECT id_tratamiento FROM tratamientos WHERE nombre = ?");
$stmtTratamiento->execute([$tratamiento]);
$idTratamiento = $stmtTratamiento->fetchColumn();

if (!$idTratamiento) {
    echo json_encode(['ok' => false, 'mensaje' => 'Tratamiento no encontrado']);
    exit;
}

$stmtUsuario = $conexion->prepare("SELECT p.id_paciente
    FROM pacientes p
    INNER JOIN usuarios u ON u.id_usuario = p.id_usuario
    WHERE u.email = ?");
$stmtUsuario->execute([$correo]);
$idPaciente = $stmtUsuario->fetchColumn();

if (!$idPaciente) {
    echo json_encode(['ok' => false, 'mensaje' => 'No existe un paciente vinculado a ese correo']);
    exit;
}

$fechaHora = $fechaDeseada !== '' ? $fechaDeseada . ' 10:00:00' : date('Y-m-d H:i:s');

$sql = "INSERT INTO citas (id_paciente, id_tratamiento, fecha_hora, estado, motivo, notas)
        VALUES (?, ?, ?, 'SOLICITADA', 'Solicitud web', ?)";
$stmt = $conexion->prepare($sql);
$stmt->execute([$idPaciente, $idTratamiento, $fechaHora, $notas]);

echo json_encode(['ok' => true, 'mensaje' => 'Solicitud de cita registrada']);
?>