
DROP DATABASE IF EXISTS clinica_estetica_rvr;
CREATE DATABASE clinica_estetica_rvr CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE clinica_estetica_rvr;

CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(150) NOT NULL
);

CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    nombre_completo VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('ACTIVO','INACTIVO') NOT NULL DEFAULT 'ACTIVO',
    CONSTRAINT fk_usuarios_roles
        FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);

CREATE TABLE pacientes (
    id_paciente INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL UNIQUE,
    fecha_nacimiento DATE,
    sexo ENUM('Mujer','Hombre','Otro') DEFAULT 'Otro',
    provincia VARCHAR(80),
    canton VARCHAR(80),
    direccion_detalle VARCHAR(180),
    estatura_cm DECIMAL(5,2),
    peso_kg DECIMAL(5,2),
    CONSTRAINT fk_pacientes_usuarios
        FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
);

CREATE TABLE historiales_medicos (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL UNIQUE,
    actividad_fisica VARCHAR(120),
    cuidado_piel VARCHAR(180),
    usa_cremas_comerciales BOOLEAN DEFAULT FALSE,
    consumo_agua_litros DECIMAL(4,2),
    evaluacion_facial VARCHAR(180),
    observaciones TEXT,
    fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_historial_paciente
        FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
);

CREATE TABLE categorias_tratamientos (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL UNIQUE,
    descripcion VARCHAR(180) NOT NULL
);

CREATE TABLE tratamientos (
    id_tratamiento INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT NOT NULL,
    nombre VARCHAR(120) NOT NULL UNIQUE,
    descripcion TEXT NOT NULL,
    precio_referencia DECIMAL(10,2) NOT NULL,
    duracion_minutos INT NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT fk_tratamientos_categoria
        FOREIGN KEY (id_categoria) REFERENCES categorias_tratamientos(id_categoria)
);

CREATE TABLE tratamiento_imagenes (
    id_imagen INT AUTO_INCREMENT PRIMARY KEY,
    id_tratamiento INT NOT NULL,
    titulo VARCHAR(120) NOT NULL,
    ruta_archivo VARCHAR(180) NOT NULL,
    orden_visual TINYINT NOT NULL DEFAULT 1,
    CONSTRAINT fk_imagenes_tratamiento
        FOREIGN KEY (id_tratamiento) REFERENCES tratamientos(id_tratamiento)
);

CREATE TABLE tratamiento_parametros (
    id_parametro INT AUTO_INCREMENT PRIMARY KEY,
    id_tratamiento INT NOT NULL,
    nombre_parametro VARCHAR(80) NOT NULL,
    valor_parametro VARCHAR(80) NOT NULL,
    unidad VARCHAR(30),
    CONSTRAINT fk_parametros_tratamiento
        FOREIGN KEY (id_tratamiento) REFERENCES tratamientos(id_tratamiento)
);

CREATE TABLE citas (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    id_paciente INT NOT NULL,
    id_tratamiento INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    estado ENUM('SOLICITADA','CONFIRMADA','ATENDIDA','CANCELADA') NOT NULL DEFAULT 'SOLICITADA',
    motivo VARCHAR(180),
    notas TEXT,
    fecha_creacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_citas_paciente
        FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente),
    CONSTRAINT fk_citas_tratamiento
        FOREIGN KEY (id_tratamiento) REFERENCES tratamientos(id_tratamiento)
);

