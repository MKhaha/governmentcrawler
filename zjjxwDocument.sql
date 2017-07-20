

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `zjjxw_documnet`
-- ----------------------------
DROP TABLE IF EXISTS `zjjxw_documnet`;
CREATE TABLE `zjjxw_documnet` (
  `id` Int NOT NULL AUTO_INCREMENT COMMENT  '文件id',
  `title` VARCHAR(500) NOT NULL COMMENT '文件标题',
  `url` VARCHAR(500) NOT NULL COMMENT '文件网址',
  `date` DATE DEFAULT NULL COMMENT '文章发表时间',
  `url_attachment` VARCHAR(200) DEFAULT NULL COMMENT '文章附件的地址',
  `content` TEXT DEFAULT NULL COMMENT '文章内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `url_unique` (`url`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS = 1;