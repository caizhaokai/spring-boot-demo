DROP TABLE IF EXISTS `sys_identity`;
DROP TABLE IF EXISTS `sys_login`;
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_identity` (
  `t_id` varchar(32) NOT NULL COMMENT 'ID编号',
  `t_id_type` varchar(2) NOT NULL COMMENT '证件类型',
  `t_id_no` varchar(64) NOT NULL COMMENT '证件号码',
  PRIMARY KEY (`t_id`,`t_id_type`,`t_id_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_login` (
  `t_id` varchar(32) NOT NULL COMMENT 'ID编号',
  `t_username` varchar(400) DEFAULT NULL COMMENT '登录名',
  `t_password` varchar(400) DEFAULT NULL COMMENT '登录密码',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sys_user` (
  `t_id` varchar(32) NOT NULL COMMENT 'ID编号',
  `t_name` varchar(300) DEFAULT NULL COMMENT '用户姓名',
  `t_age` int(11) DEFAULT NULL COMMENT '用户年龄',
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
