SET NAMES utf8mb4;

-- 商品属性分类
DROP TABLE IF EXISTS `pms_product_attribute_category`;
CREATE TABLE `pms_product_attribute_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL COMMENT '分类名称',
  `attribute_count` int DEFAULT 0 COMMENT '属性数量',
  `param_count` int DEFAULT 0 COMMENT '参数数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性分类';

-- 商品属性
DROP TABLE IF EXISTS `pms_product_attribute`;
CREATE TABLE `pms_product_attribute` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_attribute_category_id` bigint DEFAULT NULL COMMENT '属性分类ID',
  `name` varchar(64) DEFAULT NULL COMMENT '属性名称',
  `select_type` int DEFAULT NULL COMMENT '可选值选择方式：0->唯一；1->单选；2->多选',
  `input_type` int DEFAULT NULL COMMENT '录入方式：0->手工录入；1->从列表中选取',
  `input_list` varchar(255) DEFAULT NULL COMMENT '可选值列表，逗号分隔',
  `sort` int DEFAULT 0 COMMENT '排序',
  `filter_type` int DEFAULT 0 COMMENT '是否支持筛选：0->否；1->是',
  `search_type` int DEFAULT 0 COMMENT '搜索方式：0->不需要；1->关键字；2->范围',
  `related_status` int DEFAULT 0 COMMENT '关联展示：0->否；1->是',
  `hand_add_status` int DEFAULT 0 COMMENT '是否支持手动新增：0->否；1->是',
  `type` int DEFAULT NULL COMMENT '类型：0->规格；1->参数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性';

-- 商品-属性值关联
DROP TABLE IF EXISTS `pms_product_attribute_value`;
CREATE TABLE `pms_product_attribute_value` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL COMMENT '商品ID',
  `product_attribute_id` bigint DEFAULT NULL COMMENT '属性ID',
  `value` varchar(64) DEFAULT NULL COMMENT '属性值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品属性值';

-- SKU 库存
DROP TABLE IF EXISTS `pms_sku_stock`;
CREATE TABLE `pms_sku_stock` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `product_id` bigint DEFAULT NULL COMMENT '商品ID',
  `sku_code` varchar(64) DEFAULT NULL COMMENT 'SKU编码',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `stock` int DEFAULT 0 COMMENT '库存',
  `low_stock` int DEFAULT 0 COMMENT '预警库存',
  `sale` int DEFAULT 0 COMMENT '销量',
  `promotion_price` decimal(10,2) DEFAULT NULL COMMENT '促销价',
  `lock_stock` int DEFAULT 0 COMMENT '锁定库存',
  `sp_data` varchar(500) DEFAULT NULL COMMENT '规格值JSON',
  `pic` varchar(255) DEFAULT NULL COMMENT 'SKU图片',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SKU库存';

-- 测试数据
INSERT INTO `pms_product_attribute_category` (`id`, `name`, `attribute_count`, `param_count`) VALUES
(1, '服装', 0, 0),
(2, '手机数码', 0, 0);

INSERT INTO `pms_product_attribute` (`id`, `product_attribute_category_id`, `name`, `select_type`, `input_type`, `input_list`, `sort`, `filter_type`, `search_type`, `related_status`, `hand_add_status`, `type`) VALUES
(1, 1, '颜色', 1, 1, '红色,蓝色,黑色,白色', 0, 1, 0, 1, 1, 0),
(2, 1, '尺码', 1, 1, 'S,M,L,XL,XXL', 1, 1, 0, 1, 1, 0),
(3, 2, '屏幕尺寸', 1, 1, '5.5英寸,6.1英寸,6.5英寸', 0, 0, 1, 1, 1, 1);

INSERT INTO `pms_product_attribute_value` (`product_id`, `product_attribute_id`, `value`) VALUES
(4, 1, '陶瓷白'),
(4, 2, '8GB+256GB');

INSERT INTO `pms_sku_stock` (`product_id`, `sku_code`, `price`, `stock`, `low_stock`, `sale`, `promotion_price`, `lock_stock`, `sp_data`, `pic`) VALUES
(4, 'SKU001', 5299.00, 100, 10, 0, NULL, 0, '{"颜色":"陶瓷白","版本":"8GB+256GB"}', ''),
(4, 'SKU002', 5999.00, 50, 10, 0, NULL, 0, '{"颜色":"陶瓷黑","版本":"12GB+512GB"}', '');
