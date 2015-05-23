--liquibase formatted sql

--changeset josdem:1
CREATE TABLE `BANK_CODE` (
  `bankCode` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`bankCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:2
CREATE TABLE `BULK_UNIT_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:3
CREATE TABLE `USER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT '0.00',
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:4
CREATE TABLE `USER_ACCOUNT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankCode` int(11) DEFAULT NULL,
  `clabe` varchar(255) DEFAULT NULL,
  `stpClabe` varchar(255) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sd2qlubqt0ci9spck8fnax8or` (`userId`),
  CONSTRAINT `FK_sd2qlubqt0ci9spck8fnax8or` FOREIGN KEY (`userId`) REFERENCES `USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  --changeset josdem:5
CREATE TABLE `TRAMA_ACCOUNT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT '0.00',
  `clabe` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:6
CREATE TABLE `PROJECT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `audioPublic` int(11) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `cast` text,
  `description` text,
  `imagePublic` int(11) DEFAULT NULL,
  `inclosure` varchar(255) DEFAULT NULL,
  `infoPublic` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `showground` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `subcategory` int(11) DEFAULT NULL,
  `timeCreated` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `videoPublic` int(11) DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7ymmrbat9kyxqxoixnsf8p7pe` (`userId`),
  CONSTRAINT `FK_7ymmrbat9kyxqxoixnsf8p7pe` FOREIGN KEY (`userId`) REFERENCES `USER` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:7
CREATE TABLE `PROJECT_CATEGORY` (
  `id` int(11) NOT NULL,
  `father` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:8
CREATE TABLE `PROJECT_FINANCIAL_DATA` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(255) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT '0.00',
  `breakeven` decimal(38,2) DEFAULT '0.00',
  `budget` decimal(38,2) DEFAULT '0.00',
  `eventCode` varchar(255) DEFAULT NULL,
  `fundEndDate` bigint(20) DEFAULT NULL,
  `fundStartDate` bigint(20) DEFAULT NULL,
  `numberPublic` int(11) DEFAULT NULL,
  `premiereEndDate` bigint(20) DEFAULT NULL,
  `premiereStartDate` bigint(20) DEFAULT NULL,
  `productionStartDate` bigint(20) DEFAULT NULL,
  `revenuePotential` decimal(38,2) DEFAULT '0.00',
  `tramaFee` decimal(38,2) DEFAULT '0.00',
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sln4u2cos66u0ukyqybflifd` (`projectId`),
  CONSTRAINT `FK_sln4u2cos66u0ukyqybflifd` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:9
CREATE TABLE `PROJECT_BUSINESS_CASE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_aq50qy3ghy0emnt7yq7y7nb4r` (`projectId`),
  CONSTRAINT `FK_aq50qy3ghy0emnt7yq7y7nb4r` FOREIGN KEY (`projectId`) REFERENCES `PROJECT_FINANCIAL_DATA` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:10
CREATE TABLE `PROJECT_LOG` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` text,
  `reason` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fqepas589rpe3j2r4fdiu7vwq` (`projectId`),
  CONSTRAINT `FK_fqepas589rpe3j2r4fdiu7vwq` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:11
CREATE TABLE `PROJECT_PHOTO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_f37b36pbxam25jw4930ft5rwh` (`projectId`),
  CONSTRAINT `FK_f37b36pbxam25jw4930ft5rwh` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:12
CREATE TABLE `PROJECT_RATE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score` int(11) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fo5ndxq9er6wpu4dnvy0gq7ik` (`projectId`),
  CONSTRAINT `FK_fo5ndxq9er6wpu4dnvy0gq7ik` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:13
CREATE TABLE `PROJECT_SOUNDCLOUD` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_n8u4fv8urcvyufj2fpkx1iv59` (`projectId`),
  CONSTRAINT `FK_n8u4fv8urcvyufj2fpkx1iv59` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:14
CREATE TABLE `PROJECT_TAG` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3wtxhvx7qivypw4ou9wm50ytu` (`projectId`),
  CONSTRAINT `FK_3wtxhvx7qivypw4ou9wm50ytu` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:15
CREATE TABLE `PROJECT_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT '0.00',
  `uuid` varchar(255) DEFAULT NULL,
  `projectUuid` varchar(255) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:16
CREATE TABLE `PROJECT_UNITSALE` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `codeSection` varchar(255) DEFAULT NULL,
  `section` varchar(255) DEFAULT NULL,
  `unit` int(11) DEFAULT NULL,
  `unitSale` decimal(38,2) DEFAULT '0.00',
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_okgofwltlkva6dmsqh406fyn5` (`projectId`),
  CONSTRAINT `FK_okgofwltlkva6dmsqh406fyn5` FOREIGN KEY (`projectId`) REFERENCES `PROJECT_FINANCIAL_DATA` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:17
CREATE TABLE `PROJECT_VARIABLE_COST` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `value` decimal(38,2) DEFAULT '0.00',
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_7myyrsno4xb35pn7xx003xou7` (`projectId`),
  CONSTRAINT `FK_7myyrsno4xb35pn7xx003xou7` FOREIGN KEY (`projectId`) REFERENCES `PROJECT_FINANCIAL_DATA` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:18
CREATE TABLE `PROJECT_VIDEO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `projectId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_j5io172hdcqigmnt4w4xs01jl` (`projectId`),
  CONSTRAINT `FK_j5io172hdcqigmnt4w4xs01jl` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--changeset josdem:19
CREATE TABLE `PROVIDER_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT '0.00',
  `paymentType` int(11) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `projectUuid` varchar(255) DEFAULT NULL,
  `providerUuid` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:20
CREATE TABLE `SESSION_KEY` (
  `apiKey` varchar(255) NOT NULL,
  `keyStatus` int(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`apiKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:21
CREATE TABLE `STP_LOG_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `claveRastreo` int(11) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `speiId` int(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:22
CREATE TABLE `STP_STATUS` (
  `id` int(11) NOT NULL,
  `amount` decimal(38,2) DEFAULT '0.00',
  `claveRastreo` int(11) DEFAULT NULL,
  `confirmationTimestamp` bigint(20) DEFAULT NULL,
  `createdTimestamp` bigint(20) DEFAULT NULL,
  `estado` int(11) DEFAULT NULL,
  `serverTimestamp` bigint(20) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:23
CREATE TABLE `TRAMA_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT '0.00',
  `entityUuid` varchar(255) DEFAULT NULL,
  `entityType` int(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:24
CREATE TABLE `UNIT_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `projectUnitSaleId` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  `bulkId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_lnqhfr1gg9vg6t9vgntsub6yj` (`bulkId`),
  CONSTRAINT `FK_lnqhfr1gg9vg6t9vgntsub6yj` FOREIGN KEY (`bulkId`) REFERENCES `BULK_UNIT_TX` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--changeset josdem:25
CREATE TABLE `USER_TRANSFER_LIMIT` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT '0.00',
  `uuid` varchar(255) DEFAULT NULL,
  `userUuid` varchar(255) DEFAULT NULL,
  `destinationUuid` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:26
CREATE TABLE `USER_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) DEFAULT '0.00',
  `reference` varchar(255) DEFAULT NULL,
  `senderUuid` varchar(255) DEFAULT NULL,
  `projectUuid` varchar(255) DEFAULT NULL,
  `receiverUuid` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:27
CREATE TABLE `PROJECT_PROVIDER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `providerUuid` varchar(255) DEFAULT NULL,
  `advanceDate` bigint(20) DEFAULT NULL,
  `advanceFundingDate` bigint(20) DEFAULT NULL,
  `advancePaidDate` bigint(20) DEFAULT NULL,
  `advanceQuantity` decimal(38,2) DEFAULT '0.00',
  `settlementDate` bigint(20) DEFAULT NULL,
  `settlementFundingDate` bigint(20) DEFAULT NULL,
  `settlementPaidDate` bigint(20) DEFAULT NULL,
  `settlementQuantity` decimal(38,2) DEFAULT '0.00',
  `projectId` int(11) DEFAULT NULL,
  `providerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3bdvf6tox37g720goeg9xkq8i` (`projectId`),
  KEY `FK_53rdris4n15yv7441xwdwagpx` (`providerId`),
  CONSTRAINT `FK_3bdvf6tox37g720goeg9xkq8i` FOREIGN KEY (`projectId`) REFERENCES `PROJECT` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:28
CREATE TABLE `INTEGRA_USER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `balance` decimal(38,2) DEFAULT '0.00',
  `integraUuid` varchar(255) DEFAULT NULL,
  `account` varchar(255) DEFAULT NULL,
  `stpClabe` varchar(255) DEFAULT NULL,
  `timoneUuid` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--changeset josdem:29
CREATE TABLE `INTEGRA_USER_TX` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `amount` decimal(38,2) DEFAULT '0.00',
  `reference` varchar(255) DEFAULT NULL,
  `timestamp` bigint(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;