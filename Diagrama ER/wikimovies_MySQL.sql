USE Peliculas_TMDb;

-- Crear tabla Etiquetas
CREATE TABLE `Etiquetas` (
    `id_etiqueta` INT AUTO_INCREMENT NOT NULL,
    `nombre` VARCHAR(50),
    PRIMARY KEY (`id_etiqueta`),
    UNIQUE KEY `nombre_UNIQUE` (`nombre`)
);

-- Crear tabla Favoritos
CREATE TABLE `Favoritos` (
    `id_usuario` INT NOT NULL,
    `id_pelicula` INT NOT NULL,
    PRIMARY KEY (`id_usuario`, `id_pelicula`),
    CONSTRAINT `FK_Favoritos_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `Peliculas` (`id_pelicula`),
    CONSTRAINT `FK_Favoritos_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`)
);

-- Crear tabla Generos
CREATE TABLE `Generos` (
    `id_genero` INT AUTO_INCREMENT NOT NULL,
    `nombre` VARCHAR(100),
    PRIMARY KEY (`id_genero`)
);

-- Crear tabla Idiomas
CREATE TABLE `Idiomas` (
    `id_idioma` VARCHAR(2) NOT NULL,
    `nombre` VARCHAR(100),
    PRIMARY KEY (`id_idioma`)
);

-- Crear tabla Lista
CREATE TABLE `Lista` (
    `id_lista` INT AUTO_INCREMENT NOT NULL,
    `id_usuario` INT,
    `id_pelicula` INT,
    `nombre` VARCHAR(100),
    PRIMARY KEY (`id_lista`),
    CONSTRAINT `FK_Lista_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `Peliculas` (`id_pelicula`),
    CONSTRAINT `FK_Lista_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`)
);

-- Crear tabla MiembroReparto
CREATE TABLE `MiembroReparto` (
    `id_personaje` INT,
    `personaje` VARCHAR(50),
    `id_credencial` VARCHAR(50),
    `genero` INT,
    `id_miembro` INT NOT NULL,
    `nombre` VARCHAR(50),
    `orden` INT,
    PRIMARY KEY (`id_miembro`)
);

-- Crear tabla PaisProduccion
CREATE TABLE `PaisProduccion` (
    `id_pais` VARCHAR(2) NOT NULL,
    `nombre` VARCHAR(100),
    PRIMARY KEY (`id_pais`)
);

-- Crear tabla Pelicula_Etiqueta
CREATE TABLE `Pelicula_Etiqueta` (
    `id_pelicula` INT NOT NULL,
    `id_etiqueta` INT NOT NULL,
    PRIMARY KEY (`id_pelicula`, `id_etiqueta`),
    CONSTRAINT `FK_Pelicula_Etiqueta` FOREIGN KEY (`id_etiqueta`) REFERENCES `Etiquetas` (`id_etiqueta`)
);

-- Crear tabla Pelicula_Genero
CREATE TABLE `Pelicula_Genero` (
    `id_pelicula` INT NOT NULL,
    `id_genero` INT NOT NULL,
    PRIMARY KEY (`id_pelicula`, `id_genero`),
    CONSTRAINT `FK_Pelicula_Genero` FOREIGN KEY (`id_genero`) REFERENCES `Generos` (`id_genero`)
);

-- Crear tabla Pelicula_Idioma
CREATE TABLE `Pelicula_Idioma` (
    `id_pelicula` INT NOT NULL,
    `id_idioma` VARCHAR(2) NOT NULL,
    PRIMARY KEY (`id_pelicula`, `id_idioma`),
    CONSTRAINT `FK_Pelicula_Idioma` FOREIGN KEY (`id_idioma`) REFERENCES `Idiomas` (`id_idioma`)
);

-- Crear tabla Pelicula_MiembroReparto
CREATE TABLE `Pelicula_MiembroReparto` (
    `id_pelicula` INT NOT NULL,
    `id_miembro` INT NOT NULL,
    PRIMARY KEY (`id_pelicula`, `id_miembro`),
    CONSTRAINT `FK_Pelicula_MiembroReparto` FOREIGN KEY (`id_miembro`) REFERENCES `MiembroReparto` (`id_miembro`),
    CONSTRAINT `FK_Pelicula_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `Peliculas` (`id_pelicula`)
);

-- Crear tabla Pelicula_PaisProduccion
CREATE TABLE `Pelicula_PaisProduccion` (
    `id_pelicula` INT NOT NULL,
    `id_pais` VARCHAR(2) NOT NULL,
    PRIMARY KEY (`id_pelicula`, `id_pais`),
    CONSTRAINT `FK_Pelicula_PaisProduccion` FOREIGN KEY (`id_pais`) REFERENCES `PaisProduccion` (`id_pais`)
);

-- Crear tabla Pelicula_Personal
CREATE TABLE `Pelicula_Personal` (
    `id_pelicula` INT NOT NULL,
    `id_personal` INT NOT NULL,
    PRIMARY KEY (`id_pelicula`, `id_personal`),
    CONSTRAINT `FK_Pelicula_Personal` FOREIGN KEY (`id_personal`) REFERENCES `Personal` (`id_personal`),
    CONSTRAINT `FK_Pelicula_Pelicula_Personal` FOREIGN KEY (`id_pelicula`) REFERENCES `Peliculas` (`id_pelicula`)
);

-- Crear tabla Pelicula_Productora
CREATE TABLE `Pelicula_Productora` (
    `id_pelicula` INT NOT NULL,
    `id_productora` INT NOT NULL,
    PRIMARY KEY (`id_pelicula`, `id_productora`),
    CONSTRAINT `FK_Pelicula_Productora` FOREIGN KEY (`id_productora`) REFERENCES `Productoras` (`id_productora`),
    CONSTRAINT `FK_Pelicula_Pelicula_Productora` FOREIGN KEY (`id_pelicula`) REFERENCES `Peliculas` (`id_pelicula`)
);

-- Crear tabla Peliculas
CREATE TABLE `Peliculas` (
    `id_pelicula` INT AUTO_INCREMENT NOT NULL,
    `titulo` VARCHAR(255),
    `fecha_estreno` DATE,
    `presupuesto` BIGINT,
    `ingresos` BIGINT,
    `duracion` INT,
    `descripcion` TEXT,
    `enlace` VARCHAR(200),
    `idioma_original` VARCHAR(2),
    `popularidad` FLOAT,
    `estatus` VARCHAR(50),
    `eslogan` VARCHAR(100),
    `titulo_original` VARCHAR(255),
    `media_votos`_
