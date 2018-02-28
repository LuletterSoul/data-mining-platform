-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: data_mining
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `algorithm_info`
--

DROP TABLE IF EXISTS `algorithm_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `algorithm_info` (
  `algorithmId` int(11) NOT NULL,
  `algorithmName` varchar(255) DEFAULT NULL,
  `interfaceDescription` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`algorithmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `algorithm_info`
--

LOCK TABLES `algorithm_info` WRITE;
/*!40000 ALTER TABLE `algorithm_info` DISABLE KEYS */;
INSERT INTO `algorithm_info` VALUES (1,'C4.5',NULL),(2,'k均值聚类算法',NULL),(3,'支持向量机',NULL),(4,'Apriori关联算法',NULL),(5,'EM 最大期望算法',NULL),(6,'PageRank算法',NULL),(7,'AdaBoost 迭代算法',NULL),(8,'kNN：k最近邻算法',NULL),(10,'CART 分类算法',NULL);
/*!40000 ALTER TABLE `algorithm_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `algorithm_param`
--

DROP TABLE IF EXISTS `algorithm_param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `algorithm_param` (
  `paramId` bigint(20) NOT NULL,
  `dataType` varchar(255) DEFAULT NULL,
  `isNecessary` bit(1) NOT NULL,
  `paramDesc` varchar(255) DEFAULT NULL,
  `algorithmId` int(11) DEFAULT NULL,
  PRIMARY KEY (`paramId`),
  KEY `ALGORITHM_FK_ID` (`algorithmId`),
  CONSTRAINT `ALGORITHM_FK_ID` FOREIGN KEY (`algorithmId`) REFERENCES `algorithm_info` (`algorithmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `algorithm_param`
--

LOCK TABLES `algorithm_param` WRITE;
/*!40000 ALTER TABLE `algorithm_param` DISABLE KEYS */;
/*!40000 ALTER TABLE `algorithm_param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `algorithm_type`
--

DROP TABLE IF EXISTS `algorithm_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `algorithm_type` (
  `typeId` int(11) NOT NULL,
  `chTypeName` varchar(255) DEFAULT NULL,
  `enTypeName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `algorithm_type`
--

LOCK TABLES `algorithm_type` WRITE;
/*!40000 ALTER TABLE `algorithm_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `algorithm_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `algorithm_type_rel`
--

DROP TABLE IF EXISTS `algorithm_type_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `algorithm_type_rel` (
  `typeId` int(11) DEFAULT NULL,
  `algorithmId` int(11) NOT NULL,
  PRIMARY KEY (`algorithmId`),
  KEY `FKprpj9peylvqavh2ft51f8jsbq` (`typeId`),
  CONSTRAINT `FKh53ldoxfn1ws172g7jwnnx2u0` FOREIGN KEY (`algorithmId`) REFERENCES `algorithm_info` (`algorithmId`),
  CONSTRAINT `FKprpj9peylvqavh2ft51f8jsbq` FOREIGN KEY (`typeId`) REFERENCES `algorithm_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `algorithm_type_rel`
--

LOCK TABLES `algorithm_type_rel` WRITE;
/*!40000 ALTER TABLE `algorithm_type_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `algorithm_type_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area_info`
--

DROP TABLE IF EXISTS `area_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area_info` (
  `areaId` int(11) NOT NULL,
  `abbreviation` varchar(255) DEFAULT NULL,
  `chineseName` varchar(255) DEFAULT NULL,
  `englishName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`areaId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area_info`
--

LOCK TABLES `area_info` WRITE;
/*!40000 ALTER TABLE `area_info` DISABLE KEYS */;
INSERT INTO `area_info` VALUES (0,NULL,'生活','Life'),(1,NULL,'物理','Physical'),(2,NULL,'计算机','Computer'),(3,NULL,'社会','Social'),(4,NULL,'经济','Financial'),(5,NULL,'商业','Business'),(6,NULL,'游戏','Game'),(7,NULL,'其他','Other');
/*!40000 ALTER TABLE `area_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attr_char_info`
--

DROP TABLE IF EXISTS `attr_char_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attr_char_info` (
  `charId` int(11) NOT NULL,
  `abbreviation` varchar(255) DEFAULT NULL,
  `chineseName` varchar(255) DEFAULT NULL,
  `englishName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attr_char_info`
--

LOCK TABLES `attr_char_info` WRITE;
/*!40000 ALTER TABLE `attr_char_info` DISABLE KEYS */;
INSERT INTO `attr_char_info` VALUES (0,NULL,'分类的','Categorical'),(1,NULL,'整型','Integer'),(2,NULL,'实数','Real');
/*!40000 ALTER TABLE `attr_char_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `attr_feature_info`
--

DROP TABLE IF EXISTS `attr_feature_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attr_feature_info` (
  `charId` int(11) NOT NULL,
  `abbreviation` varchar(255) DEFAULT NULL,
  `chineseName` varchar(255) DEFAULT NULL,
  `englishName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attr_feature_info`
--

LOCK TABLES `attr_feature_info` WRITE;
/*!40000 ALTER TABLE `attr_feature_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `attr_feature_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection_attr_char_rel`
--

DROP TABLE IF EXISTS `collection_attr_char_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection_attr_char_rel` (
  `collectionId` varchar(255) NOT NULL,
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`collectionId`,`typeId`),
  KEY `FKhgyec5i0xmltdi50c9x039uyg` (`typeId`),
  CONSTRAINT `FKhgyec5i0xmltdi50c9x039uyg` FOREIGN KEY (`typeId`) REFERENCES `attr_char_info` (`charId`),
  CONSTRAINT `FKsxgn87ni83n0hnps09grq3k62` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection_attr_char_rel`
--

LOCK TABLES `collection_attr_char_rel` WRITE;
/*!40000 ALTER TABLE `collection_attr_char_rel` DISABLE KEYS */;
INSERT INTO `collection_attr_char_rel` VALUES ('402881e461b3c0240161b3cbaa2b000a',0),('402886fb61b740390161b75f2ce40010',0),('5b66d2cf61dbf5380161dc034d890006',0),('402881e461b3c0240161b3cbaa2b000a',1),('402886fb61b740390161b75f2ce40010',1),('402886fb61b740390161b75f2ce40010',2);
/*!40000 ALTER TABLE `collection_attr_char_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection_char_rel`
--

DROP TABLE IF EXISTS `collection_char_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection_char_rel` (
  `collectionId` varchar(255) NOT NULL,
  `charId` int(11) NOT NULL,
  PRIMARY KEY (`collectionId`,`charId`),
  KEY `FKtdou93f2ei42svsfkxiyi1pdc` (`charId`),
  CONSTRAINT `FKak7vvnxqx16tew8ecg9f2j6l7` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`),
  CONSTRAINT `FKtdou93f2ei42svsfkxiyi1pdc` FOREIGN KEY (`charId`) REFERENCES `data_set_char` (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection_char_rel`
--

LOCK TABLES `collection_char_rel` WRITE;
/*!40000 ALTER TABLE `collection_char_rel` DISABLE KEYS */;
INSERT INTO `collection_char_rel` VALUES ('402881e461b3c0240161b3cbaa2b000a',0),('402886fb61b740390161b75f2ce40010',0),('5b66d2cf61dbf5380161dc034d890006',0),('402881e461b3c0240161b3cbaa2b000a',1),('402886fb61b740390161b75f2ce40010',1);
/*!40000 ALTER TABLE `collection_char_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection_task_rel`
--

DROP TABLE IF EXISTS `collection_task_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `collection_task_rel` (
  `collectionId` varchar(255) NOT NULL,
  `typeId` int(11) NOT NULL,
  PRIMARY KEY (`collectionId`,`typeId`),
  KEY `FKdch9jjm2i7p2xehsx1bxvap7e` (`typeId`),
  CONSTRAINT `FK8hxr53r9g86pk4djw6es8jpf` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`),
  CONSTRAINT `FKdch9jjm2i7p2xehsx1bxvap7e` FOREIGN KEY (`typeId`) REFERENCES `mining_task_type` (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection_task_rel`
--

LOCK TABLES `collection_task_rel` WRITE;
/*!40000 ALTER TABLE `collection_task_rel` DISABLE KEYS */;
INSERT INTO `collection_task_rel` VALUES ('402881e461b3c0240161b3cbaa2b000a',0),('402886fb61b740390161b75f2ce40010',0),('5b66d2cf61dbf5380161dc034d890006',0),('402881e461b3c0240161b3cbaa2b000a',1),('402886fb61b740390161b75f2ce40010',1),('402886fb61b740390161b75f2ce40010',2);
/*!40000 ALTER TABLE `collection_task_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_mining_group`
--

DROP TABLE IF EXISTS `data_mining_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_mining_group` (
  `groupId` varchar(255) NOT NULL,
  `arrangementId` varchar(255) DEFAULT NULL,
  `builtTime` datetime DEFAULT NULL,
  `groupName` varchar(255) DEFAULT NULL,
  `taskId` varchar(255) DEFAULT NULL,
  `teacherUserId` varchar(32) DEFAULT NULL,
  `groupLeaderId` varchar(32) DEFAULT NULL,
  `studentUserId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`groupId`),
  KEY `TASK_FOREIGN_KEY` (`taskId`),
  KEY `LEADER_FK_ID` (`groupLeaderId`),
  KEY `S_BUILDER_FK_ID` (`studentUserId`),
  KEY `T_BUILDER_FK_ID` (`teacherUserId`),
  CONSTRAINT `BUILDER_FK_ID` FOREIGN KEY (`teacherUserId`) REFERENCES `user_info` (`userId`),
  CONSTRAINT `LEADER_FK_ID` FOREIGN KEY (`groupLeaderId`) REFERENCES `user_info` (`userId`),
  CONSTRAINT `S_BUILDER_FK_ID` FOREIGN KEY (`studentUserId`) REFERENCES `user_info` (`userId`),
  CONSTRAINT `TASK_FOREIGN_KEY` FOREIGN KEY (`taskId`) REFERENCES `data_mining_task` (`taskId`),
  CONSTRAINT `T_BUILDER_FK_ID` FOREIGN KEY (`teacherUserId`) REFERENCES `user_info` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_mining_group`
--

LOCK TABLES `data_mining_group` WRITE;
/*!40000 ALTER TABLE `data_mining_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_mining_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_mining_task`
--

DROP TABLE IF EXISTS `data_mining_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_mining_task` (
  `taskId` varchar(255) NOT NULL,
  `actualFinishTime` datetime DEFAULT NULL,
  `actualStartTime` datetime DEFAULT NULL,
  `arrangementId` varchar(255) DEFAULT NULL,
  `builtTime` datetime DEFAULT NULL,
  `plannedFinishTime` datetime DEFAULT NULL,
  `plannedStartTime` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `taskDescription` varchar(255) DEFAULT NULL,
  `taskName` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_mining_task`
--

LOCK TABLES `data_mining_task` WRITE;
/*!40000 ALTER TABLE `data_mining_task` DISABLE KEYS */;
INSERT INTO `data_mining_task` VALUES ('123',NULL,NULL,NULL,'2018-02-24 15:49:02','2018-02-24 15:49:22','2018-02-24 15:49:24','1',NULL,'数据挖掘任务测试1',NULL),('1234',NULL,NULL,NULL,'2018-01-27 11:44:53',NULL,NULL,'1',NULL,'数据挖掘任务测试2',NULL),('12345',NULL,NULL,NULL,'2018-02-11 11:48:30',NULL,NULL,'1',NULL,'数据挖掘任务测试3',NULL);
/*!40000 ALTER TABLE `data_mining_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_set_char`
--

DROP TABLE IF EXISTS `data_set_char`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_set_char` (
  `charId` int(11) NOT NULL,
  `abbreviation` varchar(255) DEFAULT NULL,
  `chineseName` varchar(255) DEFAULT NULL,
  `englishName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`charId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_set_char`
--

LOCK TABLES `data_set_char` WRITE;
/*!40000 ALTER TABLE `data_set_char` DISABLE KEYS */;
INSERT INTO `data_set_char` VALUES (0,NULL,'多变量','Multivariate'),(1,NULL,'单变量','Univariate'),(2,NULL,'按序','Sequential'),(3,NULL,'时序','Time-Series'),(4,NULL,'文本','Text'),(5,NULL,'畴理论','Domain-Theory');
/*!40000 ALTER TABLE `data_set_char` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_set_collection`
--

DROP TABLE IF EXISTS `data_set_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_set_collection` (
  `collectionId` varchar(255) NOT NULL,
  `collectionName` varchar(255) DEFAULT NULL,
  `dataSetFolderPath` varchar(255) DEFAULT NULL,
  `dateDonated` date DEFAULT NULL,
  `isMissingValues` varchar(255) DEFAULT NULL,
  `numberOfAttributes` bigint(20) DEFAULT NULL,
  `numberOfInstances` bigint(20) DEFAULT NULL,
  `numberOfWebHits` bigint(20) DEFAULT NULL,
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
INSERT INTO `data_set_collection` VALUES ('402881e461b3c0240161b3cbaa2b000a','Parkinson Disease Spiral Drawings Using Digitized Graphics Tablet Data Set ','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402881e461b3c0240161b3cbaa2b000a',NULL,'Yes',NULL,0,NULL,0),('402886fb61b740390161b75f2ce40010','Parkinson Disease Spiral Drawings Using Digitized Graphics Tablet Data Set ','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402886fb61b740390161b75f2ce40010',NULL,'Yes',NULL,0,NULL,5),('5b66d2cf61dbf5380161dc034d890006','测试数据集','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006',NULL,'Yes',NULL,0,NULL,0);
/*!40000 ALTER TABLE `data_set_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_set_container`
--

DROP TABLE IF EXISTS `data_set_container`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_set_container` (
  `containerId` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `fileType` varchar(255) DEFAULT NULL,
  `size` bigint(20) DEFAULT NULL,
  `collectionId` varchar(255) DEFAULT NULL,
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
INSERT INTO `data_set_container` VALUES ('402886fb61b7888f0161b7b9a82f0004',NULL,'C#程序设计第04章.pdf','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402886fb61b740390161b75f2ce40010\\C#程序设计第04章.pdf','.pdf',470974,'402886fb61b740390161b75f2ce40010'),('402886fb61b7888f0161b7b9a8940005',NULL,'C#程序设计第06章.pdf','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402886fb61b740390161b75f2ce40010\\C#程序设计第06章.pdf','.pdf',914734,'402886fb61b740390161b75f2ce40010'),('402886fb61b7888f0161b7b9a8970006',NULL,'C#程序设计第05章.pdf','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402886fb61b740390161b75f2ce40010\\C#程序设计第05章.pdf','.pdf',1043006,'402886fb61b740390161b75f2ce40010'),('402886fb61b7888f0161b7b9a8f60007',NULL,'C#程序设计第02章.pdf','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402886fb61b740390161b75f2ce40010\\C#程序设计第02章.pdf','.pdf',979903,'402886fb61b740390161b75f2ce40010'),('402886fb61b7888f0161b7b9a97e0008',NULL,'C#程序设计第10章.pdf','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402886fb61b740390161b75f2ce40010\\C#程序设计第10章.pdf','.pdf',753228,'402886fb61b740390161b75f2ce40010'),('5b66d2cf61dbf5380161dc02e1230000',NULL,'邓四云-工作.docx','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402881e461b3c0240161b3cbaa2b000a\\邓四云-工作.docx','.docx',91620,'402881e461b3c0240161b3cbaa2b000a'),('5b66d2cf61dbf5380161dc02e1230001',NULL,'杜稳-工作.doc','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402881e461b3c0240161b3cbaa2b000a\\杜稳-工作.doc','.doc',51200,'402881e461b3c0240161b3cbaa2b000a'),('5b66d2cf61dbf5380161dc02e1230002',NULL,'黄杰华-工作.docx','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402881e461b3c0240161b3cbaa2b000a\\黄杰华-工作.docx','.docx',43225,'402881e461b3c0240161b3cbaa2b000a'),('5b66d2cf61dbf5380161dc02e1260003',NULL,'胡波-工作.pdf','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402881e461b3c0240161b3cbaa2b000a\\胡波-工作.pdf','.pdf',88192,'402881e461b3c0240161b3cbaa2b000a'),('5b66d2cf61dbf5380161dc02e1280004',NULL,'顾颖聪-工作.docx','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402881e461b3c0240161b3cbaa2b000a\\顾颖聪-工作.docx','.docx',54932,'402881e461b3c0240161b3cbaa2b000a'),('5b66d2cf61dbf5380161dc02e1350005',NULL,'孔卓-工作.doc','F:\\GitHup\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\402881e461b3c0240161b3cbaa2b000a\\孔卓-工作.doc','.doc',1725106,'402881e461b3c0240161b3cbaa2b000a'),('5b66d2cf61dbf5380161dc0550230007',NULL,'杜稳-工作.doc','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\杜稳-工作.doc','.doc',51200,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc0550230008',NULL,'邓四云-工作.docx','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\邓四云-工作.docx','.docx',91620,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc0550260009',NULL,'黄杰华-工作.docx','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\黄杰华-工作.docx','.docx',43225,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc055032000a',NULL,'胡波-工作.pdf','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\胡波-工作.pdf','.pdf',88192,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc05503c000b',NULL,'顾颖聪-工作.docx','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\顾颖聪-工作.docx','.docx',54932,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc055060000c',NULL,'林艺辉-工作.pdf','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\林艺辉-工作.pdf','.pdf',90203,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc055070000d',NULL,'李思雨-工作.pdf','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\李思雨-工作.pdf','.pdf',333564,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc055073000e',NULL,'孔卓-工作.doc','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\孔卓-工作.doc','.doc',1725106,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc05507f000f',NULL,'马宸阳-工作.doc','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\马宸阳-工作.doc','.doc',161399,'5b66d2cf61dbf5380161dc034d890006'),('5b66d2cf61dbf5380161dc05509a0010',NULL,'刘鑫容-工作.doc','D:\\GitHub\\data-minning-platform\\dm-web\\target\\classes\\data_sets\\5b66d2cf61dbf5380161dc034d890006\\刘鑫容-工作.doc','.doc',2587809,'5b66d2cf61dbf5380161dc034d890006');
/*!40000 ALTER TABLE `data_set_container` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_set_description`
--

DROP TABLE IF EXISTS `data_set_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_set_description` (
  `descriptionId` bigint(20) NOT NULL,
  `detail` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `collectionId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`descriptionId`),
  KEY `FK40gyavj7130n4k7llpa25dkgk` (`collectionId`),
  CONSTRAINT `FK40gyavj7130n4k7llpa25dkgk` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_set_description`
--

LOCK TABLES `data_set_description` WRITE;
/*!40000 ALTER TABLE `data_set_description` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_set_description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_status`
--

DROP TABLE IF EXISTS `favorite_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favorite_status` (
  `favoriteId` int(11) NOT NULL,
  `chineseValue` varchar(255) DEFAULT NULL,
  `englishValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`favoriteId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_status`
--

LOCK TABLES `favorite_status` WRITE;
/*!40000 ALTER TABLE `favorite_status` DISABLE KEYS */;
INSERT INTO `favorite_status` VALUES (0,'已收藏','favorite'),(1,'未收藏','isNotFavorite');
/*!40000 ALTER TABLE `favorite_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_stu_rel`
--

DROP TABLE IF EXISTS `favorite_stu_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `favorite_stu_rel` (
  `t_userId` varchar(32) NOT NULL,
  `s_userId` varchar(32) NOT NULL,
  PRIMARY KEY (`t_userId`,`s_userId`),
  UNIQUE KEY `UK_omrjlhc1egbvi29hbplr05mqa` (`s_userId`),
  CONSTRAINT `FKmnu7f9rs3pphpkhqex4g3v92u` FOREIGN KEY (`t_userId`) REFERENCES `user_info` (`userId`),
  CONSTRAINT `FKtpoljycfy2kr7gqfybp5tqtdn` FOREIGN KEY (`s_userId`) REFERENCES `user_info` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_stu_rel`
--

LOCK TABLES `favorite_stu_rel` WRITE;
/*!40000 ALTER TABLE `favorite_stu_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorite_stu_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `group_student_rel`
--

DROP TABLE IF EXISTS `group_student_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `group_student_rel` (
  `memberId` varchar(32) NOT NULL,
  `groupId` varchar(255) NOT NULL,
  PRIMARY KEY (`groupId`,`memberId`),
  KEY `FKsp3gskhlpxq6ciw439rc2gqwn` (`memberId`),
  CONSTRAINT `FKaau625o60q7x78amwsl15psbp` FOREIGN KEY (`groupId`) REFERENCES `data_mining_group` (`groupId`),
  CONSTRAINT `FKsp3gskhlpxq6ciw439rc2gqwn` FOREIGN KEY (`memberId`) REFERENCES `user_info` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `group_student_rel`
--

LOCK TABLES `group_student_rel` WRITE;
/*!40000 ALTER TABLE `group_student_rel` DISABLE KEYS */;
/*!40000 ALTER TABLE `group_student_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mining_task_type`
--

DROP TABLE IF EXISTS `mining_task_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mining_task_type` (
  `typeId` int(11) NOT NULL,
  `chineseName` varchar(255) DEFAULT NULL,
  `englishName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mining_task_type`
--

LOCK TABLES `mining_task_type` WRITE;
/*!40000 ALTER TABLE `mining_task_type` DISABLE KEYS */;
INSERT INTO `mining_task_type` VALUES (0,'分类','Classification'),(1,'回归','Regression'),(2,'聚类','Clustering'),(3,'其他','Other');
/*!40000 ALTER TABLE `mining_task_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission_info`
--

DROP TABLE IF EXISTS `permission_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permission_info` (
  `permissionId` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isAvailable` bit(1) DEFAULT NULL,
  `permissionName` varchar(255) NOT NULL,
  `permissions` varchar(255) NOT NULL,
  PRIMARY KEY (`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission_info`
--

LOCK TABLES `permission_info` WRITE;
/*!40000 ALTER TABLE `permission_info` DISABLE KEYS */;
INSERT INTO `permission_info` VALUES (97,'用户模块新增','','user:create',''),(98,'用户模块修改','','user:update',''),(99,'菜单模块新增','','menu:create','');
/*!40000 ALTER TABLE `permission_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_info`
--

DROP TABLE IF EXISTS `role_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_info` (
  `roleId` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isAvailable` bit(1) NOT NULL,
  `roleName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_info`
--

LOCK TABLES `role_info` WRITE;
/*!40000 ALTER TABLE `role_info` DISABLE KEYS */;
INSERT INTO `role_info` VALUES (100,'管理员','','admin'),(101,'用户管理员','','user');
/*!40000 ALTER TABLE `role_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permission_re`
--

DROP TABLE IF EXISTS `role_permission_re`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission_re` (
  `roleId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`),
  KEY `FKfomycha2oxu73lt0b3kpf1ggx` (`permissionId`),
  CONSTRAINT `FKfomycha2oxu73lt0b3kpf1ggx` FOREIGN KEY (`permissionId`) REFERENCES `permission_info` (`permissionId`),
  CONSTRAINT `FKjruhvwe23ahtsonpq4nbsygpt` FOREIGN KEY (`roleId`) REFERENCES `role_info` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission_re`
--

LOCK TABLES `role_permission_re` WRITE;
/*!40000 ALTER TABLE `role_permission_re` DISABLE KEYS */;
/*!40000 ALTER TABLE `role_permission_re` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_status`
--

DROP TABLE IF EXISTS `student_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_status` (
  `statusId` int(11) NOT NULL,
  `chineseValue` varchar(255) DEFAULT NULL,
  `englishValue` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`statusId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_status`
--

LOCK TABLES `student_status` WRITE;
/*!40000 ALTER TABLE `student_status` DISABLE KEYS */;
INSERT INTO `student_status` VALUES (0,'任务进行中','executing'),(1,'锁定','unavailable'),(2,'任务完成','finished'),(3,'空闲','available');
/*!40000 ALTER TABLE `student_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_statistics`
--

DROP TABLE IF EXISTS `sys_statistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_statistics` (
  `statisticId` int(11) NOT NULL,
  PRIMARY KEY (`statisticId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_statistics`
--

LOCK TABLES `sys_statistics` WRITE;
/*!40000 ALTER TABLE `sys_statistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_statistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_algorithm_rel`
--

DROP TABLE IF EXISTS `task_algorithm_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_algorithm_rel` (
  `taskId` varchar(255) NOT NULL,
  `algorithmId` int(11) NOT NULL,
  PRIMARY KEY (`taskId`,`algorithmId`),
  KEY `FKidgnxfx3oljco4dvwhvv8033p` (`algorithmId`),
  CONSTRAINT `FK833p07qm3ebgqb6iqu19jvm3i` FOREIGN KEY (`taskId`) REFERENCES `data_mining_task` (`taskId`),
  CONSTRAINT `FKidgnxfx3oljco4dvwhvv8033p` FOREIGN KEY (`algorithmId`) REFERENCES `algorithm_info` (`algorithmId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_algorithm_rel`
--

LOCK TABLES `task_algorithm_rel` WRITE;
/*!40000 ALTER TABLE `task_algorithm_rel` DISABLE KEYS */;
INSERT INTO `task_algorithm_rel` VALUES ('123',1),('123',2),('123',3);
/*!40000 ALTER TABLE `task_algorithm_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_data_set_rel`
--

DROP TABLE IF EXISTS `task_data_set_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_data_set_rel` (
  `taskId` varchar(255) NOT NULL,
  `collectionId` varchar(255) NOT NULL,
  PRIMARY KEY (`taskId`,`collectionId`),
  KEY `FKmppiycdgc1xd275vg4q4s3dxk` (`collectionId`),
  CONSTRAINT `FKdo9xjukrleaimjcfli9hkuncc` FOREIGN KEY (`taskId`) REFERENCES `data_mining_task` (`taskId`),
  CONSTRAINT `FKmppiycdgc1xd275vg4q4s3dxk` FOREIGN KEY (`collectionId`) REFERENCES `data_set_collection` (`collectionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_data_set_rel`
--

LOCK TABLES `task_data_set_rel` WRITE;
/*!40000 ALTER TABLE `task_data_set_rel` DISABLE KEYS */;
INSERT INTO `task_data_set_rel` VALUES ('123','5b66d2cf61dbf5380161dc034d890006');
/*!40000 ALTER TABLE `task_data_set_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_info` (
  `serviceLevel` varchar(31) NOT NULL,
  `userId` varchar(32) NOT NULL,
  `accountStatus` int(11) DEFAULT NULL,
  `avatar` longblob,
  `birthday` date DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `privateSalt` varchar(255) DEFAULT NULL,
  `publicSalt` varchar(255) DEFAULT NULL,
  `regionCode` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `className` varchar(255) DEFAULT NULL,
  `grade` varchar(255) DEFAULT NULL,
  `profession` varchar(255) DEFAULT NULL,
  `studentId` varchar(255) DEFAULT NULL,
  `studentName` varchar(255) DEFAULT NULL,
  `teacherId` varchar(255) DEFAULT NULL,
  `teacherName` varchar(255) DEFAULT NULL,
  `favoriteId` int(11) DEFAULT NULL,
  `statusId` int(11) DEFAULT NULL,
  `finishedTaskCount` int(11) DEFAULT NULL,
  `sta` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `UK_s58k9whl7u8vgxbhhm8acqg94` (`studentId`),
  UNIQUE KEY `UK_onvosn2eqrefi1qwg3k8j15um` (`teacherId`),
  KEY `FAVORITE_FOREIGN_KEY` (`favoriteId`),
  KEY `STATUS_FOREIGN_KEY` (`statusId`),
  CONSTRAINT `FAVORITE_FOREIGN_KEY` FOREIGN KEY (`favoriteId`) REFERENCES `favorite_status` (`favoriteId`),
  CONSTRAINT `STATUS_FOREIGN_KEY` FOREIGN KEY (`statusId`) REFERENCES `student_status` (`statusId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES ('Student','5b66d2cf61dc51550161dc5e2eb100a7',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2027级','软件工程','915106840339','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb100a8',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2028级','软件工程','915106840340','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb100a9',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2029级','软件工程','915106840341','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb100aa',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2030级','软件工程','915106840342','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200ab',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2031级','软件工程','915106840343','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200ac',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2032级','软件工程','915106840344','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200ad',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2033级','软件工程','915106840345','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200ae',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2034级','软件工程','915106840346','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200af',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2035级','软件工程','915106840347','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200b0',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2036级','软件工程','915106840348','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200b1',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2037级','软件工程','915106840349','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200b2',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2038级','软件工程','915106840350','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200b3',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2039级','软件工程','915106840351','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb200b4',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2040级','软件工程','915106840352','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300b5',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2041级','软件工程','915106840353','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300b6',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2042级','软件工程','915106840354','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300b7',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2043级','软件工程','915106840355','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300b8',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2044级','软件工程','915106840356','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300b9',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2045级','软件工程','915106840357','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300ba',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2046级','软件工程','915106840358','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300bb',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2047级','软件工程','915106840359','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300bc',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2048级','软件工程','915106840360','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300bd',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2049级','软件工程','915106840361','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300be',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2050级','软件工程','915106840362','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb300bf',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2051级','软件工程','915106840363','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c0',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2052级','软件工程','915106840364','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c1',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2053级','软件工程','915106840365','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c2',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2054级','软件工程','915106840366','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c3',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2055级','软件工程','915106840367','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c4',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2056级','软件工程','915106840368','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c5',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2057级','软件工程','915106840369','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c6',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2058级','软件工程','915106840370','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c7',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2059级','软件工程','915106840371','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c8',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2060级','软件工程','915106840372','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400c9',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2061级','软件工程','915106840373','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400ca',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2062级','软件工程','915106840374','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400cb',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2063级','软件工程','915106840375','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400cc',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2064级','软件工程','915106840376','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400cd',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2065级','软件工程','915106840377','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb400ce',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2066级','软件工程','915106840378','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500cf',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2067级','软件工程','915106840379','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d0',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2068级','软件工程','915106840380','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d1',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2069级','软件工程','915106840381','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d2',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2070级','软件工程','915106840382','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d3',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2071级','软件工程','915106840383','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d4',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2072级','软件工程','915106840384','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d5',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2073级','软件工程','915106840385','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d6',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2074级','软件工程','915106840386','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d7',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2075级','软件工程','915106840387','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d8',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2076级','软件工程','915106840388','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500d9',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2077级','软件工程','915106840389','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500da',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2078级','软件工程','915106840390','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500db',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2079级','软件工程','915106840391','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500dc',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2080级','软件工程','915106840392','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb500dd',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2081级','软件工程','915106840393','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600de',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2082级','软件工程','915106840394','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600df',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2083级','软件工程','915106840395','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e0',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2084级','软件工程','915106840396','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e1',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2085级','软件工程','915106840397','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e2',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2086级','软件工程','915106840398','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e3',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2087级','软件工程','915106840399','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e4',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2088级','软件工程','915106840400','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e5',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2089级','软件工程','915106840401','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e6',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2090级','软件工程','915106840402','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e7',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2091级','软件工程','915106840403','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e8',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2092级','软件工程','915106840404','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600e9',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2093级','软件工程','915106840405','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600ea',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2094级','软件工程','915106840406','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600eb',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2095级','软件工程','915106840407','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600ec',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2096级','软件工程','915106840408','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600ed',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2097级','软件工程','915106840409','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600ee',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2098级','软件工程','915106840410','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600ef',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2099级','软件工程','915106840411','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb600f0',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2100级','软件工程','915106840412','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f1',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2101级','软件工程','915106840413','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f2',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2102级','软件工程','915106840414','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f3',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2103级','软件工程','915106840415','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f4',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2104级','软件工程','915106840416','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f5',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2105级','软件工程','915106840417','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f6',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2106级','软件工程','915106840418','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f7',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2107级','软件工程','915106840419','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f8',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2108级','软件工程','915106840420','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700f9',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2109级','软件工程','915106840421','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700fa',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2110级','软件工程','915106840422','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700fb',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2111级','软件工程','915106840423','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700fc',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2112级','软件工程','915106840424','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700fd',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2113级','软件工程','915106840425','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700fe',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2114级','软件工程','915106840426','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb700ff',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2115级','软件工程','915106840427','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb70100',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2116级','软件工程','915106840428','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80101',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2117级','软件工程','915106840429','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80102',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2118级','软件工程','915106840430','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80103',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2119级','软件工程','915106840431','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80104',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2120级','软件工程','915106840432','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80105',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2121级','软件工程','915106840433','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80106',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2122级','软件工程','915106840434','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80107',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2123级','软件工程','915106840435','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80108',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2124级','软件工程','915106840436','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80109',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2125级','软件工程','915106840437','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb8010a',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2126级','软件工程','915106840438','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb8010b',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2127级','软件工程','915106840439','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb8010c',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2128级','软件工程','915106840440','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb8010d',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2129级','软件工程','915106840441','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb8010e',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2130级','软件工程','915106840442','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb8010f',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2131级','软件工程','915106840443','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb80110',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2132级','软件工程','915106840444','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90111',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2133级','软件工程','915106840445','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90112',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2134级','软件工程','915106840446','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90113',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2135级','软件工程','915106840447','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90114',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2136级','软件工程','915106840448','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90115',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2137级','软件工程','915106840449','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90116',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2138级','软件工程','915106840450','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90117',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2139级','软件工程','915106840451','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90118',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2140级','软件工程','915106840452','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb90119',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2141级','软件工程','915106840453','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb9011a',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2142级','软件工程','915106840454','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb9011b',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2143级','软件工程','915106840455','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('Student','5b66d2cf61dc51550161dc5e2eb9011c',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'软工二班','2144级','软件工程','915106840456','刘祥德',NULL,NULL,NULL,NULL,NULL,NULL),('User','8ae4820a5eb2450d015eb2453b1c0000',NULL,'data:image/jpeg;base64,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',NULL,'男','为天地立心,为生民立命,为往圣继绝。','2017-09-29 13:03:55','未闻奇楠','1PW8FVuWiKdO633zz6l5sD3viW4roAhYsAiOevRiChU=','3dfK2jMaxWRXMOE3k8z9T9uHrprCXzxSRYV5Xc1COic=','R9iHWr3oPS059ybg3qaMB4ojTvryzV2jZ44bksyIlp0=','450981','qq313700046',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('User','8ae4820a5eb2450d015eb2453b5c0001',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'B1ZJZca+r/cMuY1dzdpbRAg0LMkNu030evlqcWViRuQ=','a7z+i1rsNhBAGd0xLfex9z+Nml7IJMpTPyVVOYWgBBI=','hdF5CY6ofOn1HwrSGHXHrpoeD/x+k0RgkFyr9NiZ6pY=',NULL,'li',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('User','8ae4820a5eb2450d015eb2453b7b0002',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'P42X57p8/lgYd9WueYUOF4S8PA5F0G9JQnhfyka0qWs=','fzYcGZb+XzK4hWQVxETubEAfr7XDdYEzAWP06VtjY4Y=','ijiUOKyNtDJmHsRFD2RRTMpqySo6hKqOsL7ZDsA2x0o=',NULL,'wu',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),('User','8ae4820a5eb2450d015eb2453b9b0003',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'NeFOeFWt//kg7iVnmDenMopccHhg4d29luuuwvDXfp4=','GuFdJfWK8dNiNUtRswr4JLWzFEQZqrHPEnmbRb232fY=','egJsf+pHfV1BmO1kirSEvbXhkJgkZHeSFHMUj3UCqyQ=',NULL,'wang',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_re`
--

DROP TABLE IF EXISTS `user_role_re`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_re` (
  `userId` varchar(32) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  PRIMARY KEY (`userId`,`roleId`),
  KEY `FKlpntrxj3k2touihec453gvgqw` (`roleId`),
  CONSTRAINT `FKctw1d0pbo86kw4vflc7t68w44` FOREIGN KEY (`userId`) REFERENCES `user_info` (`userId`),
  CONSTRAINT `FKlpntrxj3k2touihec453gvgqw` FOREIGN KEY (`roleId`) REFERENCES `role_info` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_re`
--

LOCK TABLES `user_role_re` WRITE;
/*!40000 ALTER TABLE `user_role_re` DISABLE KEYS */;
INSERT INTO `user_role_re` VALUES ('8ae4820a5eb2450d015eb2453b1c0000',100);
/*!40000 ALTER TABLE `user_role_re` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-28 22:56:46
