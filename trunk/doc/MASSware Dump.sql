CREATE DATABASE  IF NOT EXISTS `cs160` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `cs160`;
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
-- Table structure for table `advisors`
--

DROP TABLE IF EXISTS `advisors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `advisors` (
  `Email` varchar(255) NOT NULL,
  `FName` varchar(255) NOT NULL,
  `LName` varchar(255) NOT NULL,
  `MajorID` int(11) NOT NULL,
  `Contact Info` varchar(255) NOT NULL,
  PRIMARY KEY (`Email`),
  KEY `AdvisorToMajor_idx` (`MajorID`),
  CONSTRAINT `AdvisorToMajor` FOREIGN KEY (`MajorID`) REFERENCES `major` (`MajorID`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
INSERT INTO `documents` VALUES (1,'2016-12-17 23:11:38',';Student;frank.daniels@sjsu.edu;1482045097864Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(2,'2016-12-17 23:11:57',';Student;frank.daniels@sjsu.edu;1482045117041Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(3,'2016-12-17 23:19:35',';Student;frank.daniels@sjsu.edu;1482045575021Computer Science Graduation Form',NULL,'Computer Science Graduation Form','IN PROGRESS',NULL),(4,'2016-12-17 23:21:35',';Student;jack.daniels@sjsu.edu;1482045694791Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(5,'2016-12-17 23:24:35',';Student;malik.khalil@sjsu.edu;1482045875364Computer Science Graduation Form',NULL,'Computer Science Graduation Form','IN PROGRESS',NULL),(6,'2016-12-17 23:24:52',';Student;malik.khalil@sjsu.edu;1482045891650Computer Science Graduation Form',NULL,'Computer Science Graduation Form','IN PROGRESS',NULL),(7,'2016-12-17 23:27:20',';Student;arjun.nayak@sjsu.edu;1482046040482Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(8,'2016-12-17 23:27:53',';Student;arjun.nayak@sjsu.edu;1482046073281Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(9,'2016-12-17 23:28:20',';Student;arjun.nayak@sjsu.edu;1482046099928Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(10,'2016-12-17 23:42:14',';Student;frank.daniels@sjsu.edu;1482046933622Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(11,'2016-12-17 23:43:27',';Student;frank.daniels@sjsu.edu;1482047007409Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(12,'2016-12-17 23:44:49',';Student;jack.daniels@sjsu.edu;1482047088853Computer Science Graduation Form',NULL,'Computer Science Graduation Form','IN PROGRESS',NULL),(13,'2016-12-17 23:46:24',';Student;jack.daniels@sjsu.edu;1482047183529Computer Science Graduation Form',NULL,'Computer Science Graduation Form','IN PROGRESS',NULL),(14,'2016-12-17 23:47:42',';Student;malik.khalil@sjsu.edu;1482047262114Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL),(15,'2016-12-17 23:49:16',';Student;malik.khalil@sjsu.edu;1482047355595Computer Science Hold Release Form',NULL,'Computer Science Hold Release Form','IN PROGRESS',NULL);
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
  `MajorID` int(11) NOT NULL,
  KEY `MajorToAdvisors_idx` (`MajorID`),
  KEY `AdvisorsToMajor_idx` (`AdvisorEmail`),
  CONSTRAINT `AdvisorsToMajor` FOREIGN KEY (`AdvisorEmail`) REFERENCES `advisors` (`Email`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `MajorToAdvisors` FOREIGN KEY (`MajorID`) REFERENCES `major` (`MajorID`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  PRIMARY KEY (`MajorID`),
  KEY `DocumentToMajor_idx` (`DocID`),
  CONSTRAINT `DocumentToMajor` FOREIGN KEY (`DocID`) REFERENCES `documents` (`DocID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `MajorToDocument` FOREIGN KEY (`MajorID`) REFERENCES `major` (`MajorID`) ON DELETE NO ACTION ON UPDATE NO ACTION
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
  PRIMARY KEY (`Email`),
  KEY `StudentToMajor_idx` (`MajorID`),
  CONSTRAINT `StudentToMajor` FOREIGN KEY (`MajorID`) REFERENCES `major` (`MajorID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES ('arjun.nayak@sjsu.edu','482c811da5d5b4bc6d497ffa98491e38','Arjun','Nayak','34567','T',NULL,1),('frank.daniels@sjsu.edu','0444863cae2e1298ca8f9e38ca252578','Frank','Daniels','123','T',NULL,1),('jack.daniels@sjsu.edu','5f4dcc3b5aa765d61d8327deb882cf99','Jack','Daniels','12345','T',NULL,1),('malik.khalil@sjsu.edu','5f4dcc3b5aa765d61d8327deb882cf99','Malik','Khalil','23456','T',NULL,1);
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
  `DocID` int(11) NOT NULL,
  KEY `UserToDocument_idx` (`UserEmail`),
  KEY `DocumentToUser_idx` (`DocID`),
  CONSTRAINT `DocumentToUser` FOREIGN KEY (`DocID`) REFERENCES `documents` (`DocID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `UserToDocument` FOREIGN KEY (`UserEmail`) REFERENCES `students` (`Email`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userdocuments`
--

LOCK TABLES `userdocuments` WRITE;
/*!40000 ALTER TABLE `userdocuments` DISABLE KEYS */;
INSERT INTO `userdocuments` VALUES ('frank.daniels@sjsu.edu',10),('frank.daniels@sjsu.edu',11),('jack.daniels@sjsu.edu',12),('jack.daniels@sjsu.edu',13),('malik.khalil@sjsu.edu',14),('malik.khalil@sjsu.edu',15);
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

-- Dump completed on 2016-12-18  0:05:31
