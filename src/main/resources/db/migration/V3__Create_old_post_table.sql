/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50643
Source Host           : localhost:3306
Source Database       : metropolis

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

Date: 2019-09-30 08:37:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_old_post
-- ----------------------------
DROP TABLE IF EXISTS `tbl_old_post`;
CREATE TABLE `tbl_old_post` (
  `old_post_id` int(11) NOT NULL AUTO_INCREMENT,
  `old_post_code` varchar(5) NOT NULL,
  PRIMARY KEY (`old_post_id`),
  UNIQUE KEY `old_post_code` (`old_post_code`)
) ENGINE=InnoDB AUTO_INCREMENT=16383 DEFAULT CHARSET=utf8;
