DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_system_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'user表的id',
  `user_username` CHAR(10) NOT NULL COMMENT '学生端为学号，教师端为账号',
  `user_password` VARCHAR(10) NOT NULL COMMENT '密码',
  `user_type` TINYINT UNSIGNED DEFAULT '0' COMMENT '用户的状态，黑名单的同学为2，老师设置为1，学生设置为0，默认为0',
  `user_token` VARCHAR(255) NOT NULL COMMENT '令牌',
  PRIMARY KEY (`user_system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;