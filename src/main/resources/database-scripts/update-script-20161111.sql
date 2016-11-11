LOCK TABLES `configurations` WRITE;
INSERT INTO `medical-manager`.`configurations` (`key_col`, `value_col`) VALUES ('genericEmailAddress', 'no-reply@gpmate.co.uk');
UNLOCK TABLES;