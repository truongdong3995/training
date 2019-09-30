/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50643
Source Host           : localhost:3306
Source Database       : metropolis

Target Server Type    : MYSQL
Target Server Version : 50643
File Encoding         : 65001

Date: 2019-09-30 08:37:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_post
-- ----------------------------
DROP TABLE IF EXISTS `tbl_post`;
CREATE TABLE `tbl_post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_code` varchar(7) NOT NULL,
  `update_show` int(11) NOT NULL DEFAULT '0',
  `change_reason` int(11) NOT NULL DEFAULT '0',
  `multi_area` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`post_id`),
  UNIQUE KEY `post_code` (`post_code`)
) ENGINE=InnoDB AUTO_INCREMENT=393220 DEFAULT CHARSET=utf8;
