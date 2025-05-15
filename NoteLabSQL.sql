-- Crear tabla de usuarios (si aún no existe)
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL
);

-- Crear tabla de notas
CREATE TABLE IF NOT EXISTS notas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    contenido TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT
);


-- Crear tabla de hashtags
CREATE TABLE IF NOT EXISTS hashtags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla intermedia para la relación entre notas y hashtags
CREATE TABLE IF NOT EXISTS nota_hashtag (
    nota_id INT,
    hashtag_id INT,
    FOREIGN KEY (nota_id) REFERENCES notas(id),
    FOREIGN KEY (hashtag_id) REFERENCES hashtags(id),
    PRIMARY KEY (nota_id, hashtag_id)
);

-- Crear tabla de contraseñas guardadas
CREATE TABLE IF NOT EXISTS contrasenas_guardadas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sitio VARCHAR(255) NOT NULL,
    usuario VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);


ALTER TABLE notas ADD COLUMN fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
