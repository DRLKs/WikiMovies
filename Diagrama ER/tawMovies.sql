-- Crear la base de datos
CREATE DATABASE Peliculas_TMDb;
GO
USE Peliculas_TMDb;
GO

-- Tabla de Películas
CREATE TABLE Peliculas (
    id_pelicula INT PRIMARY KEY IDENTITY(1,1),
    titulo VARCHAR(255),
    fecha_estreno DATE,
    presupuesto BIGINT,
    ingresos BIGINT,
    duracion INT,
    descripcion TEXT
);

-- Tabla de Géneros
CREATE TABLE Generos (
    id_genero INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100)
);

-- Relación Películas - Géneros (Muchos a Muchos)
CREATE TABLE Pelicula_Genero (
    id_pelicula INT,
    id_genero INT,
    PRIMARY KEY (id_pelicula, id_genero),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula),
    FOREIGN KEY (id_genero) REFERENCES Generos(id_genero)
);

-- Tabla de Productoras
CREATE TABLE Productoras (
    id_productora INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(255)
);

-- Relación Películas - Productoras (Muchos a Muchos)
CREATE TABLE Pelicula_Productora (
    id_pelicula INT,
    id_productora INT,
    PRIMARY KEY (id_pelicula, id_productora),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula),
    FOREIGN KEY (id_productora) REFERENCES Productoras(id_productora)
);

-- Tabla de Idiomas
CREATE TABLE Idiomas (
    id_idioma INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(100)
);

-- Relación Películas - Idiomas (Muchos a Muchos)
CREATE TABLE Pelicula_Idioma (
    id_pelicula INT,
    id_idioma INT,
    PRIMARY KEY (id_pelicula, id_idioma),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula),
    FOREIGN KEY (id_idioma) REFERENCES Idiomas(id_idioma)
);

-- Tabla de Actores
CREATE TABLE Actores (
    id_actor INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(255)
);

-- Relación Películas - Actores (Muchos a Muchos)
CREATE TABLE Pelicula_Actor (
    id_pelicula INT,
    id_actor INT,
    PRIMARY KEY (id_pelicula, id_actor),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula),
    FOREIGN KEY (id_actor) REFERENCES Actores(id_actor)
);

-- Tabla de Directores
CREATE TABLE Directores (
    id_director INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(255)
);

-- Relación Películas - Directores (Muchos a Muchos)
CREATE TABLE Pelicula_Director (
    id_pelicula INT,
    id_director INT,
    PRIMARY KEY (id_pelicula, id_director),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula),
    FOREIGN KEY (id_director) REFERENCES Directores(id_director)
);

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    id_usuario INT PRIMARY KEY IDENTITY(1,1),
    nombre_usuario VARCHAR(100) UNIQUE,
    correo_electronico VARCHAR(255) UNIQUE,
    contrasena_hash VARCHAR(255),
    rol VARCHAR(10) CHECK (rol IN ('admin', 'usuario')) DEFAULT 'usuario'
);

-- Tabla de Reseñas / Comentarios
CREATE TABLE Reseñas (
    id_reseña INT PRIMARY KEY IDENTITY(1,1),
    id_usuario INT,
    id_pelicula INT,
    calificacion DECIMAL(3,1) CHECK (calificacion BETWEEN 0 AND 10),
    comentario TEXT,
    fecha_creacion DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula)
);

-- Tabla de Favoritos
CREATE TABLE Favoritos (
    id_usuario INT,
    id_pelicula INT,
    PRIMARY KEY (id_usuario, id_pelicula),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula)
);

-- Tabla de Historial de Visualización
CREATE TABLE Historial_Visualizacion (
    id_historial INT PRIMARY KEY IDENTITY(1,1),
    id_usuario INT,
    id_pelicula INT,
    fecha_vista DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula)
);

-- Tabla de Etiquetas (Tags)
CREATE TABLE Etiquetas (
    id_etiqueta INT PRIMARY KEY IDENTITY(1,1),
    nombre VARCHAR(50) UNIQUE
);

-- Relación Películas - Etiquetas (Muchos a Muchos)
CREATE TABLE Pelicula_Etiqueta (
    id_pelicula INT,
    id_etiqueta INT,
    PRIMARY KEY (id_pelicula, id_etiqueta),
    FOREIGN KEY (id_pelicula) REFERENCES Peliculas(id_pelicula),
    FOREIGN KEY (id_etiqueta) REFERENCES Etiquetas(id_etiqueta)
);
