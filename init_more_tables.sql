SET NAMES utf8mb4;

-- 商品分类
DROP TABLE IF EXISTS `pms_product_category`;
CREATE TABLE `pms_product_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT 0 COMMENT '上级分类id，0表示一级',
  `name` varchar(64) DEFAULT NULL,
  `level` int DEFAULT NULL COMMENT '分类级别：0->1级；1->2级',
  `product_count` int DEFAULT 0,
  `product_unit` varchar(255) DEFAULT NULL,
  `nav_status` int DEFAULT 1 COMMENT '是否显示在导航栏：0->不显示；1->显示',
  `show_status` int DEFAULT 1 COMMENT '显示状态：0->不显示；1->显示',
  `sort` int DEFAULT 0,
  `icon` varchar(255) DEFAULT NULL,
  `keywords` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类';

-- 角色
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int DEFAULT 0 COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int DEFAULT 1 COMMENT '启用状态：0->禁用；1->启用',
  `sort` int DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 菜单
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT 0 COMMENT '父级ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int DEFAULT NULL COMMENT '菜单级数',
  `sort` int DEFAULT 0 COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int DEFAULT 0 COMMENT '前端隐藏：0->否；1->是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单表';

-- 订单
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_sn` varchar(64) DEFAULT NULL COMMENT '订单编号',
  `member_id` bigint DEFAULT NULL,
  `member_username` varchar(64) DEFAULT NULL COMMENT '用户账号',
  `total_amount` decimal(10,2) DEFAULT NULL COMMENT '订单总金额',
  `pay_amount` decimal(10,2) DEFAULT NULL COMMENT '应付金额',
  `pay_type` int DEFAULT 0 COMMENT '支付方式：0->未支付；1->支付宝；2->微信',
  `status` int DEFAULT 0 COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭',
  `create_time` datetime DEFAULT NULL COMMENT '提交时间',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '收货人',
  `receiver_phone` varchar(32) DEFAULT NULL COMMENT '收货人电话',
  `receiver_address` varchar(500) DEFAULT NULL COMMENT '收货地址',
  `delivery_company` varchar(64) DEFAULT NULL COMMENT '物流公司',
  `delivery_sn` varchar(64) DEFAULT NULL COMMENT '物流单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单项
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_id` bigint DEFAULT NULL,
  `order_sn` varchar(64) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `product_name` varchar(200) DEFAULT NULL,
  `product_pic` varchar(500) DEFAULT NULL,
  `product_price` decimal(10,2) DEFAULT NULL,
  `product_quantity` int DEFAULT 1,
  `product_sn` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单项表';

-- 测试数据
INSERT INTO `pms_product_category` (`id`, `parent_id`, `name`, `level`, `sort`) VALUES
(1, 0, '服装', 0, 0),
(2, 0, '手机数码', 0, 1),
(3, 1, '男装', 1, 0),
(4, 1, '女装', 1, 1),
(5, 2, '手机', 1, 0);

INSERT INTO `ums_role` (`id`, `name`, `description`, `create_time`, `status`) VALUES
(1, '超级管理员', '拥有所有权限', NOW(), 1),
(2, '商品管理员', '负责商品管理', NOW(), 1),
(3, '订单管理员', '负责订单管理', NOW(), 1);

INSERT INTO `ums_menu` (`id`, `parent_id`, `create_time`, `title`, `level`, `sort`, `name`, `icon`) VALUES
(1, 0, NOW(), '商品管理', 0, 0, 'pms', 'Goods'),
(2, 0, NOW(), '用户管理', 0, 1, 'ums', 'User'),
(3, 0, NOW(), '订单管理', 0, 2, 'oms', 'List'),
(4, 1, NOW(), '商品列表', 1, 0, 'product', 'Goods'),
(5, 1, NOW(), '品牌管理', 1, 1, 'brand', 'GoodsFilled'),
(6, 1, NOW(), '商品分类', 1, 2, 'category', 'Folder'),
(7, 2, NOW(), '管理员', 1, 0, 'admin', 'UserFilled'),
(8, 2, NOW(), '角色', 1, 1, 'role', 'Avatar'),
(9, 3, NOW(), '订单列表', 1, 0, 'order', 'List');

INSERT INTO `oms_order` (`id`, `order_sn`, `member_id`, `member_username`, `total_amount`, `pay_amount`, `pay_type`, `status`, `create_time`, `receiver_name`, `receiver_phone`, `receiver_address`) VALUES
(1, '20241027001', 1, 'testuser', 5398.00, 5398.00, 1, 2, '2024-10-25 10:00:00', '张三', '13800138000', '北京市朝阳区建国路88号'),
(2, '20241027002', 2, 'testuser2', 298.00, 298.00, 2, 1, '2024-10-26 14:30:00', '李四', '13900139000', '上海市浦东新区世纪大道100号'),
(3, '20241027003', 1, 'testuser', 99.00, 99.00, 0, 0, '2024-10-27 09:00:00', '王五', '13700137000', '广州市天河区天河路100号');

INSERT INTO `oms_order_item` (`order_id`, `order_sn`, `product_id`, `product_name`, `product_price`, `product_quantity`) VALUES
(1, '20241027001', 4, '小米10 至尊纪念版', 5299.00, 1),
(1, '20241027001', 6, '海澜之家休闲裤', 199.00, 1),
(2, '20241027002', 6, '海澜之家休闲裤', 199.00, 1),
(2, '20241027002', 5, '七匹狼短袖T恤', 99.00, 1),
(3, '20241027003', 5, '七匹狼短袖T恤', 99.00, 1);
