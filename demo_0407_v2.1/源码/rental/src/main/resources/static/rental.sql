/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.6.24 : Database - rental
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`rental` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `rental`;

/*Table structure for table `car` */

DROP TABLE IF EXISTS `car`;

CREATE TABLE `car` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '车辆编号',
  `maker` varchar(32) DEFAULT NULL COMMENT '车辆制造商',
  `model` varchar(64) DEFAULT NULL COMMENT '型号',
  `type` varchar(32) DEFAULT NULL COMMENT '车型。小型、中型、大型、豪华型',
  `product_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '生产时间',
  `rent` decimal(10,0) DEFAULT '0' COMMENT '租金/天',
  `cicon` varchar(255) DEFAULT NULL COMMENT '汽车图片',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '信息插入时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '信息修改时间',
  `disable` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否禁用。1：禁用；0：可用',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Data for the table `car` */

insert  into `car`(`cid`,`maker`,`model`,`type`,`product_time`,`rent`,`cicon`,`create_time`,`update_time`,`disable`) values (1,'ford','fox','Compact car','2018-03-11 00:00:00','180','/imgs/a29c7a51-2944-4949-95b0-4e6669f0fc21.jpg','2019-04-07 16:44:13','2019-04-07 16:44:13',0),(2,'Volkswagen','polo','Small car','2019-04-07 16:48:47','200','/imgs/VolkswagenPolo.jpg','2019-04-07 16:48:47','2019-04-07 16:48:47',0),(3,'Volkswagen','LaVida','Compact car','2018-03-11 00:00:00','200','/imgs/VolkswagenLaVida.jpg','2019-04-07 16:50:09','2019-04-07 16:50:09',0),(4,'Audi','Q3','Compact SUV','2019-04-07 16:53:09','300','/imgs/AudiQ3.jpg','2019-04-07 16:53:09','2019-04-07 16:53:09',0),(5,'BMW','5Series','Medium and large','2019-04-07 16:54:21','300','/imgs/BMW5Series.jpg','2019-04-07 16:54:21','2019-04-07 16:54:21',0),(6,'BMW','X3','Medium SUV','2019-04-07 16:55:02','350','/imgs/BMWX3.jpg','2019-04-07 16:55:02','2019-04-07 16:55:02',1),(8,'Toyota','Hanlanda','SUV','2017-08-06 00:00:00','110','/imgs/4321dded-420c-4174-ab72-07857e666e20.jpg','2019-04-07 18:12:08','2019-04-07 18:12:08',0),(9,'Toyota','Camry','Medium car','2018-05-06 00:00:00','110','/imgs/962b497a-2e05-4ff8-a2cd-c9f1c2a959af.jpg','2019-04-07 21:45:12','2019-04-07 21:45:12',0),(10,'Toyota','Camry','Medium car','2018-05-06 00:00:00','110','/imgs/322dcb87-021c-4a34-aabd-6e41286477e2.jpg','2019-04-07 21:45:36','2019-04-07 21:45:36',0),(11,'ford','polo','small','2019-04-04 00:00:00','200','/imgs/a6e4ccaa-bf47-4b0b-97e9-468e02cc9e7b.jpg','2019-04-08 00:41:26','2019-04-08 00:41:26',0),(12,'BMW','3 series','SUV','2019-04-01 00:00:00','20','/imgs/9d470162-4d78-4644-86ab-9dd6d991cdb0.jpg','2019-04-08 00:44:25','2019-04-08 00:44:25',0);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `order_id` varchar(32) NOT NULL COMMENT '订单编号',
  `order_amount` decimal(10,0) NOT NULL COMMENT '订单金额',
  `user_id` int(11) DEFAULT NULL COMMENT '客户编号',
  `user_name` varchar(64) DEFAULT NULL COMMENT '客户名称',
  `user_phone` varchar(11) DEFAULT NULL COMMENT '客户电话',
  `user_address` varchar(255) DEFAULT NULL COMMENT '客户地址',
  `car_id` int(11) DEFAULT NULL COMMENT '车辆编号',
  `car_maker` varchar(255) DEFAULT NULL COMMENT '车辆品牌：ford等',
  `car_type` varchar(255) DEFAULT NULL COMMENT '车型：小型、中型等',
  `pickup_location` varchar(255) DEFAULT NULL COMMENT '取车地址',
  `pickup_latlng` varchar(255) DEFAULT NULL COMMENT '取车经纬度',
  `pickup_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '取车时间',
  `dropoff_location` varchar(255) DEFAULT NULL COMMENT '还车地址',
  `dropoff_latlng` varchar(255) DEFAULT NULL COMMENT '还车经纬度',
  `dropoff_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '预定还车时间',
  `dropoff_real_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '实际还车时间',
  `order_status` int(11) DEFAULT NULL COMMENT '订单状态。0：取车中；1：使用中；2：已还车，订单完结。',
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '订单修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order` */

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `url` varchar(255) DEFAULT '',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `permission` */

insert  into `permission`(`pid`,`name`,`url`) values (1,'add',''),(2,'edit',''),(3,'query',''),(4,'delete','');

/*Table structure for table `permission_role` */

DROP TABLE IF EXISTS `permission_role`;

CREATE TABLE `permission_role` (
  `rid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  KEY `idx_rid` (`rid`),
  KEY `idx_pid` (`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `permission_role` */

insert  into `permission_role`(`rid`,`pid`) values (1,1),(1,2),(1,3),(1,4),(2,3);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `rid` int(11) NOT NULL COMMENT 'id',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `role` */

insert  into `role`(`rid`,`name`) values (1,'staff'),(2,'customer');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `id_card` varchar(18) NOT NULL COMMENT '用户id',
  `username` varchar(32) NOT NULL COMMENT '姓名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `phone` varchar(32) NOT NULL COMMENT '电话号码',
  `address` varchar(255) NOT NULL COMMENT '居住地址',
  `credit` int(11) NOT NULL DEFAULT '80' COMMENT '信用分数',
  `is_blacklist` tinyint(4) NOT NULL DEFAULT '0' COMMENT '拉黑标识。0：正常；1：被拉黑。',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '账号创建时间',
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后登陆时间',
  `icon` varchar(255) NOT NULL DEFAULT ' ' COMMENT '用户头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`id`,`id_card`,`username`,`password`,`phone`,`address`,`credit`,`is_blacklist`,`create_time`,`last_login_time`,`icon`) values (1,'666666200103315225','admin','123','17788889999','180 St Kilda Rd, Melbourne VIC 3006 Australia',80,0,'2019-03-30 15:31:08','2019-04-08 00:44:00',' '),(2,'333333199909090373','jack','123','13355556666','328 Swanston St, Melbourne VIC 3000 Australia',80,0,'2019-03-30 15:30:33','2019-04-07 18:29:51',' '),(3,'888888999999666666','admin2','123','15574223387','212 King St, Melbourne VIC 3000 Australia',80,0,'2019-04-01 23:23:37','2019-04-07 18:30:15',' '),(4,'111111666666888888','demo','123','13355556666','301 Hampshire Rd, Sunshine VIC 3020 Australia',80,0,'2019-04-07 13:01:59','2019-04-07 18:30:44',' '),(5,'555555666666777777','test','123','13355556666','1040 Mt Alexander Rd, Essendon VIC 3040 Australia',80,0,'2019-04-07 15:32:46','2019-04-07 18:31:07',' ');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `uid` int(11) NOT NULL COMMENT '用户id',
  `rid` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`uid`,`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`uid`,`rid`) values (1,1),(2,1),(3,2),(4,1),(5,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
