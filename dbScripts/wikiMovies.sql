-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: peliculas_tmdb
-- ------------------------------------------------------
-- Server version	8.0.41

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `crew`
--

DROP TABLE IF EXISTS `crew`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crew` (
  `id_crew` int NOT NULL AUTO_INCREMENT,
  `id_pelicula` int NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `genero` int DEFAULT NULL,
  `id_credencial` varchar(50) NOT NULL,
  PRIMARY KEY (`id_crew`),
  KEY `FK_Crew_Pelicula` (`id_pelicula`),
  CONSTRAINT `FK_Crew_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crew`
--

LOCK TABLES `crew` WRITE;
/*!40000 ALTER TABLE `crew` DISABLE KEYS */;
/*!40000 ALTER TABLE `crew` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crew_personaje`
--

DROP TABLE IF EXISTS `crew_personaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crew_personaje` (
  `Crew_id_crew` int NOT NULL,
  `Personaje_id_miembro` int NOT NULL,
  PRIMARY KEY (`Crew_id_crew`,`Personaje_id_miembro`),
  KEY `fk_Crew_has_Personaje_Personaje1_idx` (`Personaje_id_miembro`),
  KEY `fk_Crew_has_Personaje_Crew1_idx` (`Crew_id_crew`),
  CONSTRAINT `fk_Crew_has_Personaje_Crew1` FOREIGN KEY (`Crew_id_crew`) REFERENCES `crew` (`id_crew`),
  CONSTRAINT `fk_Crew_has_Personaje_Personaje1` FOREIGN KEY (`Personaje_id_miembro`) REFERENCES `personaje` (`id_miembro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crew_personaje`
--

LOCK TABLES `crew_personaje` WRITE;
/*!40000 ALTER TABLE `crew_personaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `crew_personaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crew_trabajo`
--

DROP TABLE IF EXISTS `crew_trabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crew_trabajo` (
  `Crew_id_crew` int NOT NULL,
  `Trabajo_id_trabajo` int NOT NULL,
  PRIMARY KEY (`Crew_id_crew`,`Trabajo_id_trabajo`),
  KEY `fk_Crew_has_Trabajo_Trabajo1_idx` (`Trabajo_id_trabajo`),
  KEY `fk_Crew_has_Trabajo_Crew1_idx` (`Crew_id_crew`),
  CONSTRAINT `fk_Crew_has_Trabajo_Crew1` FOREIGN KEY (`Crew_id_crew`) REFERENCES `crew` (`id_crew`),
  CONSTRAINT `fk_Crew_has_Trabajo_Trabajo1` FOREIGN KEY (`Trabajo_id_trabajo`) REFERENCES `trabajo` (`id_trabajo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crew_trabajo`
--

LOCK TABLES `crew_trabajo` WRITE;
/*!40000 ALTER TABLE `crew_trabajo` DISABLE KEYS */;
/*!40000 ALTER TABLE `crew_trabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `etiquetas`
--

DROP TABLE IF EXISTS `etiquetas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `etiquetas` (
  `id_etiqueta` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id_etiqueta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `etiquetas`
--

LOCK TABLES `etiquetas` WRITE;
/*!40000 ALTER TABLE `etiquetas` DISABLE KEYS */;
/*!40000 ALTER TABLE `etiquetas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favoritos`
--

DROP TABLE IF EXISTS `favoritos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favoritos` (
  `id_usuario` int NOT NULL,
  `id_pelicula` int NOT NULL,
  PRIMARY KEY (`id_usuario`,`id_pelicula`),
  KEY `FK_Favoritos_Pelicula` (`id_pelicula`),
  CONSTRAINT `FK_Favoritos_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`),
  CONSTRAINT `FK_Favoritos_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favoritos`
--

LOCK TABLES `favoritos` WRITE;
/*!40000 ALTER TABLE `favoritos` DISABLE KEYS */;
/*!40000 ALTER TABLE `favoritos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `generos`
--

DROP TABLE IF EXISTS `generos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `generos` (
  `id_genero` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_genero`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generos`
--

LOCK TABLES `generos` WRITE;
/*!40000 ALTER TABLE `generos` DISABLE KEYS */;
INSERT INTO `generos` VALUES (1,'Terror'),(2,'Comedia'),(3,'Románticas'),(4,'Acción'),(5,'Super Heroes'),(6,'Drama'),(7,'Intriga'),(8,'Policiacas');
/*!40000 ALTER TABLE `generos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `idiomas`
--

DROP TABLE IF EXISTS `idiomas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `idiomas` (
  `id_idioma` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_idioma`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `idiomas`
--

LOCK TABLES `idiomas` WRITE;
/*!40000 ALTER TABLE `idiomas` DISABLE KEYS */;
INSERT INTO `idiomas` VALUES (1,'English'),(2,'Español'),(3,'Français'),(4,'Italiano'),(5,'Deutsch'),(6,'日本語'),(7,'한국어'),(8,'Português');
/*!40000 ALTER TABLE `idiomas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lista`
--

DROP TABLE IF EXISTS `lista`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista` (
  `id_lista` int NOT NULL AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id_lista`),
  KEY `FK_Lista_Usuario` (`id_usuario`),
  CONSTRAINT `FK_Lista_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista`
--

LOCK TABLES `lista` WRITE;
/*!40000 ALTER TABLE `lista` DISABLE KEYS */;
/*!40000 ALTER TABLE `lista` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lista_peliculas`
--

DROP TABLE IF EXISTS `lista_peliculas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista_peliculas` (
  `id_lista` int NOT NULL,
  `id_pelicula` int NOT NULL,
  PRIMARY KEY (`id_lista`,`id_pelicula`),
  KEY `FK_Pelicula_idx` (`id_pelicula`),
  CONSTRAINT `FK_lista` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id_lista`),
  CONSTRAINT `FK_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_peliculas`
--

LOCK TABLES `lista_peliculas` WRITE;
/*!40000 ALTER TABLE `lista_peliculas` DISABLE KEYS */;
/*!40000 ALTER TABLE `lista_peliculas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paisproduccion`
--

DROP TABLE IF EXISTS `paisproduccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paisproduccion` (
  `id_pais` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`id_pais`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paisproduccion`
--

LOCK TABLES `paisproduccion` WRITE;
/*!40000 ALTER TABLE `paisproduccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `paisproduccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula_etiqueta`
--

DROP TABLE IF EXISTS `pelicula_etiqueta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula_etiqueta` (
  `id_pelicula` int NOT NULL,
  `id_etiqueta` int NOT NULL,
  PRIMARY KEY (`id_pelicula`,`id_etiqueta`),
  KEY `FK_Pelicula_Etiqueta_Etiquetas` (`id_etiqueta`),
  CONSTRAINT `FK_Pelicula_Etiqueta_Etiquetas` FOREIGN KEY (`id_etiqueta`) REFERENCES `etiquetas` (`id_etiqueta`) ON DELETE CASCADE,
  CONSTRAINT `FK_Pelicula_Etiqueta_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula_etiqueta`
--

LOCK TABLES `pelicula_etiqueta` WRITE;
/*!40000 ALTER TABLE `pelicula_etiqueta` DISABLE KEYS */;
/*!40000 ALTER TABLE `pelicula_etiqueta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula_genero`
--

DROP TABLE IF EXISTS `pelicula_genero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula_genero` (
  `id_pelicula` int NOT NULL,
  `id_genero` int NOT NULL,
  PRIMARY KEY (`id_pelicula`,`id_genero`),
  KEY `FK_Pelicula_Genero_Generos` (`id_genero`),
  CONSTRAINT `FK_Pelicula_Genero_Generos` FOREIGN KEY (`id_genero`) REFERENCES `generos` (`id_genero`),
  CONSTRAINT `FK_Pelicula_Genero_Peliculas` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula_genero`
--

LOCK TABLES `pelicula_genero` WRITE;
/*!40000 ALTER TABLE `pelicula_genero` DISABLE KEYS */;
INSERT INTO `pelicula_genero` VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `pelicula_genero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula_idioma`
--

DROP TABLE IF EXISTS `pelicula_idioma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula_idioma` (
  `id_pelicula` int NOT NULL,
  `id_idioma` int NOT NULL,
  PRIMARY KEY (`id_pelicula`,`id_idioma`),
  KEY `FK_Pelicula_Idioma_Idiomas` (`id_idioma`),
  CONSTRAINT `FK_Pelicula_Idioma_Idiomas` FOREIGN KEY (`id_idioma`) REFERENCES `idiomas` (`id_idioma`),
  CONSTRAINT `FK_Pelicula_Idioma_Peliculas` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula_idioma`
--

LOCK TABLES `pelicula_idioma` WRITE;
/*!40000 ALTER TABLE `pelicula_idioma` DISABLE KEYS */;
/*!40000 ALTER TABLE `pelicula_idioma` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula_paisproduccion`
--

DROP TABLE IF EXISTS `pelicula_paisproduccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula_paisproduccion` (
  `id_pelicula` int NOT NULL,
  `id_pais` int NOT NULL,
  PRIMARY KEY (`id_pelicula`,`id_pais`),
  KEY `FK_Pelicula_PaisProduccion` (`id_pais`),
  CONSTRAINT `FK_Pelicula_PaisProduccion` FOREIGN KEY (`id_pais`) REFERENCES `paisproduccion` (`id_pais`),
  CONSTRAINT `FK_Pelicula_pelPais` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula_paisproduccion`
--

LOCK TABLES `pelicula_paisproduccion` WRITE;
/*!40000 ALTER TABLE `pelicula_paisproduccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `pelicula_paisproduccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pelicula_productora`
--

DROP TABLE IF EXISTS `pelicula_productora`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pelicula_productora` (
  `id_pelicula` int NOT NULL,
  `id_productora` int NOT NULL,
  PRIMARY KEY (`id_pelicula`,`id_productora`),
  KEY `FK_Pelicula_Productora` (`id_productora`),
  CONSTRAINT `FK_Pelicula_Pelicula_Productora` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`),
  CONSTRAINT `FK_Pelicula_Productora` FOREIGN KEY (`id_productora`) REFERENCES `productoras` (`id_productora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pelicula_productora`
--

LOCK TABLES `pelicula_productora` WRITE;
/*!40000 ALTER TABLE `pelicula_productora` DISABLE KEYS */;
/*!40000 ALTER TABLE `pelicula_productora` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peliculas`
--

DROP TABLE IF EXISTS `peliculas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peliculas` (
  `id_pelicula` int NOT NULL AUTO_INCREMENT,
  `titulo` varchar(225) DEFAULT NULL,
  `fecha_estreno` date DEFAULT NULL,
  `presupuesto` bigint DEFAULT NULL,
  `ingresos` bigint DEFAULT NULL,
  `duracion` int DEFAULT NULL,
  `descripcion` varchar(500) DEFAULT NULL,
  `enlace` varchar(200) DEFAULT NULL,
  `idioma_original` int DEFAULT NULL,
  `popularidad` float DEFAULT NULL,
  `estatus` varchar(50) DEFAULT NULL,
  `eslogan` varchar(100) DEFAULT NULL,
  `titulooriginal` varchar(255) DEFAULT NULL,
  `media_votos` float DEFAULT NULL,
  `numero_votos` int DEFAULT NULL,
  `poster` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id_pelicula`),
  KEY `FK_Peliiculas_Idioma_idx` (`idioma_original`),
  CONSTRAINT `FK_Peliculas_Idioma` FOREIGN KEY (`idioma_original`) REFERENCES `idiomas` (`id_idioma`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peliculas`
--

LOCK TABLES `peliculas` WRITE;
/*!40000 ALTER TABLE `peliculas` DISABLE KEYS */;
INSERT INTO `peliculas` VALUES (1,'El laberinto del fauno','2006-10-11',19000000,83850340,118,'En la España de 1944, la joven Ofelia y su madre enferma llegan al puesto del nuevo marido de ésta, un sádico capitán del ejército franquista. Mientras explora un antiguo laberinto, Ofelia conoce al Fauno, una extraña criatura que le hace una sorprendente revelación.','https://www.themoviedb.org/movie/1417-pan-s-labyrinth',2,72.5,'Released','La inocencia tiene un poder que el mal no puede imaginar.','El laberinto del fauno',7.9,9873,'https://image.tmdb.org/t/p/original/gtce25cra64QLmnbK6dnqzfRcRz.jpg'),(2,'Todo sobre mi madre','1999-04-08',5000000,67872296,101,'Manuela pierde a su único hijo el día en que cumple 17 años. Destrozada, huye a Barcelona en busca del padre del muchacho, que nunca supo que tenía un hijo.',NULL,1,7.2,'Estrenada','Un homenaje a las mujeres que actúan','Todo sobre mi madre',7.8,1250,'https://media.themoviedb.org/t/p/w440_and_h660_face/evvT3HUwoQYoPQXWFO10R6AvhxG.jpg'),(3,'La casa de papel','2021-12-03',12000000,95000000,130,'Después del exitoso atraco a la Casa de la Moneda, el Profesor y su equipo vuelven para enfrentar su misión más arriesgada.',NULL,1,8.2,'Estrenada','La resistencia continúa','La casa de papel: El final',7.9,5489,'https://media.themoviedb.org/t/p/w440_and_h660_face/lIa5eVVFdVNQmfAlKRexoJCVVd1.jpg');
/*!40000 ALTER TABLE `peliculas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personaje`
--

DROP TABLE IF EXISTS `personaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personaje` (
  `id_miembro` int NOT NULL AUTO_INCREMENT,
  `personaje` varchar(50) DEFAULT NULL,
  `orden` int DEFAULT NULL,
  PRIMARY KEY (`id_miembro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personaje`
--

LOCK TABLES `personaje` WRITE;
/*!40000 ALTER TABLE `personaje` DISABLE KEYS */;
/*!40000 ALTER TABLE `personaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productoras`
--

DROP TABLE IF EXISTS `productoras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `productoras` (
  `id_productora` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id_productora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productoras`
--

LOCK TABLES `productoras` WRITE;
/*!40000 ALTER TABLE `productoras` DISABLE KEYS */;
/*!40000 ALTER TABLE `productoras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seguidores`
--

DROP TABLE IF EXISTS `seguidores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seguidores` (
  `idUsuario1` int NOT NULL,
  `idUsuario2` int NOT NULL,
  PRIMARY KEY (`idUsuario1`,`idUsuario2`),
  KEY `Seguido2_idx` (`idUsuario2`),
  CONSTRAINT `Seguidores` FOREIGN KEY (`idUsuario1`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Seguidos` FOREIGN KEY (`idUsuario2`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seguidores`
--

LOCK TABLES `seguidores` WRITE;
/*!40000 ALTER TABLE `seguidores` DISABLE KEYS */;
/*!40000 ALTER TABLE `seguidores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trabajo`
--

DROP TABLE IF EXISTS `trabajo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `trabajo` (
  `id_trabajo` int NOT NULL AUTO_INCREMENT,
  `departamento` varchar(50) DEFAULT NULL,
  `trabajo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_trabajo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trabajo`
--

LOCK TABLES `trabajo` WRITE;
/*!40000 ALTER TABLE `trabajo` DISABLE KEYS */;
/*!40000 ALTER TABLE `trabajo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` int NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(100) DEFAULT NULL,
  `correo_electronico` varchar(255) DEFAULT NULL,
  `contrasena_hash` varchar(255) DEFAULT NULL,
  `rol` int DEFAULT NULL,
  `avatarUrl` varchar(200) DEFAULT NULL,
  `biografia` varchar(500) DEFAULT NULL,
  `nacimientoFecha` datetime DEFAULT NULL,
  `genero` int DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo_electronico_UNIQUE` (`correo_electronico`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'DavidFuerte','david@uma.es','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',NULL,'https://hips.hearstapps.com/hmg-prod/images/chris-bumstead-competes-in-the-classic-physique-event-news-photo-1730192136.jpg?crop=1xw:0.84334xh;center,top&resize=1200:*','Soy David','2025-04-22 00:00:00',1),(2,'Luis','luis@uma.es','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',NULL,NULL,NULL,NULL,NULL),(3,'UsuarioPrueba','usuarioPrueba@gmail.com','17baa0a148a3fc7c50c128eaf53a7cb052a0c63096d0154b394c9d4670ab68cb',NULL,NULL,NULL,NULL,NULL),(4,'Marcos','m@uma.es','43f1efecd33031b0ccd142b1c5cccc44ea19ad3e7a947965c5b0c16a632b5d7b',NULL,NULL,NULL,NULL,NULL),(5,'CBarba','cbarba@uma.es','c31febb2564abe57222be150a26b16fb1fbf00a17c6c439faa67eec03643ccd9',NULL,'https://itis.uma.es/wp-content/uploads/2020/12/CristobalBarba-731x1024-200x250.jpg','Sí',NULL,1);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-27 16:54:30
