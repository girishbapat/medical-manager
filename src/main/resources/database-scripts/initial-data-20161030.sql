LOCK TABLES `configurations` WRITE;
INSERT INTO `medical-manager`.`configurations` (`key_col`, `value_col`) VALUES ('maxNoOfProblemBoxes', '4');
INSERT INTO `medical-manager`.`configurations` (`key_col`, `value_col`) VALUES ('timeInMinutes', '15');
UNLOCK TABLES;
