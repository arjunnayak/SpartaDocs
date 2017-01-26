-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: cs160
-- ------------------------------------------------------
-- Server version	5.7.15-log

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
-- Table structure for table `advisorinbox`
--

DROP TABLE IF EXISTS `advisorinbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advisorinbox` (
  `Email` varchar(255) NOT NULL,
  `DocID` int(11) NOT NULL,
  `Date` datetime NOT NULL,
  `StudentEmail` varchar(255) NOT NULL,
  `DateSubmitted` datetime NOT NULL,
  PRIMARY KEY (`Email`,`DocID`,`Date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisorinbox`
--

LOCK TABLES `advisorinbox` WRITE;
/*!40000 ALTER TABLE `advisorinbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `advisorinbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `advisors`
--

DROP TABLE IF EXISTS `advisors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advisors` (
  `Email` varchar(255) NOT NULL,
  `Password` varchar(45) NOT NULL,
  `FName` varchar(255) NOT NULL,
  `LName` varchar(255) NOT NULL,
  `SID` varchar(255) NOT NULL,
  `Verified` enum('T','F') NOT NULL,
  `MajorID` int(11) NOT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `advisors`
--

LOCK TABLES `advisors` WRITE;
/*!40000 ALTER TABLE `advisors` DISABLE KEYS */;
/*!40000 ALTER TABLE `advisors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `DocID` int(11) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Date` datetime NOT NULL,
  `Message` varchar(255) NOT NULL,
  `Role` enum('Advisor','Student') NOT NULL,
  `DocDate` datetime DEFAULT NULL,
  PRIMARY KEY (`DocID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `DocID` int(11) NOT NULL,
  `Date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DocLocation` varchar(255) NOT NULL,
  `PreviousDate` datetime DEFAULT NULL,
  `DocName` varchar(45) NOT NULL,
  `Status` enum('IN PROGRESS','PENDING','APPROVED','REJECTED') NOT NULL DEFAULT 'IN PROGRESS',
  `AdvisorEmail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`DocID`,`Date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (1,'2016-11-17 19:02:25','%PROGRAM_FOLDER%\\src\\main\\resources\\documents\\department\\CS\\CSHoldReleaseBase.json','2016-11-17 19:02:25','Computer Science Hold Release Form','IN PROGRESS',NULL),(1,'2016-11-25 15:38:28','Doc 1','2016-11-25 15:38:28','Computer Science Hold Release Form','IN PROGRESS',NULL),(1,'2016-11-25 15:38:34','Doc 2','2016-11-25 15:38:34','Computer Science Hold Release Form','IN PROGRESS',NULL),(1,'2016-11-25 15:38:39','Doc 3','2016-11-25 15:38:39','Computer Science Hold Release Form','IN PROGRESS',NULL),(2,'2016-12-04 20:31:52',';Student;frank.daniels@sjsu.edu;1480912311854Computer Science Graduation Form',NULL,'Computer Science Graduation Form','IN PROGRESS',NULL),(3,'2016-12-04 23:18:32',';Student;frank.daniels@sjsu.edu;1480922311560Computer Science Graduation Form',NULL,'Computer Science Graduation Form','IN PROGRESS',NULL);
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major`
--

DROP TABLE IF EXISTS `major`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `major` (
  `MajorID` int(11) NOT NULL AUTO_INCREMENT,
  `MajorName` varchar(45) NOT NULL,
  `DeanEmail` varchar(255) DEFAULT NULL,
  `Location` varchar(255) NOT NULL,
  `Phone` varchar(45) NOT NULL,
  PRIMARY KEY (`MajorID`),
  KEY `DeanToMajor_idx` (`DeanEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major`
--

LOCK TABLES `major` WRITE;
/*!40000 ALTER TABLE `major` DISABLE KEYS */;
INSERT INTO `major` VALUES (1,'Computer Science',NULL,'208 MacQuarrie Hall','(408) 924-5060');
/*!40000 ALTER TABLE `major` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `majoradvisors`
--

DROP TABLE IF EXISTS `majoradvisors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `majoradvisors` (
  `AdvisorEmail` varchar(255) NOT NULL,
  `MajorID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `majoradvisors`
--

LOCK TABLES `majoradvisors` WRITE;
/*!40000 ALTER TABLE `majoradvisors` DISABLE KEYS */;
/*!40000 ALTER TABLE `majoradvisors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `majordocuments`
--

DROP TABLE IF EXISTS `majordocuments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `majordocuments` (
  `MajorID` int(11) NOT NULL,
  `DocID` int(11) NOT NULL,
  PRIMARY KEY (`MajorID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `majordocuments`
--

LOCK TABLES `majordocuments` WRITE;
/*!40000 ALTER TABLE `majordocuments` DISABLE KEYS */;
/*!40000 ALTER TABLE `majordocuments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studentinbox`
--

DROP TABLE IF EXISTS `studentinbox`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studentinbox` (
  `Email` varchar(255) NOT NULL,
  `DocID` int(11) NOT NULL,
  `Date` datetime NOT NULL,
  `DateChecked` datetime NOT NULL,
  PRIMARY KEY (`Email`,`DocID`,`Date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studentinbox`
--

LOCK TABLES `studentinbox` WRITE;
/*!40000 ALTER TABLE `studentinbox` DISABLE KEYS */;
/*!40000 ALTER TABLE `studentinbox` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `FName` varchar(255) NOT NULL,
  `LName` varchar(255) NOT NULL,
  `SID` varchar(45) NOT NULL,
  `Verified` enum('T','F') NOT NULL,
  `AdvisorEmail` varchar(255) DEFAULT NULL,
  `MajorID` int(11) NOT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('frank.daniels@sjsu.edu','feebd73caf8c8934042f51f73d77ed54','Frank','Daniels','12345','T',NULL,1);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userdocuments`
--

DROP TABLE IF EXISTS `userdocuments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userdocuments` (
  `UserEmail` varchar(255) NOT NULL,
  `DocID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdocuments`
--

LOCK TABLES `userdocuments` WRITE;
/*!40000 ALTER TABLE `userdocuments` DISABLE KEYS */;
INSERT INTO `userdocuments` VALUES ('frank.daniels@sjsu.edu',2),('frank.daniels@sjsu.edu',3);
/*!40000 ALTER TABLE `userdocuments` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-05  0:00:47
