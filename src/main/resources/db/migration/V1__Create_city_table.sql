/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50643
Source Host           : localhost:3306
Source Database       : metropolis

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

Date: 2019-09-26 16:12:18
*/

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
) ENGINE=InnoDB AUTO_INCREMENT=412417 DEFAULT CHARSET=utf8;
