-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: peliculas_tmdb
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
INSERT INTO `favoritos` VALUES (10,1),(11,1),(10,2),(10,3);
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
-- Table structure for table `generousuario`
--

DROP TABLE IF EXISTS `generousuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `generousuario` (
  `id` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `generousuario`
--

LOCK TABLES `generousuario` WRITE;
/*!40000 ALTER TABLE `generousuario` DISABLE KEYS */;
INSERT INTO `generousuario` VALUES (0,'No definido'),(1,'Hombre'),(2,'Mujer'),(3,'Otro');
/*!40000 ALTER TABLE `generousuario` ENABLE KEYS */;
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
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(400) NOT NULL,
  `imgURL` varchar(600) DEFAULT NULL,
  PRIMARY KEY (`id_lista`),
  KEY `FK_Lista_Usuario` (`id_usuario`),
  CONSTRAINT `FK_Lista_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista`
--

LOCK TABLES `lista` WRITE;
/*!40000 ALTER TABLE `lista` DISABLE KEYS */;
INSERT INTO `lista` VALUES (21,10,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(22,10,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(26,23,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(27,23,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(28,24,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(29,24,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(30,25,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(31,25,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(32,26,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(33,26,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(34,27,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(35,27,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(39,10,'Pelis molonas','molan mucho','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTioBYbh-FAERJPBR9RjVeIkvhXBnwUsFh8uw&s'),(40,23,'Veranito','','https://media.istockphoto.com/id/93986448/es/foto/clapper.jpg?s=612x612&w=0&k=20&c=bbZguzBvEVkMczAm-NCcYKR8FLpsJvfogOEr9J_5WnA='),(41,28,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(42,28,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(43,28,'ListaNocturna','Para dormir','https://media.istockphoto.com/id/1328217256/es/foto/sala-de-estar-moderna-por-la-noche.jpg?s=612x612&w=0&k=20&c=56rgceYK6ehX2lTO8yMeNXEeDYt16-eqyszNKuuOw7Q='),(44,29,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(45,29,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(46,29,'Lista Fornite','Me gusta el fortnite','https://i.ytimg.com/vi/fNztlmSd5tU/hq720.jpg?sqp=-oaymwEhCK4FEIIDSFryq4qpAxMIARUAAAAAGAElAADIQj0AgKJD&rs=AOn4CLB2gMfnS6YMoikMd_7EZmk6IxqqWw'),(47,10,'Lista Redonda','Redonda','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzImnOTTF60bcT_NMNHe_tMM4M23t2zvfa4w&s'),(48,30,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(49,30,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(50,31,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(51,31,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(52,30,'MarcosLista','Mejores pelis','https://media.traveler.es/photos/63346504391ca8b9a43304b6/master/w_1600%2Cc_limit/E9NMJ9.jpg'),(53,32,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(54,32,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(55,33,'Vistas','Lista de películas vistas','https://static.vecteezy.com/system/resources/previews/023/731/459/non_2x/eye-icon-optic-illustration-sign-open-symbol-see-logo-vector.jpg'),(56,33,'Favoritas','\"Lista de películas favoritas\"','https://media.istockphoto.com/id/1439973042/es/vector/icono-plano-de-coraz%C3%B3n-rojo-el-s%C3%ADmbolo-del-amor-ilustraci%C3%B3n-vectorial.jpg?s=612x612&w=0&k=20&c=jBR9ICw7P_X7M4NIboEu17ZTjCxsxZE2GN8FOIRWqeg='),(57,33,'MuslimMovies','Películas super cools que me gustan a mi','https://m.media-amazon.com/images/I/71MMRIvK6EL._AC_UF894,1000_QL80_.jpg');
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
  CONSTRAINT `FK_lista` FOREIGN KEY (`id_lista`) REFERENCES `lista` (`id_lista`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `FK_Pelicula` FOREIGN KEY (`id_pelicula`) REFERENCES `peliculas` (`id_pelicula`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_peliculas`
--

