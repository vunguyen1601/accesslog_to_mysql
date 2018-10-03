-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host:     Database: testdb
-- ------------------------------------------------------
-- Server version	5.6.36

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
-- Table structure for table `blocked`
--

DROP TABLE IF EXISTS `blocked`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blocked` (
  `iID` int(11) NOT NULL AUTO_INCREMENT,
  `date_created` datetime NOT NULL,
  `decription` varchar(255) NOT NULL,
  `IP` varchar(45) NOT NULL,
  PRIMARY KEY (`iID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blocked`
--

LOCK TABLES `blocked` WRITE;
/*!40000 ALTER TABLE `blocked` DISABLE KEYS */;
/*!40000 ALTER TABLE `blocked` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logrecords`
--

DROP TABLE IF EXISTS `logrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logrecords` (
  `iID` int(11) NOT NULL AUTO_INCREMENT,
  `IP` varchar(45) DEFAULT NULL,
  `request` varchar(100) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `useragent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`iID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logrecords`
--

LOCK TABLES `logrecords` WRITE;
/*!40000 ALTER TABLE `logrecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `logrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'testdb'
--

--
-- Dumping routines for database 'testdb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-03 11:49:39
