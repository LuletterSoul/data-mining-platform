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
-- Table structure for table `task_algorithm_rela`
--

DROP TABLE IF EXISTS `task_algorithm_rela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_algorithm_rela` (
  `taskId` varchar(255) NOT NULL,
  `algorithmId` varchar(255) NOT NULL,
  PRIMARY KEY (`taskId`,`algorithmId`),
  KEY `FKfrenb42wf96owc15h11jn3xk0` (`algorithmId`),
  CONSTRAINT `FK6colekrw0o116yyndn24h70s7` FOREIGN KEY (`taskId`) REFERENCES `data_mining_task` (`taskId`),
  CONSTRAINT `FKfrenb42wf96owc15h11jn3xk0` FOREIGN KEY (`algorithmId`) REFERENCES `algorithm_info` (`algorithmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_algorithm_rela`
--

LOCK TABLES `task_algorithm_rela` WRITE;
/*!40000 ALTER TABLE `task_algorithm_rela` DISABLE KEYS */;
INSERT INTO `task_algorithm_rela` VALUES ('ff8080815ed2cdf4015ed2d0ee6b0001','10'),('ff8080815ed2cdf4015ed2d0ee6b0001','3');
/*!40000 ALTER TABLE `task_algorithm_rela` ENABLE KEYS */;
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