LOCK TABLES `lista_peliculas` WRITE;
/*!40000 ALTER TABLE `lista_peliculas` DISABLE KEYS */;
INSERT INTO `lista_peliculas` VALUES (26,1),(27,1),(35,1),(51,1),(53,1),(55,1),(21,2),(22,2),(34,2),(50,2),(51,2),(53,2),(21,3),(26,3),(52,3),(27,6),(39,6),(57,6),(53,7),(22,8),(27,8),(52,8),(53,9),(47,10),(52,10),(53,10),(50,11),(46,13),(43,14),(27,16),(43,16),(53,19),(47,20),(26,25),(27,25),(27,26),(47,29),(55,29),(56,29),(46,38),(56,46);
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
INSERT INTO `pelicula_genero` VALUES (1,1),(5,1),(21,1),(23,1),(31,1),(36,1),(40,1),(1,2),(6,2),(15,2),(20,2),(25,2),(29,2),(34,2),(35,2),(41,2),(43,2),(1,3),(15,3),(25,3),(28,3),(32,3),(34,3),(35,3),(36,3),(37,3),(38,3),(39,3),(43,3),(44,3),(46,3),(47,3),(48,3),(15,4),(16,4),(20,4),(22,4),(24,4),(25,4),(27,4),(29,4),(30,4),(33,4),(34,4),(36,4),(37,4),(38,4),(45,4),(16,5),(24,5),(29,5),(35,5),(38,5),(19,6),(21,6),(22,6),(23,6),(27,6),(28,6),(30,6),(31,6),(32,6),(39,6),(40,6),(42,6),(44,6),(45,6),(46,6),(48,6),(5,7),(19,7),(21,7),(23,7),(27,7),(30,7),(31,7),(39,7),(40,7),(42,7),(46,7),(47,7),(20,8),(22,8),(33,8),(37,8);
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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peliculas`
--

LOCK TABLES `peliculas` WRITE;
/*!40000 ALTER TABLE `peliculas` DISABLE KEYS */;
INSERT INTO `peliculas` VALUES (1,'El laberinto del fauno','2006-10-11',19000000,83850340,118,'En la España de 1944, la joven Ofelia y su madre enferma llegan al puesto del nuevo marido de ésta, un sádico capitán del ejército franquista. Mientras explora un antiguo laberinto, Ofelia conoce al Fauno, una extraña criatura que le hace una sorprendente revelación.','https://www.themoviedb.org/movie/1417-pan-s-labyrinth',2,72.5,'Estrenada','La inocencia tiene un poder que el mal no puede imaginar.','El laberinto del fauno',7.9,9873,'https://image.tmdb.org/t/p/original/gtce25cra64QLmnbK6dnqzfRcRz.jpg'),(2,'Todo sobre mi madre','1999-04-08',5000000,67872296,101,'Manuela pierde a su único hijo el día en que cumple 17 años. Destrozada, huye a Barcelona en busca del padre del muchacho, que nunca supo que tenía un hijo.',NULL,1,7.2,'Estrenada','Un homenaje a las mujeres que actúan','Todo sobre mi madre',7.8,1250,'https://media.themoviedb.org/t/p/w440_and_h660_face/evvT3HUwoQYoPQXWFO10R6AvhxG.jpg'),(3,'La casa de papel','2021-12-03',12000000,95000000,130,'Después del exitoso atraco a la Casa de la Moneda, el Profesor y su equipo vuelven para enfrentar su misión más arriesgada.','https://www.themoviedb.org/tv/132719-money-heist-from-tokyo-to-berlin',1,8.2,'Estrenada','La resistencia continúa','La casa de papel: El final',7.9,5489,'https://media.themoviedb.org/t/p/w440_and_h660_face/lIa5eVVFdVNQmfAlKRexoJCVVd1.jpg'),(5,'Wicked','2024-11-27',100000000,518497683,150,'En la tierra de Oz, Elphaba, de piel verde y marginada, comparte habitación con la aristócrata Glinda en la Universidad de Shiz. Su improbable amistad se pone a prueba mientras cumplen sus respectivos destinos como Glinda la Buena y la Malvada Bruja del Oeste.','https://www.themoviedb.org/movie/402431-wicked',1,80.5,'Estrenada','Una amistad que cambiará Oz para siempre.','Wicked',7.8,1200,'https://www.themoviedb.org/t/p/w1280/1ZSVA2pNpc89C07RDgsR6p0GMs7.jpg'),(6,'A Minecraft Movie','2025-04-04',150000000,215084963,110,'Cuatro inadaptados son transportados a través de un portal misterioso al Overworld, un mundo cúbico lleno de imaginación. Para regresar a casa, deben embarcarse en una aventura mágica junto a un experto artesano llamado Steve.','https://www.themoviedb.org/movie/950387-a-minecraft-movie',1,75.3,'Estrenada','Construye tu destino.','A Minecraft Movie',6.5,850,'https://image.tmdb.org/t/p/w1280/rZYYmjgyF5UP1AVsvhzzDOFLCwG.jpg'),(7,'El Regreso','2024-12-06',50000000,542689310,116,'Después de veinte años, Odiseo regresa a Ítaca, desaliñado e irreconocible. El rey finalmente ha vuelto a casa, pero mucho ha cambiado en su reino desde que partió a la guerra de Troya.','https://www.themoviedb.org/movie/975511-the-return',1,68.4,'Estrenada','Algunas leyendas son más que mitos.','The Return',7.2,950,'https://www.themoviedb.org/t/p/w1280/cKp0ZDYGydWxI3v8KS3tY8W9aM7.jpg'),(8,'Blancanieves','2025-03-21',120000000,548961320,109,'La princesa Blancanieves huye del castillo cuando la Reina, celosa de su belleza interior, intenta matarla. En lo profundo del bosque, encuentra a siete enanos mágicos y a un joven bandido llamado Jonathan. Juntos, luchan por sobrevivir y recuperar el reino.','https://www.themoviedb.org/movie/447273-snow-white',1,85.7,'Estrenada','El clásico cobra vida.','Snow White',6.9,1100,'https://www.themoviedb.org/t/p/w1280/sm91FNDF6OOKU4hT9BDW6EMoyDB.jpg'),(9,'Trampa','2024-08-02',40000000,548763201,105,'Un padre y su hija adolescente asisten a un concierto pop, donde se dan cuenta de que están en el centro de un evento oscuro y siniestro.','https://www.themoviedb.org/movie/1032823-trap',1,70.2,'Estrenada','30,000 fans. 300 cops. 1 serial killer. No escape.','Trap',7,980,'https://www.themoviedb.org/t/p/w1280/j81fGYj8hjDhpdNSHzREWMaE8p8.jpg'),(10,'Guerra','2025-04-11',60000000,215634890,95,'Un pelotón de Navy SEALs emprende una peligrosa misión en Ramadi, Irak, con el caos y la hermandad de la guerra contados a través de sus recuerdos.','https://www.themoviedb.org/movie/1241436-warfare',1,65.8,'Estrenada','Todo se basa en la memoria.','Warfare',7.5,870,'https://www.themoviedb.org/t/p/w1280/fkVpNJugieKeTu7Se8uQRqRag2M.jpg'),(11,'Aquí','2024-12-25',70000000,245819736,104,'Una odisea a través del tiempo y la memoria, centrada en un lugar en Nueva Inglaterra donde el amor, la pérdida, la lucha, la esperanza y el legado se desarrollan entre parejas y familias a lo largo de generaciones.','https://www.themoviedb.org/movie/940139-here',1,72.4,'Estrenada','Cada lugar tiene una historia.','Here',8,1050,'https://www.themoviedb.org/t/p/w1280/sXfGJsoB7gcuhP6CTggQ2AKg4I.jpg'),(12,'Caos','2025-04-25',80000000,215467983,107,'Cuando un atraco de drogas sale mal, un policía desilusionado lucha a través del mundo criminal de una ciudad corrupta para salvar al hijo de un político.','https://www.themoviedb.org/movie/668489-havoc',1,78.6,'Estrenada','Sin ley. Solo desorden.','Havoc',7.3,990,'https://www.themoviedb.org/t/p/w1280/tbsDLmo2Ej8YFM0HKcOGfNMTlyJ.jpg'),(13,'Leyenda','2024-10-31',30000000,316457982,100,'Cuatro amigos amantes de las emociones fuertes reservan una excursión de campamento con temática de terror dirigida por Darwin, un guía extraño y excéntrico, quien una noche los anima a contar su historia más aterradora alrededor de una fogata.','https://www.themoviedb.org/movie/1155090-lore',1,60.9,'Estrenada','Algunas historias cobran vida.','Lore',6.8,750,'https://www.themoviedb.org/t/p/w1280/2ui3kTAiDznWR040qepibw8IWRt.jpg'),(14,'De Vuelta en Acción','2025-01-17',90000000,312479821,114,'Quince años después de desaparecer de la CIA para formar una familia, los espías de élite Matt y Emily regresan al mundo del espionaje cuando su tapadera se ve comprometida.','https://www.themoviedb.org/movie/993710-back-in-action',1,83.2,'Estrenada','Viviendo sus mejores mentiras.','Back in Action',7.1,880,'https://www.themoviedb.org/t/p/w1280/mAvyQ2X3767LwXE2htvAd22ucd3.jpg'),(15,'Shrek','2001-07-13',60000000,488351320,92,'Shrek es una película de comedia estadounidense de animación por computadora estrenada en 2001 basada en el libro homónimo de William Steig de 1990. ','https://www.themoviedb.org/movie/808-shrek',1,7.4,'Estrenada','El mayor cuento de hadas jamas contado.','Shrek',7.3,5660,'https://www.themoviedb.org/t/p/w1280/5G1RjHMSt7nYONqCqSwFlP87Ckk.jpg'),(16,'The Dark Knight','2008-07-18',185000000,1006000000,152,'The Dark Knight (conocida en Hispanoamérica como Batman: El caballero de la noche y en España como El caballero oscuro) es una película de superhéroes de 2008 dirigida por Christopher Nolan a partir de un guion coescrito con su hermano Jonathan.','https://www.themoviedb.org/movie/155-the-dark-knight',1,7.7,'Estrenada','Batman','The Dark Knight',7.7,6342,'https://www.themoviedb.org/t/p/w1280/8QDQExnfNFOtabLDKqfDQuHDsIg.jpg'),(19,'La lista de Schindler','1993-11-13',22000000,322139355,195,'La lista de Schindler (título original: Schindler\'s List) es una película estadounidense de 1993 del género de drama histórico basada en la novela de ficción histórica El arca de Schindler del escritor australiano Thomas Keneally.','https://www.themoviedb.org/movie/424-schindler-s-list',1,8.9,'Estrenada','Quien salva una vida, salva al mundo entero.','La lista de Schindler',9,5674,'https://www.themoviedb.org/t/p/w1280/3Ho0pXsnMxpGJWqdOi0KDNdaTkT.jpg'),(20,'Misión Imposible','2025-05-23',400000000,506800000,169,'El agente Ethan Hunt continúa su misión de impedir que Gabriel controle el tecnológicamente omnipotente programa de IA conocido como','https://www.themoviedb.org/movie/575265-mission-impossible-the-final-reckoning',1,8.1,'Estrenada','Cada elección, cada misión, ha conducido a esto.','Misión Imposible',8.2,7354,'https://www.themoviedb.org/t/p/w1280/haOjJGUV00dKlZaJWgjM1UD1cJV.jpg'),(21,'Destino final: Lazos de sangre','2025-05-16',30000000,500000000,90,'Cuando comienzan a morir de forma inexplicable, los supervivientes descubren que la Muerte está tramando una venganza…','https://www.themoviedb.org/movie/574475-final-destination-bloodlines',1,70.2,'Estrenada','La parada final espera.','Destino final: Lazos de sangre',6.5,230,'https://www.themoviedb.org/t/p/w1280/frNkbclQpexf3aUzZrnixF3t5Hw.jpg'),(22,'The Amateur','2025-04-11',40000000,600000000,110,'Un ex‑oficial de la CIA se ve arrastrado de nuevo al espionaje tras un ataque que amenaza su vida y la de su familia.','https://www.themoviedb.org/movie/1087891-the-amateur',1,65.3,'Estrenada','Ha sido entrenado por el mejor.','The Amateur',6.8,175,'https://www.themoviedb.org/t/p/w1280/9WPWF0AmklsKclrXeyKXbQ543ZA.jpg'),(23,'STRAW','2025-06-06',20000000,700000000,100,'Un thriller urbano donde una mujer se enfrenta a un pasado que creía olvidado.','https://www.themoviedb.org/movie/1426776-straw',1,60.1,'Estrenada','Algunos secretos se entierran vivos.','STRAW',5.9,112,'https://www.themoviedb.org/t/p/w1280/eAf8Is1xX4gq4Jk14iFC1qhmT8R.jpg'),(24,'Predator: Killer of Killers','2025-06-06',120000000,80000000,130,'La cacería definitiva regresa cuando un escuadrón elite se enfrenta al depredador más letal.','https://www.themoviedb.org/movie/1376434-predator-killer-of-killers',1,85,'Estrenada','Cazar o ser cazado.','Predator: Killer of Killers',7.2,502,'https://www.themoviedb.org/t/p/w1280/qHDsrBZJRx6ZCO4tocFh3gnbosU.jpg'),(25,'Lilo & Stitch','2025-05-23',150000000,900000000,105,'La reinvención live‑action del clásico animado sobre una niña y su peculiar mascota alienígena.','https://www.themoviedb.org/movie/552524-lilo-stitch',1,75.4,'Estrenada','Ohana significa familia.','Lilo & Stitch',7.5,640,'https://www.themoviedb.org/t/p/w1280/tUae3mefrDVTgm5mRzqWnZK6fOP.jpg'),(26,'Cómo entrenar a tu dragón','2025-06-12',175000000,30000000,104,'La querida saga continúa en una nueva entrega donde Hipo y Desdentado enfrentan retos nunca vistos.','https://www.themoviedb.org/movie/1087192-how-to-train-your-dragon',1,78.6,'Estrenada','Conduce el viento.','How to Train Your Dragon',8.1,850,'https://www.themoviedb.org/t/p/w1280/fTpbUIwdsfyIldzYvzQi2K4Icws.jpg'),(27,'Utopia','2024-12-09',60000000,770000000,110,'Un grupo descubre un mundo perfecto que esconde secretos terribles.','https://www.themoviedb.org/tv/46511-utopia',1,64.5,'Estrenada','The Network is watching.','Utopia',6.3,210,'https://www.themoviedb.org/t/p/w1280/Atas96nmykMl0ugerdnLgxxaNA4.jpg'),(28,'Mikaela','2025-01-31',30000000,150000000,95,'Una joven apasionada por el surf lucha por encontrar su lugar en el mundo.','https://www.themoviedb.org/movie/1315988-mikaela',1,55.7,'Estrenada','A storm. A heist. A cop on the edge.','Mikaela',5.8,90,'https://www.themoviedb.org/t/p/w1280/jm6lNbpmKcxX6M0pieaKhP6hs3w.jpg'),(29,'K.O.','2025-06-06',25000000,420000000,105,'Un boxeador busca redimirse golpe a golpe en su última oportunidad.','https://www.themoviedb.org/movie/1450599-k-o',1,58,'Estrenada','Un asesino con una misión.','K.O.',6,130,'https://www.themoviedb.org/t/p/w1280/qcM2sUiAeP4zXwx4ADSvgc9S58k.jpg'),(30,'Deep Cover','2025-06-12',40000000,78900000,120,'Un agente encubierto se infiltra en un cártel peligroso.','https://www.themoviedb.org/movie/1239193-deep-cover',1,62.3,'Estrenada','Si quieres sobrevivir, improvisa.','Deep Cover',6.4,150,'https://www.themoviedb.org/t/p/w1280/dpLNAIU8AjAxw39fZfSDt2AiwMs.jpg'),(31,'Clown in a Cornfield','2025-05-08',15000000,55000000,95,'Un payaso terrorífico siembra el caos en un pequeño pueblo agrícola.','https://www.themoviedb.org/movie/713364-clown-in-a-cornfield',1,50.2,'Estrenada','Are you a friend of Frendo?','Clown in a Cornfield',5.5,80,'https://www.themoviedb.org/t/p/w1280/6ep6gw90TJ8bYvJC6hEDo8SxjoJ.jpg'),(32,'Sinners','2025-04-16',10000000,480000000,100,'Una comunidad religiosa enfrenta una serie de crímenes inexplicables.','https://www.themoviedb.org/movie/1233413-sinners',1,48.7,'Estrenada','Baila con el diablo.','Sinners',5.2,60,'https://www.themoviedb.org/t/p/w1280/zdClwqpYQXBSCGGDMdtvsuggwec.jpg'),(33,'The Roundup: Punishment','2024-10-09',35000000,326000000,125,'Un oficial de élite regresa a Corea para enfrentarse a un psicópata.','https://www.themoviedb.org/movie/1017163-4',1,66,'Estrenada','Time for the ultimate wipeout.','The Roundup: Punishment',7,250,'https://www.themoviedb.org/t/p/w1280/h6DvCL25R61iRzN7qkUnFg6O33A.jpg'),(34,'Life After Fighting','2024-06-07',20000000,1420000000,110,'Después de un torbellino de peleas, un hombre busca reconstruir su vida.','https://www.themoviedb.org/movie/1289601-life-after-fighting',1,45,'Estrenada','La vida después de la lucha.','Life After Fighting',5,40,'https://www.themoviedb.org/t/p/w1280/8iZZyL7FyC4wYipwKemvuf58U3R.jpg'),(35,'Kayara','2025-04-30',15000000,420000000,105,'Una arqueóloga descubre misterios que cambiarán la historia.','https://www.themoviedb.org/movie/666154-kayara',1,47.5,'Estrenada','Una joven inca apasionada.','Kayara',5.9,70,'https://www.themoviedb.org/t/p/w1280/A1DSh29atQjLZCVCddMyGw1mbM8.jpg'),(36,'Hunt the Wicked','2024-07-18',18000000,530000000,105,'Un cazador de recompensas persigue a un asesino en una ciudad corrupta.','https://www.themoviedb.org/movie/1240475',1,44.3,'Estrenada','Caza al mal.','Hunt the Wicked',5.3,50,'https://www.themoviedb.org/t/p/w1280/n78hDmtawKOvJh3YLMcC5OJr2ER.jpg'),(37,'Ballerina','2025-06-06',100000000,400000000,120,'Una joven bailarina se enfrenta a malos del crimen en París.','https://www.themoviedb.org/movie/541671-ballerina',1,60.5,'Estrenada','La venganza tiene una nueva cara.','Ballerina',6.2,220,'https://www.themoviedb.org/t/p/w1280/gQCrYmvCK7JCLXjCGTMRF5Lzr5c.jpg'),(38,'Avatar','2009-12-18',237000000,284724620,162,'Un marine tetrapléjico es enviado al planeta Pandora.','https://www.themoviedb.org/movie/19995-avatar',1,90,'Estrenada','Entra al mundo de avatar.','Avatar',7.5,18000,'https://www.themoviedb.org/t/p/w1280/nn7prZXNz3dgCV5jeShqqfHcU9F.jpg'),(39,'Titanic','1997-12-19',200000000,530000000,194,'La historia de amor a bordo del Titanic.','https://www.themoviedb.org/movie/597-titanic',1,89.5,'Estrenada','Nada en el mundo podría separarlos','Titanic',7.8,21000,'https://www.themoviedb.org/t/p/w1280/VMOt5scbGmBKDvkfHjZN6Ki54i.jpg'),(40,'La viuda negra','2025-05-30',200000000,530000000,104,'Thriller basado en el caso real del crimen de Patraix, con Ivana Baquero y Carmen Machi.','https://www.themoviedb.org/movie/1397832-la-viuda-negra',2,87.5,'Estrenada','Basado en hechos reales.','La viuda negra',7.8,21000,'https://www.themoviedb.org/t/p/w1280/uuabL0qp3zygLDEjImbPiWR9j2e.jpg'),(41,'Los aitas','2025-03-21',200000000,530000000,104,'Comedia de época sobre padres que conducen un equipo infantil a Berlín en los 80.','https://www.themoviedb.org/movie/1187315-los-aitas',2,62.6,'Estrenada','Padres desinteresados.','Los aitas',7.8,21000,'https://www.themoviedb.org/t/p/w1280/8m0hnwzgM9da74WCxBLfCdRcsaT.jpg'),(42,'Hamburgo','2025-05-30',200000000,530000000,104,'Thriller: un conductor implicado en trata decide robar a la mafia.','https://www.themoviedb.org/movie/1452198-hamburgo',2,55.6,'Estrenada','Consigue escapar.','Hamburgo',7.8,21000,'https://www.themoviedb.org/t/p/w1280/aWghdaaHi1TKab5Esf25XhxnxcN.jpg'),(43,'La luz de Aisha','2025-04-11',200000000,530000000,104,'Película de animación en catalán: aventura en Al‑Ándalus dirigida por Shadi Adib.','https://www.themoviedb.org/movie/1297962-la-llum-de-l-aisha',2,75.6,'Estrenada','Acompaña al saber.','La luz de Aisha',7.8,21000,'https://www.themoviedb.org/t/p/w1280/mijPmMCLCI12iwIwi2rn1kd7esJ.jpg'),(44,'Muy lejos','2025-04-11',200000000,530000000,104,'Drama protagonizado por Mario Casas sobre inmigración y pánico en Europa.','https://www.themoviedb.org/movie/1409925-molt-lluny',2,86.3,'Estrenada','Que no cunda el pánico.','Muy lejos',7.8,21000,'https://www.themoviedb.org/t/p/w1280/oF3noXatzftfCltE0iEDnzU5WIi.jpg'),(45,'8','2025-03-21',200000000,530000000,104,'Drama histórico dirigido por Julio Medem, estrenado en el Festival de Málaga.','https://www.themoviedb.org/movie/691143-8',2,65.3,'Estrenada','Estás invitado','8',7.8,21000,'https://www.themoviedb.org/t/p/w1280/2XKQtJo9BIVtRVn5grINP8JKkwl.jpg'),(46,'Wolfgang (Extraordinario)','2025-03-14',200000000,530000000,104,'Comedia dramática sobre un niño con autismo y su padre, dirigida por J. Ruiz Caldera.','https://www.themoviedb.org/movie/1306284-wolfgang-extraordinari',2,45.6,'Estrenada','Un joven cerebrito.','Wolfgang (Extraordinario)',7.8,21000,'https://www.themoviedb.org/t/p/w1280/bfLFwdbfdTalLpJKyEXdUv5pELs.jpg'),(47,'El secreto del orfebre','2025-02-28',200000000,530000000,104,'Drama romántico de época con Mario Casas y Michelle Jenner.','https://www.themoviedb.org/movie/1310861-el-secreto-del-orfebre',2,84.5,'Estrenada','Una historia de amor que trasciende el tiempo.','El secreto del orfebre',7.8,21000,'https://www.themoviedb.org/t/p/w1280/8cAXLYDmn0POhjqzTHeT6rJMUVc.jpg'),(48,'Romería','2026-01-30',200000000,530000000,104,'Drama de Carla Simón sobre una joven que viaja a Galicia para conocer a su familia.','https://www.themoviedb.org/movie/1182861-romeria',2,72.3,'Pendiente de estreno','Un descubrimiento asombroso.','Romería',7.8,21000,'https://www.themoviedb.org/t/p/w1280/pQd4HoRdoE6kqZov3vQXdzCn3XW.jpg');
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
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (0,'Normal'),(1,'Premium'),(2,'Administrador de películas'),(3,'Administrador de usuarios');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
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
INSERT INTO `seguidores` VALUES (29,10),(10,23),(10,32),(30,32),(31,32);
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
  `nombre_usuario` varchar(100) NOT NULL,
  `correo_electronico` varchar(255) DEFAULT NULL,
  `contrasena_hash` varchar(255) DEFAULT NULL,
  `rol` int DEFAULT '0',
  `avatarUrl` varchar(600) DEFAULT NULL,
  `biografia` varchar(500) DEFAULT NULL,
  `nacimientoFecha` date DEFAULT NULL,
  `genero` int NOT NULL DEFAULT '0',
  `creacionCuentaFecha` date NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `correo_electronico_UNIQUE` (`correo_electronico`),
  KEY `rol_idx` (`rol`),
  KEY `genero_idx` (`genero`),
  CONSTRAINT `generoUsuario` FOREIGN KEY (`genero`) REFERENCES `generousuario` (`id`),
  CONSTRAINT `rol` FOREIGN KEY (`rol`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (10,'David','david@uma.es','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4',1,'https://preview.redd.it/hilo-de-im%C3%A1genes-mas-duras-v0-yr9l0oh5oxtb1.png?width=640&crop=smart&auto=webp&s=3f0931c5c7b97352d60738264c5595c79a2b53b7','','2025-05-07',1,'2022-01-01'),(23,'AdminPeliculas','AdminPeliculas@uma.es','8b795c74238ebde20d42cc2129a93f03e214c5ad0740e283931d921ccb64d335',2,'https://img2.rtve.es/i/?w=1600&i=1633090530572.jpg','',NULL,0,'2025-06-11'),(24,'UsuarioNormal','UsuarioNormal@uma.es','ad6e2e298080754490c14619a31c6ee14eaf1eaad5594a14c913892c65d760cb',0,NULL,'',NULL,0,'2025-06-12'),(25,'UsuarioPremium','UsuarioPremium@uma.es','3e468462007b2425b4d2d90d57df5d1ecd3f27d11631347a6cea50ced4a07211',1,NULL,'',NULL,0,'2025-06-12'),(26,'AdminUsuarios','AdminUsuarios@uma.es','a5d106ac453bbb1c94a262086ab784a07dfebcfd6e0828f09dec400f258af935',3,NULL,'Admin',NULL,1,'2025-06-12'),(27,'DavidNueva','davidn@uma.es','c69e41fdd2acacc1e4d092d6344640c0178ea7da49a64252256764ef0532e21a',1,NULL,'',NULL,0,'2025-06-15'),(28,'Joaquin','Joaquin@uma.es','d3a21f544438b315e93fef7723a06350a6b0e1b2294ff0a825ee6860f2373c02',0,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRtbwmi_aVxgdCQxKNNSB2lvxAYXFLsJ0BkUA&s','Tengo 10 años y me gusta el cine','2015-02-11',1,'2025-06-16'),(29,'Luis','Luis@uma.es','1be075b9041a58b82be347b54e9f3d7f5d84dc57935bcc769106748a9eb237e8',0,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWx4IY9b8o8b7JaTczhxjt9zReAQuam8O5Ew&s','',NULL,1,'2025-06-16'),(30,'Marcos','marcos@uma.es','43f1efecd33031b0ccd142b1c5cccc44ea19ad3e7a947965c5b0c16a632b5d7b',0,'https://i.blogs.es/387576/mrburns/450_1000.png','',NULL,0,'2025-06-16'),(31,'Alejandro','Alejandro@uma.es','30cc1cb006f560be31de08160f80efe06635bd3efb8cb8745cf74d6ca8f6d921',0,'https://i.pinimg.com/originals/6e/c5/de/6ec5de6486d44589dec2402bc84e331e.jpg','Me gustan las gorras','2004-06-30',1,'2025-06-16'),(32,'Fran','Fran@uma.es','e7be6c6f309a16f2315c92fe67d2620929e778f6a4c4dd5be1ff756550f6bd0b',0,'https://previews.123rf.com/images/yacobchuk/yacobchuk1601/yacobchuk160100213/50835209-kid-with-laptop-chubby-boy-in-eyeglasses-and-headphones-is-sitting-on-the-sofa-while-holding-his.jpg','Soy Fran',NULL,2,'2025-06-16'),(33,'Soraya','Soraya@uma.es','cc6016b62f25d56507033c48b04517ba40b3490b1e9b01f1c485371311ed42c4',1,'https://i0.wp.com/muslimgirl.com/wp-content/uploads/2019/06/Screen-Shot-2019-06-10-at-12.12.19-PM.png?fit=768%2C652&ssl=1','Hola',NULL,2,'2025-06-16');
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

-- Dump completed on 2025-06-16 21:24:10
