-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: data_mining
-- ------------------------------------------------------
-- Server version	5.7.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `data_set_container`
--

DROP TABLE IF EXISTS `data_set_container`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_set_container` (
  `containerId` varchar(255) NOT NULL,
  `containerName` varchar(255) DEFAULT NULL,
  `data` longblob,
  `fileDescription` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `fileType` varchar(255) DEFAULT NULL,
  `instances` bigint(20) DEFAULT NULL,
  `size` double DEFAULT NULL,
  `collectionId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`containerId`),
  KEY `COLLECTION_ID_FK` (`collectionId`),
  CONSTRAINT `COLLECTION_ID_FK` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_set_container`
--

LOCK TABLES `data_set_container` WRITE;
/*!40000 ALTER TABLE `data_set_container` DISABLE KEYS */;
INSERT INTO `data_set_container` VALUES ('402881e85ec408fc015ec82c156f0003',NULL,NULL,'得问氛围',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec82c15700004',NULL,NULL,'氛围氛围氛围分',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec851b02a0012',NULL,NULL,'违法为氛围',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec85303010016',NULL,NULL,'氛围氛围',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec85307a50018',NULL,NULL,'氛围氛围',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec8532941001a',NULL,NULL,'氛围氛围',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec85a2c3e001e',NULL,NULL,'得问得问文档',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec85a2c44001f',NULL,NULL,'得问的发掘个',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec86462e30020',NULL,NULL,'得问欺负',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec408fc015ec869410d0023',NULL,NULL,'氛围个人体会但是',NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec99a00015ecb05e1bd0003',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('402881e85ec99a00015ecb05e1bd0004',NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,NULL),('402881ef5ecb1bf1015ecb32850b0004',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec86c3aff0000',NULL,NULL,'分无关痛痒就',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec86fd2990003',NULL,NULL,'弗兰克我氛围',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec8707f2c0005',NULL,NULL,'违法个',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec8714a660007',NULL,NULL,'繁荣',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec871e4110009',NULL,NULL,'得问个个人',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec872f910000b',NULL,NULL,'分个人个人额',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec873eb54000d',NULL,NULL,'得分王狗头人违法的',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec8769ff1000f',NULL,NULL,'分个人和好',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec87784140011',NULL,NULL,'机会 热天是',NULL,NULL,NULL,NULL,NULL,NULL),('8ae483f15ec86a01015ec879e1210014',NULL,NULL,'氛围Greg而过服务氛围',NULL,NULL,NULL,NULL,NULL,NULL),('ff8080815ecb3b0f015ecb6cca780000',NULL,NULL,'',NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `data_set_container` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-16  0:46:55
