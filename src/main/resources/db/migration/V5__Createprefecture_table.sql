/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50643
Source Host           : localhost:3306
Source Database       : metropolis

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

Date: 2019-09-30 08:37:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_prefecture
-- ----------------------------
DROP TABLE IF EXISTS `tbl_prefecture`;
CREATE TABLE `tbl_prefecture` (
  `prefecture_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '都道府県ID',
  `prefecture_kana` varchar(100) NOT NULL,
  `prefecture` varchar(100) NOT NULL,
  `prefecture_code` varchar(2) NOT NULL,
  PRIMARY KEY (`prefecture_id`),
  UNIQUE KEY `prefecture_kana` (`prefecture_kana`,`prefecture`,`prefecture_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2223 DEFAULT CHARSET=utf8;
