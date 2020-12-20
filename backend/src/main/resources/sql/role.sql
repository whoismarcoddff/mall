DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

INSERT INTO `role` VALUES (1,'2020-12-13 10:07:04',NULL,'2020-12-13 10:07:04',NULL,'User','USER'),(2,'2020-12-13 10:07:04',NULL,'2020-12-13 10:07:04',NULL,'Temporary user','TEMP_USER'),(3,'2020-12-13 10:07:04',NULL,'2020-12-13 10:07:04',NULL,'Manager','MANAGER'),(4,'2020-12-13 10:07:04',NULL,'2020-12-13 10:07:04',NULL,'Admin','ADMIN');