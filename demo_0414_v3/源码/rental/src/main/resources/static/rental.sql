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
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '车辆状态。0：未加入网点；1：已加入网点；2：被使用中（已加入网点）；',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Data for the table `car` */

insert  into `car`(`cid`,`maker`,`model`,`type`,`product_time`,`rent`,`cicon`,`create_time`,`update_time`,`disable`,`status`) values (1,'ford','fox','Compact car','2018-03-11 00:00:00','180','/imgs/a29c7a51-2944-4949-95b0-4e6669f0fc21.jpg','2019-04-07 16:44:13','2019-04-07 16:44:13',0,1),(2,'Volkswagen','polo','Small car','2019-04-07 16:48:47','200','/imgs/VolkswagenPolo.jpg','2019-04-07 16:48:47','2019-04-07 16:48:47',0,1),(3,'Volkswagen','LaVida','Compact car','2018-03-11 00:00:00','200','/imgs/VolkswagenLaVida.jpg','2019-04-07 16:50:09','2019-04-07 16:50:09',0,1),(4,'Audi','Q3','Compact SUV','2019-04-07 16:53:09','300','/imgs/AudiQ3.jpg','2019-04-07 16:53:09','2019-04-07 16:53:09',0,0),(5,'BMW','5Series','Medium and large','2019-04-07 16:54:21','300','/imgs/BMW5Series.jpg','2019-04-07 16:54:21','2019-04-07 16:54:21',0,1),(6,'BMW','X3','Medium SUV','2019-04-07 16:55:02','350','/imgs/BMWX3.jpg','2019-04-07 16:55:02','2019-04-07 16:55:02',0,1),(8,'Toyota','Hanlanda','SUV','2017-08-06 00:00:00','110','/imgs/4321dded-420c-4174-ab72-07857e666e20.jpg','2019-04-07 18:12:08','2019-04-07 18:12:08',0,1),(9,'Toyota','Camry','Medium car','2018-05-06 00:00:00','110','/imgs/962b497a-2e05-4ff8-a2cd-c9f1c2a959af.jpg','2019-04-07 21:45:12','2019-04-07 21:45:12',0,1),(10,'Toyota','Camry','Medium car','2018-05-06 00:00:00','110','/imgs/322dcb87-021c-4a34-aabd-6e41286477e2.jpg','2019-04-07 21:45:36','2019-04-07 21:45:36',0,1),(11,'ford','polo','small','2019-04-04 00:00:00','200','/imgs/a6e4ccaa-bf47-4b0b-97e9-468e02cc9e7b.jpg','2019-04-08 00:41:26','2019-04-08 00:41:26',0,1),(12,'BMW','3 series','SUV','2019-04-01 00:00:00','20','/imgs/9d470162-4d78-4644-86ab-9dd6d991cdb0.jpg','2019-04-08 00:44:25','2019-04-08 00:44:25',0,1),(13,'ford','Q','SUV','2019-04-07 00:00:00','200','/imgs/7bc1ff42-7c2b-4505-aefa-54bd2034fcf9.jpg','2019-04-09 00:04:10','2019-04-09 00:04:10',0,1),(14,'ford','12series','SUV','2019-04-07 00:00:00','123','/imgs/bf36a514-e45f-4d12-95ff-65f6efdb5484.jpg','2019-04-11 23:37:07','2019-04-11 23:37:07',0,1),(15,'Audi','polo','small','2019-04-07 00:00:00','123','/imgs/7a12983e-886a-4e39-ab98-7ba514e24c31.jpg','2019-04-11 23:37:23','2019-04-11 23:37:23',0,1);

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `order_id` varchar(32) NOT NULL COMMENT '订单编号',
  `comment_score` int(11) DEFAULT NULL COMMENT '订单评分',
  `comment_desc` varchar(255) DEFAULT NULL COMMENT '订单服务评价',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `comment` */

