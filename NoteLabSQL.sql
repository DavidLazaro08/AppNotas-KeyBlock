create database notelab if not exists;
USE notelab;

-- Crear tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- Crear tabla de notas
CREATE TABLE notas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    contenido TEXT,
    fecha_creacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Crear tabla de hashtags
CREATE TABLE hashtags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hashtag VARCHAR(50)
);

-- Crear tabla intermedia nota_hashtag
CREATE TABLE nota_hashtag (
    nota_id INT,
    hashtag_id INT,
    FOREIGN KEY (nota_id) REFERENCES notas(id),
    FOREIGN KEY (hashtag_id) REFERENCES hashtags(id),
    PRIMARY KEY (nota_id, hashtag_id)
);

-- Crear tabla de contraseñas_guardadas
CREATE TABLE contrasenas_guardadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    contrasena VARCHAR(255),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);
