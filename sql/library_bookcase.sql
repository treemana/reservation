DROP TABLE IF EXISTS `bookcase`;
CREATE TABLE `bookcase` (
  `bc_system_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '表的id',
  `bc_location` TINYINT UNSIGNED NOT NULL COMMENT '书柜的位置1，2，3，4代表不同的区域',
  `bc_number` INT UNSIGNED NOT NULL COMMENT '书包柜的编号',
  `bc_user_id` INT UNSIGNED DEFAULT NULL COMMENT '书包柜对应的学生的id',
  `bc_status` TINYINT UNSIGNED NOT NULL DEFAULT '0' COMMENT '书包柜的状态 默认0为可选',
  PRIMARY KEY (`bc_system_id`),
  INDEX `idx_n` (`bc_number`),
  INDEX `idx_l` (`bc_location`),
  INDEX `idx_u` (`bc_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;