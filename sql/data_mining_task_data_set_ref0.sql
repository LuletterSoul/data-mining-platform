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
-- Table structure for table `task_data_set_ref`
--

DROP TABLE IF EXISTS `task_data_set_ref`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_data_set_ref` (
  `taskId` varchar(255) NOT NULL,
  `collectionId` varchar(32) NOT NULL,
  PRIMARY KEY (`taskId`,`collectionId`),
  KEY `FKsdcssd8b8y5vuxe9rvsoec2wy` (`collectionId`),
  CONSTRAINT `FKhqo2ihw38k1wkvh83ck7oixbj` FOREIGN KEY (`taskId`) REFERENCES `data_mining_task` (`taskId`),
  CONSTRAINT `FKsdcssd8b8y5vuxe9rvsoec2wy` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_data_set_ref`
--

LOCK TABLES `task_data_set_ref` WRITE;
/*!40000 ALTER TABLE `task_data_set_ref` DISABLE KEYS */;
INSERT INTO `task_data_set_ref` VALUES ('ff8080815ed2cdf4015ed2d0ee6b0001','402881e85ec408fc015ec86463000021'),('ff8080815ed2cdf4015ed2d0ee6b0001','402881ef5ecb1bf1015ecb3285000003');
/*!40000 ALTER TABLE `task_data_set_ref` ENABLE KEYS */;
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
