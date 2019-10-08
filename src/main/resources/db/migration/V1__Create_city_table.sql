SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_city
-- ----------------------------
DROP TABLE IF EXISTS `tbl_city`;
CREATE TABLE `tbl_city` (
  `city_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(5) NOT NULL,
  `city_kana` varchar(100) NOT NULL,
  `city` varchar(100) NOT NULL,
  `prefecture_id` int(11) NOT NULL,
  PRIMARY KEY (`city_id`),
  UNIQUE KEY `code` (`city_kana`,`city`,`prefecture_id`) USING BTREE,
  UNIQUE KEY `code1` (`code`),
  KEY `prefecture_id` (`prefecture_id`),
  CONSTRAINT `prefecture_id` FOREIGN KEY (`prefecture_id`) REFERENCES `tbl_prefecture` (`prefecture_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