insert  into `comment`(`order_id`,`comment_score`,`comment_desc`) values ('1555077051572603443',85,'Not bad');

/*Table structure for table `location` */

DROP TABLE IF EXISTS `location`;

CREATE TABLE `location` (
  `location_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '网点编号',
  `latlng` varchar(255) DEFAULT NULL COMMENT '网点经纬度',
  `location_name` varchar(255) DEFAULT NULL COMMENT '网点名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`location_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `location` */

insert  into `location`(`location_id`,`latlng`,`location_name`,`create_time`,`update_time`) values (1,'-37.793015887528156,144.93263840675357','Advanced Vetcare','2019-04-12 00:15:38','2019-04-14 22:27:27'),(2,'-37.78704484217024,144.92791637778285 ','Woolworths Newmarket Plaza','2019-04-09 20:52:08','2019-04-14 22:28:28'),(3,'37.74726690097809,144.92099829018116','Glenbervie','2019-04-09 20:53:08','2019-04-14 22:29:31'),(4,'-37.74370692997668,144.91070330142978','Coles Essendon','2019-04-09 20:55:35','2019-04-14 22:30:14'),(5,'-37.753751186167285,144.95844125747684','John Fawkner Private Hospital','2019-04-09 20:57:10','2019-04-14 22:31:03'),(6,'-37.76966311138132,144.96174573898318','Savers','2019-04-12 00:16:01','2019-04-14 22:31:38'),(7,'-37.78136586046713,144.89708304405142','WE EREW','2019-04-12 00:16:32','2019-04-14 19:26:13'),(8,'-37.628745811432196,144.94803696870807','BP','2019-04-09 21:25:20','2019-04-14 19:26:18');

/*Table structure for table `location_car` */

DROP TABLE IF EXISTS `location_car`;

CREATE TABLE `location_car` (
  `location_id` int(11) NOT NULL COMMENT '网点编号',
  `cid` int(11) NOT NULL COMMENT '车辆编号',
  PRIMARY KEY (`location_id`,`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `location_car` */

insert  into `location_car`(`location_id`,`cid`) values (1,2),(2,3),(2,5),(2,8),(2,13),(3,9),(4,10),(5,14),(7,15),(8,1),(8,6),(8,11),(8,12);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` varchar(32) NOT NULL COMMENT '订单编号',
  `order_amount` decimal(10,0) NOT NULL COMMENT '订单金额',
  `user_id` int(11) NOT NULL COMMENT '客户编号',
  `user_name` varchar(64) DEFAULT NULL COMMENT '客户名称',
  `user_phone` varchar(32) DEFAULT NULL COMMENT '客户电话',
  `user_address` varchar(255) DEFAULT NULL COMMENT '客户地址',
  `car_id` int(11) DEFAULT NULL COMMENT '车辆编号',
  `car_maker` varchar(64) DEFAULT NULL COMMENT '车辆制造商',
  `car_type` varchar(64) DEFAULT NULL COMMENT '车辆类型',
  `pickup_location` varchar(255) DEFAULT NULL COMMENT '取车地址',
  `pickup_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '取车时间',
  `dropoff_location` varchar(255) DEFAULT NULL COMMENT '还车地址',
  `dropoff_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '预定还车时间',
  `order_status` int(11) NOT NULL DEFAULT '0' COMMENT '订单状态。0：取车中；1：使用中；2：已还车,订单完结；3:取消订单。',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单修改时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`order_id`,`order_amount`,`user_id`,`user_name`,`user_phone`,`user_address`,`car_id`,`car_maker`,`car_type`,`pickup_location`,`pickup_time`,`dropoff_location`,`dropoff_time`,`order_status`,`create_time`,`update_time`) values ('1555076483446350015','4800',4,'demo','13355556666','301 Hampshire Rd, Sunshine VIC 3020 Australia',2,'Volkswagen','Small car','Super Market','2019-04-13 08:00:00','Abbe Corrugated','2019-04-14 08:00:00',0,'2019-04-12 21:41:23','2019-04-13 20:52:41'),('1555076948795257348','200',5,'test','13355556666','1040 Mt Alexander Rd, Essendon VIC 3040 Australia',2,'Volkswagen','Small car','Super Market','2019-04-12 21:49:09','Abbe Corrugated','2019-04-12 21:49:09',0,'2019-04-12 21:49:09','2019-04-13 20:44:16'),('1555076987224355673','200',2,'jack','13355556666','1040 Mt Alexander Rd, Essendon VIC 3040 Australia',2,'Volkswagen','Small car','Normandby Dve Post Box','2019-04-12 21:49:47','Abbe Corrugated','2019-04-12 21:49:47',3,'2019-04-12 21:49:47','2019-04-14 13:58:37'),('1555077017881194546','200',5,'test','13355556666','1040 Mt Alexander Rd, Essendon VIC 3040 Australia',2,'Volkswagen','Small car','Super Market','2019-04-12 21:50:18','Abbe Corrugated','2019-04-12 21:50:18',0,'2019-04-12 21:50:18','2019-04-12 23:41:02'),('1555077051572603443','2000',2,'jack','13355556667','328 Swanston St, Melbourne VIC 3000 Australia',13,'ford','SUV','Melbourne ShowGrounds','2019-04-14 10:10:00','Abbe Corrugated','2019-04-14 20:10:00',0,'2019-04-12 21:50:52','2019-04-14 16:07:48'),('1555159756004496412','2000',2,'jack','13355556666','328 Swanston St, Melbourne VIC 3000 Australia',3,'Volkswagen','Compact car','Melbourne ShowGrounds','2019-04-13 08:00:00','Super Market','2019-04-13 18:00:00',0,'2019-04-13 20:49:16','2019-04-13 20:49:16'),('1555160020473464556','2200',2,'jack','15574223387','212 King St, Melbourne VIC 3000 Australia',10,'Toyota','Medium car','Abbe Corrugated','2019-04-13 04:00:00','Footscray Hospital','2019-04-14 00:00:00',0,'2019-04-13 20:53:40','2019-04-13 23:48:44'),('1555255388134103960','360',2,'jack','13355556667','328 Swanston St, Melbourne VIC 3000 Australia',1,'ford','Compact car','BP','2019-04-14 08:08:00','Coles Essendon','2019-04-14 10:08:00',0,'2019-04-14 23:23:08','2019-04-14 23:23:08');

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

insert  into `user`(`id`,`id_card`,`username`,`password`,`phone`,`address`,`credit`,`is_blacklist`,`create_time`,`last_login_time`,`icon`) values (1,'666666200103315225','admin','123','17788889999','180 St Kilda Rd, Melbourne VIC 3006 Australia',80,0,'2019-03-30 15:31:08','2019-04-14 23:33:30',' '),(2,'333333199909090373','jack','123','13355556667','328 Swanston St, Melbourne VIC 3000 Australia',80,0,'2019-03-30 15:30:33','2019-04-14 23:33:49',' '),(3,'111111666666888888','admin2','123','13355556666','328 Swanston St, Melbourne VIC 3000 Australia',80,0,'2019-04-01 23:23:37','2019-04-13 23:18:30',' '),(4,'111111666666888888','demo','123','13355556666','301 Hampshire Rd, Sunshine VIC 3020 Australia',80,0,'2019-04-07 13:01:59','2019-04-13 21:36:10',' '),(5,'555555666666777777','test','123','13355556666','1040 Mt Alexander Rd, Essendon VIC 3040 Australia',80,0,'2019-04-07 15:32:46','2019-04-14 18:20:45',' ');

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `uid` int(11) NOT NULL COMMENT '用户id',
  `rid` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`uid`,`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

insert  into `user_role`(`uid`,`rid`) values (1,1),(2,2),(3,2),(4,2),(5,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
