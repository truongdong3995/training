SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_area
-- ----------------------------
DROP TABLE IF EXISTS `tbl_area`;
CREATE TABLE `tbl_area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `area_kana` longtext NOT NULL,
  `area` longtext NOT NULL,
  `city_id` int(11) NOT NULL,
  `chome_area` int(11) NOT NULL DEFAULT '0',
  `koaza_area` int(11) NOT NULL DEFAULT '0',
  `multi_post_area` int(11) NOT NULL DEFAULT '0',
  `post_id` int(11) NOT NULL,
  `old_post_id` int(11) NOT NULL,
  PRIMARY KEY (`area_id`),
  KEY `old_post_id` (`old_post_id`),
  KEY `post_id` (`post_id`),
  KEY `city_id` (`city_id`),
  CONSTRAINT `city_id` FOREIGN KEY (`city_id`) REFERENCES `tbl_city` (`city_id`),
  CONSTRAINT `tbl_area_ibfk_1` FOREIGN KEY (`post_id`) REFERENCES `tbl_post` (`post_id`),
  CONSTRAINT `tbl_area_ibfk_2` FOREIGN KEY (`old_post_id`) REFERENCES `tbl_old_post` (`old_post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
