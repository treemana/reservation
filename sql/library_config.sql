
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `config_system_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '配置的id',
  `config_key` VARCHAR(10) DEFAULT NULL COMMENT '配置的key',
  `config_value` VARCHAR(255) DEFAULT NULL  COMMENT '配置的value（时间）',
  PRIMARY KEY (`config_system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;