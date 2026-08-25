-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: lab10_db
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sach`
--

DROP TABLE IF EXISTS `sach`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sach` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma_sach` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten_sach` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tac_gia` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nha_xuat_ban` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `nam_xuat_ban` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sach_ma` (`ma_sach`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sach`
--

LOCK TABLES `sach` WRITE;
/*!40000 ALTER TABLE `sach` DISABLE KEYS */;
INSERT INTO `sach` VALUES (1,'BK001','L?p tr?nh Java c? b?n','Nguy?n Thanh Tu?n','Nh? xu?t b?n Gi?o d?c',2023),(2,'BK002','C?u tr?c d? li?u v? gi?i thu?t','Tr?n Minh Ho?ng','Nh? xu?t b?n Khoa h?c',2022),(3,'BK003','L?p tr?nh h??ng ??i t??ng v?i Java','Ph?m V?n H?ng','Nh? xu?t b?n ??i h?c Qu?c gia',2024),(4,'BK004','SQL c? b?n v? n?ng cao','L? Th? Lan','Nh? xu?t b?n Th?ng k?',2023),(5,'BK005','Clean Code - Th?c h?nh','Robert C. Martin','Prentice Hall',2021),(6,'BK006','Design Patterns','Gang of Four','Addison-Wesley',2020),(7,'BK007','Spring Boot in Action','Craig Walls','Manning Publications',2022),(8,'BK008','Microservices Architecture','Sam Newman','O\'Reilly Media',2023);
/*!40000 ALTER TABLE `sach` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `san_pham`
--

DROP TABLE IF EXISTS `san_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `san_pham` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ten` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `mo_ta` text COLLATE utf8mb4_unicode_ci,
  `gia` decimal(12,2) NOT NULL DEFAULT '0.00',
  `so_luong` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sanpham_ma` (`ma`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham`
--

LOCK TABLES `san_pham` WRITE;
/*!40000 ALTER TABLE `san_pham` DISABLE KEYS */;
INSERT INTO `san_pham` VALUES (1,'SP001','Laptop Dell XPS 15','Laptop cao c?p, i7, 16GB RAM, 512GB SSD',28990000.00,15),(2,'SP002','iPhone 15 Pro','?i?n tho?i Apple A17 Pro, 256GB',32990000.00,20),(3,'SP003','Samsung Galaxy S24','?i?n tho?i Samsung Snapdragon 8 Gen 3',24990000.00,25),(4,'SP004','Tai nghe Sony WH-1000XM5','Tai nghe ch?ng ?n cao c?p',7990000.00,30),(5,'SP005','B?n ph?m Keychron K8','B?n ph?m c? wireless, layout TKL',2890000.00,50),(6,'SP006','Chu?t Logitech MX Master 3','Chu?t kh?ng d?y cao c?p',3490000.00,40),(7,'SP007','Monitor LG 27UK850','M?n h?nh 27 inch 4K IPS',14990000.00,12),(8,'SP008','USB Kingston 64GB','USB 3.2, t?c ?? cao',290000.00,100);
/*!40000 ALTER TABLE `san_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinh_vien`
--

DROP TABLE IF EXISTS `sinh_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinh_vien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ma_sinh_vien` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ho_ten` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lop` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sinhvien_ma` (`ma_sinh_vien`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinh_vien`
--

LOCK TABLES `sinh_vien` WRITE;
/*!40000 ALTER TABLE `sinh_vien` DISABLE KEYS */;
INSERT INTO `sinh_vien` VALUES (1,'20240001','Nguy?n V?n An','an@gmail.com','DCCNTT15.10.1'),(2,'20240002','Tr?n Th? B?nh','binh@gmail.com','DCCNTT15.10.2'),(3,'20240003','L? V?n C??ng','cuong@gmail.com','DCCNTT15.10.1'),(4,'20240004','Ph?m Th? Dung','dung@gmail.com','DCCNTT15.10.2'),(5,'20240005','Ho?ng V?n Em','em@gmail.com','DCCNTT15.10.1'),(6,'20240006','V? Th? Giang','giang@gmail.com','DCCNTT15.10.3'),(7,'20240007','?? V?n Hoa','hoa@gmail.com','DCCNTT15.10.3');
/*!40000 ALTER TABLE `sinh_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` enum('ADMIN','STAFF','USER') COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'USER',
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin@test.com','admin123','Qu?n tr? vi?n','ADMIN',1,'2026-08-25 10:38:13','2026-08-25 10:38:13'),(2,'staff@test.com','staff123','Nh?n vi?n','STAFF',1,'2026-08-25 10:38:13','2026-08-25 10:38:13'),(3,'user@test.com','user123','Ng??i d?ng','USER',1,'2026-08-25 10:38:13','2026-08-25 10:38:13'),(4,'inactive@test.com','inactive123','T?i kho?n b? kh?a','USER',0,'2026-08-25 10:38:13','2026-08-25 10:38:13');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-08-25 17:43:17