CREATE TABLE mensajes_contacto (
    id_mensaje INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL,
    telefono VARCHAR(20),
    asunto VARCHAR(120) NOT NULL,
    mensaje TEXT NOT NULL,
    fecha_envio DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atendido BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE comentarios_tratamientos (
    id_comentario INT AUTO_INCREMENT PRIMARY KEY,
    id_tratamiento INT NOT NULL,
    id_paciente INT,
    calificacion TINYINT CHECK (calificacion BETWEEN 1 AND 5),
    comentario TEXT NOT NULL,
    fecha_comentario DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_comentarios_tratamiento
        FOREIGN KEY (id_tratamiento) REFERENCES tratamientos(id_tratamiento),
    CONSTRAINT fk_comentarios_paciente
        FOREIGN KEY (id_paciente) REFERENCES pacientes(id_paciente)
);

INSERT INTO roles (nombre, descripcion) VALUES
('ADMINISTRADOR', 'Gestiona el contenido y las citas de la clínica'),
('PACIENTE', 'Usuario final que solicita información y reserva tratamientos');

INSERT INTO usuarios (id_rol, nombre_completo, email, password_hash, telefono) VALUES
(1, 'Dr. Roberto Vargas Rojas', 'drrobertovargasrojas@gmail.com', SHA2('Admin1234',256), '8756-0542'),
(2, 'Ana Vargas', 'ana@gmail.com', SHA2('Paciente123',256), '8888-1111');

INSERT INTO pacientes (id_usuario, fecha_nacimiento, sexo, provincia, canton, direccion_detalle, estatura_cm, peso_kg) VALUES
(2, '1991-03-21', 'Mujer', 'Alajuela', 'Palmares', 'Palmares centro', 152.00, 53.00);

INSERT INTO historiales_medicos (id_paciente, actividad_fisica, cuidado_piel, usa_cremas_comerciales, consumo_agua_litros, evaluacion_facial, observaciones) VALUES
(1, 'Yoga 2 veces por semana', 'Uso diario de protector solar', TRUE, 1.50, 'Fototipo III, piel mixta, leve bronceado', 'Paciente apta para valoración estética inicial.');

INSERT INTO categorias_tratamientos (nombre, descripcion) VALUES
('Facial', 'Tratamientos enfocados en armonización, revitalización y glow facial'),
('Capilar', 'Procedimientos para fortalecer salud folicular y cuero cabelludo');

INSERT INTO tratamientos (id_categoria, nombre, descripcion, precio_referencia, duracion_minutos, activo) VALUES
(1, 'Botox', 'Aplicación de toxina botulínica para líneas de expresión.', 60.00, 30, TRUE),
(1, 'Mesoterapia Facial', 'Revitalización facial con principios activos.', 45000.00, 45, TRUE),
(2, 'Mesoterapia Capilar', 'Fortalecimiento del folículo y salud capilar.', 50000.00, 60, TRUE),
(1, 'Relleno de Labios', 'Armonización y volumen labial personalizado.', 75000.00, 50, TRUE),
(1, 'BB Lips', 'Pigmentación e hidratación labial estética.', 38000.00, 60, TRUE),
(1, 'Mesobótox', 'Combinación de glow, textura uniforme y suavizado.', 55000.00, 45, TRUE);

INSERT INTO tratamiento_imagenes (id_tratamiento, titulo, ruta_archivo, orden_visual) VALUES
(1, 'Botox principal', 'assets/img/treatments/botox.jpg', 1),
(2, 'Mesoterapia facial', 'assets/img/treatments/mesoterapia-facial.jpg', 1),
(3, 'Mesoterapia capilar', 'assets/img/treatments/mesoterapia-capilar.jpg', 1),
(4, 'Relleno de labios', 'assets/img/treatments/relleno-labios.jpg', 1),
(5, 'BB Lips', 'assets/img/treatments/bb-lips.jpg', 1),
(6, 'Mesobótox', 'assets/img/treatments/mesobotox.jpg', 1);

INSERT INTO tratamiento_parametros (id_tratamiento, nombre_parametro, valor_parametro, unidad) VALUES
(1, 'Duración del efecto', '2 a 4', 'meses'),
(1, 'Sesión', '1', 'aplicación'),
(2, 'Duración de sesión', '45', 'minutos'),
(3, 'Duración de sesión', '60', 'minutos'),
(4, 'Duración de sesión', '50', 'minutos');

INSERT INTO citas (id_paciente, id_tratamiento, fecha_hora, estado, motivo, notas) VALUES
(1, 2, '2026-03-20 10:00:00', 'SOLICITADA', 'Valoración inicial', 'Cita de prueba cargada desde el script SQL.');

INSERT INTO mensajes_contacto (nombre, email, telefono, asunto, mensaje) VALUES
('Ana Vargas', 'ana@gmail.com', '8888-1111', 'Consulta de botox', 'Deseo conocer disponibilidad para la próxima semana.');

INSERT INTO comentarios_tratamientos (id_tratamiento, id_paciente, calificacion, comentario) VALUES
(1, 1, 5, 'Muy buena orientación previa y seguimiento posterior.');
