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
-- Table structure for table `data_set_collection`
--

DROP TABLE IF EXISTS `data_set_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_set_collection` (
  `collectionId` varchar(32) NOT NULL,
  `abstractInfo` varchar(255) DEFAULT NULL,
  `collectionName` varchar(255) DEFAULT NULL,
  `dataDonated` date DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isMissingValues` varchar(255) DEFAULT NULL,
  `hits` int(11) NOT NULL,
  `relevantPapers` varchar(255) DEFAULT NULL,
  `topics` varchar(255) DEFAULT NULL,
  `areaId` int(11) DEFAULT NULL,
  PRIMARY KEY (`collectionId`),
  KEY `AREA_TYPE_FK` (`areaId`),
  CONSTRAINT `AREA_TYPE_FK` FOREIGN KEY (`areaId`) REFERENCES `area_info` (`areaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_set_collection`
--

LOCK TABLES `data_set_collection` WRITE;
/*!40000 ALTER TABLE `data_set_collection` DISABLE KEYS */;
INSERT INTO `data_set_collection` VALUES ('402881e85ec408fc015ec85a2c3a001d','For Further information about the variables see the file in the data folder.','Parkinson Disease Spiral Drawings Using Digitized Graphics Tablet Data Set ','2017-09-28',NULL,'true',2131,'Liang, X., S. Li, S. Zhang, H. Huang, and S. X. Chen (2016), PM2.5 data reliability, consistency, and air quality assessment in five Chinese cities, J. Geophys. Res. Atmos., 121, 10220â€“10236, [Web Link].\n\n',NULL,0),('402881e85ec408fc015ec86463000021','Prof. D. Petkovic (SFSU) Petkovic \'@\' sfsu.edu; Prof. Rainer Todtenhoefer (Fulda University, Germany); Prof. Shihong Huang (FAU)','Data for Software Engineering Teamwork Assessment in Education Setting Data Set ','2017-09-28',NULL,'true',0,'Liang, X., S. Li, S. Zhang, H. Huang, and S. X. Chen (2016), PM2.5 data reliability, consistency, and air quality assessment in five Chinese cities, J. Geophys. Res. Atmos., 121, 10220â€“10236, [Web Link].\n\n',NULL,0),('402881e85ec99a00015ecb05dd7e0002','','DAADA','2017-09-29',NULL,NULL,0,NULL,NULL,NULL),('402881ef5ecb1bf1015ecb3285000003','For Further information about the variables see the file in the data folder.','Parkinson Disease Spiral Drawings Using Digitized ','2017-09-29',NULL,'true',0,'Liang, X., S. Li, S. Zhang, H. Huang, and S. X. Chen (2016), PM2.5 data reliability, consistency, and air quality assessment in five Chinese cities, J. Geophys. Res. Atmos., 121, 10220â€“10236, [Web Link].\n\n',NULL,0),('8ae483f15ec86a01015ec8714a760008','For Further information about the variables see the file in the data folder.','Parkinson Disease Spiral Drawings Using Digitized Graphics Tablet Data Set ','2017-09-28',NULL,'true',0,'Liang, X., S. Li, S. Zhang, H. Huang, and S. X. Chen (2016), PM2.5 data reliability, consistency, and air quality assessment in five Chinese cities, J. Geophys. Res. Atmos., 121, 10220â€“10236, [Web Link].\n\n',NULL,0),('8ae483f15ec86a01015ec873eb62000e','For Further information about the variables see the file in the data folder.','Balance Scale Data Set ','2017-09-28',NULL,'true',0,'Liang, X., S. Li, S. Zhang, H. Huang, and S. X. Chen (2016), PM2.5 data reliability, consistency, and air quality assessment in five Chinese cities, J. Geophys. Res. Atmos., 121, 10220â€“10236, [Web Link].\n\n',NULL,0),('8ae483f15ec86a01015ec87784190012','For Further information about the variables see the file in the data folder.','Breast Cancer Wisconsin (Diagnostic) Data Set ','2017-09-28',NULL,'true',0,'Liang, X., S. Li, S. Zhang, H. Huang, and S. X. Chen (2016), PM2.5 data reliability, consistency, and air quality assessment in five Chinese cities, J. Geophys. Res. Atmos., 121, 10220â€“10236, [Web Link].\n\n',NULL,0);
/*!40000 ALTER TABLE `data_set_collection` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-10-16  0:46:51
