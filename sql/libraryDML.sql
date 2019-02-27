-- Insert into library.user
INSERT INTO `user` (`user_username`, `user_password`, `user_type`, `user_token`)
VALUES ('admin', 'admin', 1, NULL);
INSERT INTO `config` (`config_key`, `config_value`)
VALUES ('startTime', '2019-01-11 00:00:00');
INSERT INTO `config` (`config_key`, `config_value`)
VALUES ('endTime', '2019-02-28 00:00:00');
