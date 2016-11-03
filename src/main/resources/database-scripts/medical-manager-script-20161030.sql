DROP TABLE IF EXISTS `configurations`;
CREATE TABLE `configurations`
(
`id` BIGINT(20) NOT NULL AUTO_INCREMENT,
`key_col` VARCHAR(255) NOT NULL,
`value_col` VARCHAR(255),
PRIMARY KEY (`id`)
);
