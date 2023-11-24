/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : mcrm

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 15/04/2022 17:20:28
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mdc_client
-- ----------------------------
CREATE TABLE `mdc_client`
(
    `id`              bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`      tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`    datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `name`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '客户名称',
    `source`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '来源',
    `phone_no`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '客户手机号',
    `sex`             int NULL DEFAULT NULL COMMENT '1：男，2：女',
    `id_card`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '身份证号码',
    `contact_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '联系地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdc_client_order
-- ----------------------------
CREATE TABLE `mdc_client_order`
(
    `phone_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '客户手机号',
    `order_split_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdc_goods
-- ----------------------------
CREATE TABLE `mdc_goods`
(
    `id`           bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`   tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `goods_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
    `goods_id`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
    `sku_name`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
    `sku_id`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'skuId',
    `source`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源渠道',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdc_order
-- ----------------------------
CREATE TABLE `mdc_order`
(
    `id`                 bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`         tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `order_split_id`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
    `order_status`       int NOT NULL COMMENT '三方订单状态',
    `self_order_status`  int NULL DEFAULT NULL COMMENT '系统订单状态',
    `refund_status`      int NULL DEFAULT NULL COMMENT '退款状态',
    `goods_name`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
    `goods_id`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品goodsId',
    `sku_name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
    `sku_code`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商家sku编码',
    `goods_price`        decimal(19, 4) NULL DEFAULT NULL COMMENT '商品价',
    `order_amount`       decimal(19, 4) NULL DEFAULT NULL COMMENT '订单价格（单位：分）',
    `contact_phone_no`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '联系电话',
    `user_name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '买家名称',
    `order_create_time`  datetime NULL DEFAULT NULL COMMENT '订单创建时间',
    `pay_time`           datetime NULL DEFAULT NULL COMMENT '支付时间',
    `refund_time`        datetime NULL DEFAULT NULL COMMENT '订单退款时间',
    `source`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道',
    `refund_reason`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款原因',
    `refund_amount`      decimal(19, 4) NULL DEFAULT NULL COMMENT '退款金额',
    `agreement_merchant` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '履约服务商',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for mdc_order_appoint
-- ----------------------------
CREATE TABLE `mdc_order_appoint`
(
    `id`                  bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`          tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`         datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `order_split_id`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
    `first_appoint_time`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第一针预约时间',
    `second_appoint_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第二针预约时间',
    `third_appoint_time`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三针预约时间',
    `source`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 786 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ods_bai_du_client
-- ----------------------------
CREATE TABLE `ods_bai_du_client`
(
    `id`            bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`    tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `data_time_int` bigint NULL DEFAULT NULL COMMENT '数据时间',
    `order_id`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
    `name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户姓名',
    `gender`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
    `birthday`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出生日期',
    `id_card`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号',
    `phone`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话',
    `card_type`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 198 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ods_bai_du_order
-- ----------------------------
CREATE TABLE `ods_bai_du_order`
(
    `id`                  bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`          tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`         datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `data_time_int`       bigint NULL DEFAULT NULL COMMENT '数据时间',
    `order_id`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
    `status`              int NULL DEFAULT NULL COMMENT '订单状态',
    `agree_refund_time`   datetime NULL DEFAULT NULL COMMENT '商家同意退款时间',
    `apply_refund_reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请退款原因',
    `apply_refund_time`   datetime NULL DEFAULT NULL COMMENT '申请退款时间',
    `appoint_date`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约日期',
    `appoint_mobile`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
    `appoint_user_name`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约人',
    `assign_name`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '医师姓名',
    `back_money_fen`      decimal(19, 4) NULL DEFAULT NULL COMMENT '退款金额',
    `create_time`         datetime NULL DEFAULT NULL COMMENT '创建时间',
    `goods_name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
    `goods_id`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
    `sku_name`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku名称',
    `tp_sku_code`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商家sku编码',
    `goods_price_fen`     decimal(19, 4) NULL DEFAULT NULL COMMENT '商品总价格',
    `message`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户留言',
    `original_price_fen`  decimal(19, 4) NULL DEFAULT NULL COMMENT '订单原价(分)',
    `paid_amount_fen`     decimal(19, 4) NULL DEFAULT NULL COMMENT '商家结算金额(分)',
    `bd_discount`         decimal(19, 4) NULL DEFAULT NULL COMMENT '平台补贴',
    `user_payment`        decimal(19, 4) NULL DEFAULT NULL COMMENT '用户实际支付金额',
    `pay_time`            datetime NULL DEFAULT NULL COMMENT '付款时间',
    `pay_type`            int NULL DEFAULT NULL COMMENT '支付方式',
    `sex`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货/购买人性别',
    `rece_mobile`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货/购买人手机号',
    `rece_name`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货/购买人',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 134 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for ods_ding_xiang_client
-- ----------------------------
CREATE TABLE `ods_ding_xiang_client`
(
    `id`               bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`       tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`     datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `data_time_int`    bigint NULL DEFAULT NULL COMMENT '数据时间',
    `order_split_id`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
    `name`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户姓名',
    `gender`           int NULL DEFAULT NULL COMMENT '性别',
    `domicile`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '户籍所在地',
    `contact_phone_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系电话',
    `birthday`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '出生日期',
    `contact_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系地址',
    `id_card`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号',
    `age`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '年龄',
    `detail_address`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1509041685140854377 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ods_ding_xiang_order
-- ----------------------------
CREATE TABLE `ods_ding_xiang_order`
(
    `id`                bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`        tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`       datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`      datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `order_split_id`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
    `order_item_id`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `order_status`      int NOT NULL COMMENT '订单状态',
    `pay_status`        int NOT NULL COMMENT '支付状态',
    `order_create_time` datetime NOT NULL COMMENT '订单创建时间',
    `service_service`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '预约服务',
    `specification`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '规格',
    `specification_id`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '规格id',
    `amount`            decimal(19, 4) NOT NULL COMMENT '订单金额',
    `data_time_int`     bigint NULL DEFAULT NULL COMMENT '数据时间',
    `contact_phone_no`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '联系电话',
    `order_remarks`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '下单备注',
    `reserve_info`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '预约详情',
    `operating_record`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '操作记录',
    `refund_info`       text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '退款信息',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1962 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单详情' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for ods_excel_order
-- ----------------------------
CREATE TABLE `ods_excel_order`
(
    `id`                  bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `is_deleted`          tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除：0-未删除 1-已删除',
    `gmt_created`         datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `data_time_int`       bigint NULL DEFAULT NULL COMMENT '数据时间',
    `order_code`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
    `goods_id`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品id',
    `goods_name`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
    `sku_name`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SKU名称',
    `tp_sku_code`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku编码',
    `num`                 int      NOT NULL DEFAULT 1 COMMENT '数量',
    `order_amount`        decimal(19, 4) NULL DEFAULT NULL COMMENT '订单金额',
    `user_payment`        decimal(19, 4) NULL DEFAULT NULL COMMENT '用户结算金额',
    `order_create_time`   datetime NULL DEFAULT NULL COMMENT '订单创建时间',
    `pay_time`            datetime NULL DEFAULT NULL COMMENT '付款时间',
    `first_appoint_time`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单预约时间',
    `second_appoint_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约时间二',
    `third_appoint_time`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约时间三',
    `phone_no`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预约人手机号',
    `contact_phone_no`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买人电话',
    `name`                varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
    `id_card`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
    `order_status`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单状态',
    `refund_time`         datetime NULL DEFAULT NULL COMMENT '订单退款时间',
    `refund_reason`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '退款原因',
    `refund_amount`       decimal(19, 4) NULL DEFAULT NULL COMMENT '退款金额',
    `agreement_merchant`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '履约服务商',
    `order_channel`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道来源',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for sys_kv
-- ----------------------------
CREATE TABLE `sys_kv`
(
    `id`           bigint UNSIGNED NOT NULL AUTO_INCREMENT,
    `gmt_created`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `k`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '键',
    `v`            text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE INDEX `uq_k`(`k`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '键值表' ROW_FORMAT = DYNAMIC;

SET
FOREIGN_KEY_CHECKS = 1;
