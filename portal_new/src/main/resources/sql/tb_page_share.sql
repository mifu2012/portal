/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.15
Source Server Version : 50521
Source Host           : 192.168.0.15:3306
Source Database       : portal

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2012-05-30 15:34:45
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `tb_page_share`
-- ----------------------------
DROP TABLE IF EXISTS `tb_page_share`;
CREATE TABLE `tb_page_share` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SEND_USER` varchar(255) DEFAULT NULL,
  `SEND_USER_NAME` varchar(2000) DEFAULT NULL,
  `RECEIVE_USER` varchar(255) DEFAULT NULL COMMENT '如果是发送，则接收人可能有多个；如果是接收，接收人字段为一个（当前接收人）',
  `RECEIVE_USER_NAME` varchar(2000) DEFAULT NULL,
  `URL` varchar(500) DEFAULT NULL,
  `IS_READED` int(11) DEFAULT NULL COMMENT '只对接收有效，发送状态无效',
  `REMARK` varchar(2000) DEFAULT NULL,
  `FLAG` int(11) DEFAULT NULL COMMENT '0：发送 1：接收',
  `GMT_SEND` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ORIGIN_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='共享一URL,，则保存一条发送记录；另保存N条（N个人）接收记录';

-- ----------------------------
-- Records of tb_page_share
-- ----------------------------
INSERT INTO tb_page_share VALUES ('28', '1', '管理员', '14', 'a', 'http://localhost:9999/portal_new//productHealth/showProductHealth?productId=2002&menuId=1_03&crtYearMonth=2011-10&nextYearMonth=2011-11&productId=2002&queryDate=2011-10-01&queryMonth=2011-10', '1', '健康度页面', '1', '2012-05-30 15:39:44', '27');
INSERT INTO tb_page_share VALUES ('29', '14', 'a', '1', '管理员', 'http://localhost:9999/portal_new//productHealth/showProductHealth?productId=2002&menuId=1_03&crtYearMonth=2011-10&nextYearMonth=2011-11&productId=2002&queryDate=2011-10-01&queryMonth=2011-10', null, '哦，深深地', '0', '2012-05-30 15:40:30', '27');
INSERT INTO tb_page_share VALUES ('31', '14', 'a', '1', '管理员', 'http://localhost:9999/portal_new/userExperience/showUserExperience.html?1=1&menuId=1_0303&productId=2002&queryDate=2011-10-01&queryMonth=2011-10', null, '用户体验页面', '0', '2012-05-30 15:41:57', '-1');
INSERT INTO tb_page_share VALUES ('32', '14', 'a', '1', '管理员', 'http://localhost:9999/portal_new/userExperience/showUserExperience.html?1=1&menuId=1_0303&productId=2002&queryDate=2011-10-01&queryMonth=2011-10', '0', '用户体验页面', '1', '2012-05-30 15:41:57', '31');
