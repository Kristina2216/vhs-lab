DROP SCHEMA IF EXISTS `vhs_db`;

CREATE SCHEMA `vhs_db`;

use `vhs_db`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `authorities`;
DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) UNIQUE NOT NULL,
  `password` char(128) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `users` (`username`, `password`, `enabled`) 
VALUES 
('john','{bcrypt}$2a$12$W0SQYNnzaI4Z8v/qlw/6KO.wzRdfENYNWB/nEmjv9fjFiAR9tP15q',1),
('mary','{bcrypt}$2a$12$EHrEkkczVNglu5nGgLBKTOG0VingXZqWYyVPd3TIAl/BWFbhh2lvK',1),
('susan','{bcrypt}$2a$12$S7ie3Z24qF.rq8Jab9nvzeJN8mBaxaVtTNymbMCHx0spVYf7DliqW',1);


CREATE TABLE `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `authorities4_idx_1` (`username`,`authority`),
  CONSTRAINT `authorities4_ibfk_1` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `authorities` 
VALUES 
('john','ROLE_CUSTOMER'),
('mary','ROLE_CUSTOMER'),
('susan','ROLE_ADMIN');



CREATE TABLE `vhs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `genre` varchar(128) DEFAULT NULL,
  `is_available` tinyint(1) DEFAULT '1' NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `TITLE_UNIQUE` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


INSERT INTO `vhs` (`title`, `genre`, `is_available`)
VALUES 
('Back To The Future','Sci-Fi', 0),
('Dune','Sci-Fi, Action', 1),
('Forrest Gump','Drama, Romance', 1),
('The Shawshank Redemption','Drama', 0),
('12 Angry Men','Crime, Drama', 1),
('Pulp Fiction','Crime, Drama', 0),
('Fight Club','Drama', 1),
('Late Night with the Devil','Horror', 1),
('The Zone of Interest','Drama, History, War', 1),
('Poor Things','Comedy, Drama, Romance', 1),
('All of Us Strangers','Drama, Romance', 1),
('The Matrix','Action, Sci-Fi', 1),
('Fallen Leaves','Comedy, Drama, Romance', 0),
('Anatomy of a Fall','Crime, Drama, Thriller', 1),
('The French Dispatch','Comedy, Drama, Romance', 0),
('The Banshees of Inisherin','Comedy, Drama', 1),
('Killers of the Flower Moon','Crime, Drama, History', 1),
('Asteroid City','Comedy, Drama, Romance', 1);


CREATE TABLE `rental` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `vhs_id` int NOT NULL,
  `rental_date` date,
  `due_date` date,

  PRIMARY KEY (`id`),

  CONSTRAINT `FK_VHS_05` FOREIGN KEY (`vhs_id`)
  REFERENCES `vhs` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT `FK_USERS` FOREIGN KEY (`user_id`)
  REFERENCES `users` (`id`)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


INSERT INTO `rental` (`user_id`, `vhs_id`, `rental_date`, `due_date`)
VALUES 
(1, 13, '2024-07-08', '2024-07-09'),
(2, 1, '2024-07-12', '2024-07-13'),
(2, 4, '2024-08-05', '2024-08-06'),
(2, 15, '2024-07-11', '2024-07-12'),
(1, 6, '2024-07-15', '2024-07-16'),
(1, 11, '2024-07-27', '2024-07-28'),
(1, 4, '2024-07-28', '2024-07-29'),
(2, 11, '2024-07-24', '2024-07-25');

SET FOREIGN_KEY_CHECKS = 1;
