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
-- Table structure for table `set_task_rel`
--

DROP TABLE IF EXISTS `set_task_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `set_task_rel` (
  `collectionId` varchar(32) NOT NULL,
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`collectionId`,`typeId`),
  KEY `FKljqi2ox3y2w5np2s0118hp0fl` (`typeId`),
  CONSTRAINT `FK64ccuy1aed5s744jenbk6di31` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`),
  CONSTRAINT `FKljqi2ox3y2w5np2s0118hp0fl` FOREIGN KEY (`typeId`) REFERENCES `mining_task_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `set_task_rel`
--

LOCK TABLES `set_task_rel` WRITE;
/*!40000 ALTER TABLE `set_task_rel` DISABLE KEYS */;
INSERT INTO `set_task_rel` VALUES ('402881e85ec99a00015ecb05dd7e0002',0),('402881ef5ecb1bf1015ecb3285000003',0),('402881e85ec99a00015ecb05dd7e0002',1),('402881ef5ecb1bf1015ecb3285000003',1),('402881e85ec99a00015ecb05dd7e0002',2),('402881ef5ecb1bf1015ecb3285000003',2);
/*!40000 ALTER TABLE `set_task_rel` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-16  0:46:54
