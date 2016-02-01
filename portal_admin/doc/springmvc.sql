/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50508
Source Host           : localhost:3306
Source Database       : springmvc

Target Server Type    : MYSQL
Target Server Version : 50508
File Encoding         : 65001

Date: 2012-02-09 14:56:01
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `example`
-- ----------------------------
DROP TABLE IF EXISTS `example`;
CREATE TABLE `example` (
  `ID` int(255) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) DEFAULT NULL,
  `BIRTHDAY` date DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of example
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_info`;
CREATE TABLE `tb_info` (
  `info_id` int(10) NOT NULL AUTO_INCREMENT,
  `info1` varchar(100) DEFAULT NULL,
  `info2` varchar(100) DEFAULT NULL,
  `info3` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_info
-- ----------------------------
INSERT INTO tb_info VALUES ('1', 'a1', 'a2', 'a3');
INSERT INTO tb_info VALUES ('2', 'b1', 'b2', 'b3');
INSERT INTO tb_info VALUES ('3', 'c1', 'c2', 'c3');
INSERT INTO tb_info VALUES ('4', 'd1', 'd2', 'd3');
INSERT INTO tb_info VALUES ('5', 'e1', 'e2', 'e3');
INSERT INTO tb_info VALUES ('6', 'f1', 'f2', 'f3');
INSERT INTO tb_info VALUES ('7', 'g1', 'g2', 'g3');
INSERT INTO tb_info VALUES ('8', 'h1', 'h2', 'h3');
INSERT INTO tb_info VALUES ('9', 'i1', 'i2', 'i3');
INSERT INTO tb_info VALUES ('10', 'j1', 'j2', 'j3');
INSERT INTO tb_info VALUES ('11', 'k1', 'k2', 'k3');
INSERT INTO tb_info VALUES ('12', 'l1', 'l2', 'l3');
INSERT INTO tb_info VALUES ('13', 'm1', 'm2', 'm3');
INSERT INTO tb_info VALUES ('14', 'n1', 'n2', 'n3');
INSERT INTO tb_info VALUES ('15', 'o1', 'o2', 'o3');
INSERT INTO tb_info VALUES ('16', 'p1', 'p2', 'p3');
INSERT INTO tb_info VALUES ('17', 'q1', 'q2', 'q3');
INSERT INTO tb_info VALUES ('18', 'r1', 'r2', 'r3');
INSERT INTO tb_info VALUES ('19', 's1', 's2', 's3');
INSERT INTO tb_info VALUES ('20', 't1', 't2', 't3');
INSERT INTO tb_info VALUES ('21', 'u1', 'u2', 'u3');
INSERT INTO tb_info VALUES ('22', 'v1', 'v2', 'v3');
INSERT INTO tb_info VALUES ('23', 'w1', 'w2', 'w3');
INSERT INTO tb_info VALUES ('24', 'x1', 'x2', 'x3');
INSERT INTO tb_info VALUES ('25', 'y1', 'y2', 'y3');
INSERT INTO tb_info VALUES ('26', 'z1', 'z2', 'z3');

-- ----------------------------
-- Table structure for `tb_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `menu_id` int(10) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(20) DEFAULT NULL,
  `menu_url` varchar(100) DEFAULT NULL,
  `parent_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------
INSERT INTO tb_menu VALUES ('2', '样板模块', '', null);
INSERT INTO tb_menu VALUES ('16', '系统管理', '', null);
INSERT INTO tb_menu VALUES ('17', '用户管理', 'user.html', '16');
INSERT INTO tb_menu VALUES ('18', '角色管理', 'role.html', '16');
INSERT INTO tb_menu VALUES ('19', '菜单管理', 'menu.html', '16');
INSERT INTO tb_menu VALUES ('20', '样板功能', 'example.html', '2');

-- ----------------------------
-- Table structure for `tb_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `role_id` int(10) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL,
  `rights` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO tb_role VALUES ('1', '系统管理员', '35822');
INSERT INTO tb_role VALUES ('2', '普通用户', '2038792');
INSERT INTO tb_role VALUES ('3', '系统用户', '230374');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` int(10) NOT NULL AUTO_INCREMENT,
  `loginname` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  `rights` varchar(100) DEFAULT NULL,
  `status` tinyint(1) DEFAULT NULL,
  `role_id` int(10) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO tb_user VALUES ('1', 'admin', 'admin', '管理员', '2070510', '0', '1', '2012-01-04 10:13:38');
INSERT INTO tb_user VALUES ('2', 'user1', '111111', '用户A1a', '40716', '0', '2', null);
INSERT INTO tb_user VALUES ('3', 'user2', '111111', '用户B', null, '0', null, '2011-06-13 15:39:08');
INSERT INTO tb_user VALUES ('4', 'user3', '111111', '用户3', null, '0', null, '2011-06-13 15:35:42');
INSERT INTO tb_user VALUES ('5', 'aaa', '1111', 'aaa', null, '0', null, null);
INSERT INTO tb_user VALUES ('6', 'bbb', '111111', 'dsfdsf2', null, '0', '2', null);
INSERT INTO tb_user VALUES ('7', 'fffff', '1', 'ddds', null, '0', null, null);
INSERT INTO tb_user VALUES ('10', 'abc', '111111', 'dsfdsf2x', null, '0', null, null);
INSERT INTO tb_user VALUES ('11', 'user4', '111111', 'afdsf', null, '0', '1', null);
INSERT INTO tb_user VALUES ('20', 'admin1', 'admin', 'wo de tian', null, '0', '1', null);
INSERT INTO tb_user VALUES ('21', '1231', '123', '123', null, '0', '1', null);
INSERT INTO tb_user VALUES ('22', '123123', '123', '123', null, '0', '1', null);
INSERT INTO tb_user VALUES ('23', '12313123', '123', '123', null, '0', '1', null);
