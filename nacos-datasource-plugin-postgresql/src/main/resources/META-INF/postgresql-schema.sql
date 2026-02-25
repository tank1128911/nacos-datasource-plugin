-- ----------------------------
-- Table structure for config_info
-- ----------------------------
DROP TABLE IF EXISTS "config_info";
CREATE TABLE "config_info" (
  "id" BIGSERIAL,
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(255) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "md5" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "src_user" text COLLATE "pg_catalog"."default",
  "src_ip" varchar(50) COLLATE "pg_catalog"."default",
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "c_desc" varchar(256) COLLATE "pg_catalog"."default",
  "c_use" varchar(64) COLLATE "pg_catalog"."default",
  "effect" varchar(64) COLLATE "pg_catalog"."default",
  "type" varchar(64) COLLATE "pg_catalog"."default",
  "c_schema" text COLLATE "pg_catalog"."default",
  "encrypted_data_key" varchar(1024) COLLATE "pg_catalog"."default" NOT NULL DEFAULT ''::character varying
)
;

COMMENT ON COLUMN "config_info"."id" IS '唯一键';
COMMENT ON COLUMN "config_info"."data_id" IS 'data_id';
COMMENT ON COLUMN "config_info"."group_id" IS '分组';
COMMENT ON COLUMN "config_info"."content" IS '配置内容';
COMMENT ON COLUMN "config_info"."md5" IS 'md5';
COMMENT ON COLUMN "config_info"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "config_info"."gmt_modified" IS '修改时间';
COMMENT ON COLUMN "config_info"."src_user" IS 'source user';
COMMENT ON COLUMN "config_info"."src_ip" IS 'source ip';
COMMENT ON COLUMN "config_info"."app_name" IS 'app_name';
COMMENT ON COLUMN "config_info"."tenant_id" IS '命名空间（租户）';
COMMENT ON COLUMN "config_info"."c_desc" IS 'configuration description';
COMMENT ON COLUMN "config_info"."c_use" IS 'configuration usage';
COMMENT ON COLUMN "config_info"."effect" IS '配置生效的描述';
COMMENT ON COLUMN "config_info"."type" IS '配置的类型';
COMMENT ON COLUMN "config_info"."c_schema" IS '配置的模式';
COMMENT ON COLUMN "config_info"."encrypted_data_key" IS '密钥';
COMMENT ON TABLE "config_info" IS '配置信息';

-- ----------------------------
-- Table structure for config_info_gray
-- ----------------------------
DROP TABLE IF EXISTS "config_info_gray";
CREATE TABLE "config_info_gray" (
  "id" BIGSERIAL,
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "md5" varchar(32) COLLATE "pg_catalog"."default",
  "src_user" text COLLATE "pg_catalog"."default",
  "src_ip" varchar(100) COLLATE "pg_catalog"."default",
  "gmt_create" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "gray_name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "gray_rule" text COLLATE "pg_catalog"."default" NOT NULL,
  "encrypted_data_key" varchar(256) COLLATE "pg_catalog"."default" NOT NULL DEFAULT ''::character varying
)
;

COMMENT ON COLUMN "config_info_gray"."id" IS 'id';
COMMENT ON COLUMN "config_info_gray"."data_id" IS 'data_id';
COMMENT ON COLUMN "config_info_gray"."group_id" IS 'group_id';
COMMENT ON COLUMN "config_info_gray"."content" IS 'content';
COMMENT ON COLUMN "config_info_gray"."md5" IS 'md5';
COMMENT ON COLUMN "config_info_gray"."src_user" IS 'src_user';
COMMENT ON COLUMN "config_info_gray"."src_ip" IS 'src_ip';
COMMENT ON COLUMN "config_info_gray"."gmt_create" IS 'gmt_create';
COMMENT ON COLUMN "config_info_gray"."gmt_modified" IS 'gmt_modified';
COMMENT ON COLUMN "config_info_gray"."app_name" IS 'app_name';
COMMENT ON COLUMN "config_info_gray"."tenant_id" IS 'tenant_id';
COMMENT ON COLUMN "config_info_gray"."gray_name" IS 'gray_name';
COMMENT ON COLUMN "config_info_gray"."gray_rule" IS 'gray_rule';
COMMENT ON COLUMN "config_info_gray"."encrypted_data_key" IS 'encrypted_data_key';
COMMENT ON TABLE "config_info_gray" IS 'config_info_gray';

-- ----------------------------
-- Table structure for config_tags_relation
-- ----------------------------
DROP TABLE IF EXISTS "config_tags_relation";
CREATE TABLE "config_tags_relation" (
  "id" int8 NOT NULL,
  "tag_name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "tag_type" varchar(64) COLLATE "pg_catalog"."default",
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "nid" BIGSERIAL
)
;

COMMENT ON COLUMN "config_tags_relation"."id" IS 'id';
COMMENT ON COLUMN "config_tags_relation"."tag_name" IS 'tag_name';
COMMENT ON COLUMN "config_tags_relation"."tag_type" IS 'tag_type';
COMMENT ON COLUMN "config_tags_relation"."data_id" IS 'data_id';
COMMENT ON COLUMN "config_tags_relation"."group_id" IS 'group_id';
COMMENT ON COLUMN "config_tags_relation"."tenant_id" IS 'tenant_id';
COMMENT ON COLUMN "config_tags_relation"."nid" IS '自增长标识，唯一键';
COMMENT ON TABLE "config_tags_relation" IS 'config_tag_relation';

-- ----------------------------
-- Table structure for group_capacity
-- ----------------------------
DROP TABLE IF EXISTS "group_capacity";
CREATE TABLE "group_capacity" (
  "id" BIGSERIAL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL DEFAULT ''::character varying,
  "quota" int4 NOT NULL DEFAULT 0,
  "usage" int4 NOT NULL DEFAULT 0,
  "max_size" int4 NOT NULL DEFAULT 0,
  "max_aggr_count" int4 NOT NULL DEFAULT 0,
  "max_aggr_size" int4 NOT NULL DEFAULT 0,
  "max_history_count" int4 NOT NULL DEFAULT 0,
  "gmt_create" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

COMMENT ON COLUMN "group_capacity"."id" IS 'id';
COMMENT ON COLUMN "group_capacity"."group_id" IS 'Group ID，空字符表示整个集群';
COMMENT ON COLUMN "group_capacity"."quota" IS '配额，0表示使用默认值';
COMMENT ON COLUMN "group_capacity"."usage" IS '使用量';
COMMENT ON COLUMN "group_capacity"."max_size" IS '单个配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "group_capacity"."max_aggr_count" IS '聚合子配置最大个数，，0表示使用默认值';
COMMENT ON COLUMN "group_capacity"."max_aggr_size" IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "group_capacity"."max_history_count" IS '最大变更历史数量';
COMMENT ON COLUMN "group_capacity"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "group_capacity"."gmt_modified" IS '修改时间';
COMMENT ON TABLE "group_capacity" IS '集群、各Group容量信息表';

-- ----------------------------
-- Table structure for his_config_info
-- ----------------------------
DROP TABLE IF EXISTS "his_config_info";
CREATE TABLE "his_config_info" (
  "id" int8 NOT NULL,
  "nid" BIGSERIAL,
  "data_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "group_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "app_name" varchar(128) COLLATE "pg_catalog"."default",
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "md5" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "src_user" text COLLATE "pg_catalog"."default",
  "src_ip" varchar(50) COLLATE "pg_catalog"."default",
  "op_type" char(10) COLLATE "pg_catalog"."default",
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "encrypted_data_key" varchar(1024) COLLATE "pg_catalog"."default" NOT NULL DEFAULT ''::character varying,
  "publish_type" varchar(50) COLLATE "pg_catalog"."default" DEFAULT 'formal'::character varying,
  "gray_name" varchar(128) COLLATE "pg_catalog"."default",
  "ext_info" text COLLATE "pg_catalog"."default"
)
;


-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS "permissions";
CREATE TABLE "permissions" (
  "role" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "resource" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "action" varchar(8) COLLATE "pg_catalog"."default" NOT NULL
)
;

COMMENT ON COLUMN "permissions"."role" IS '角色';
COMMENT ON COLUMN "permissions"."resource" IS 'resource';
COMMENT ON COLUMN "permissions"."action" IS 'action';

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS "roles";
CREATE TABLE "roles" (
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "role" varchar(50) COLLATE "pg_catalog"."default" NOT NULL
)
;

COMMENT ON COLUMN "roles"."username" IS '登录名';
COMMENT ON COLUMN "roles"."role" IS '角色';

-- ----------------------------
-- Table structure for tenant_capacity
-- ----------------------------
DROP TABLE IF EXISTS "tenant_capacity";
CREATE TABLE "tenant_capacity" (
  "id" BIGSERIAL,
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL DEFAULT ''::character varying,
  "quota" int4 NOT NULL DEFAULT 0,
  "usage" int4 NOT NULL DEFAULT 0,
  "max_size" int4 NOT NULL DEFAULT 0,
  "max_aggr_count" int4 NOT NULL DEFAULT 0,
  "max_aggr_size" int4 NOT NULL DEFAULT 0,
  "max_history_count" int4 NOT NULL DEFAULT 0,
  "gmt_create" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  "gmt_modified" timestamptz(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
)
;

COMMENT ON COLUMN "tenant_capacity"."id" IS '主键ID';
COMMENT ON COLUMN "tenant_capacity"."tenant_id" IS 'Tenant ID';
COMMENT ON COLUMN "tenant_capacity"."quota" IS '配额，0表示使用默认值';
COMMENT ON COLUMN "tenant_capacity"."usage" IS '使用量';
COMMENT ON COLUMN "tenant_capacity"."max_size" IS '单个配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "tenant_capacity"."max_aggr_count" IS '聚合子配置最大个数';
COMMENT ON COLUMN "tenant_capacity"."max_aggr_size" IS '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';
COMMENT ON COLUMN "tenant_capacity"."max_history_count" IS '最大变更历史数量';
COMMENT ON COLUMN "tenant_capacity"."gmt_create" IS '创建时间';
COMMENT ON COLUMN "tenant_capacity"."gmt_modified" IS '修改时间';
COMMENT ON TABLE "tenant_capacity" IS '租户容量信息表';

-- ----------------------------
-- Table structure for tenant_info
-- ----------------------------
DROP TABLE IF EXISTS "tenant_info";
CREATE TABLE "tenant_info" (
  "id" BIGSERIAL,
  "kp" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_id" varchar(128) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "tenant_name" varchar(128) COLLATE "pg_catalog"."default" DEFAULT ''::character varying,
  "tenant_desc" varchar(256) COLLATE "pg_catalog"."default",
  "create_source" varchar(32) COLLATE "pg_catalog"."default",
  "gmt_create" int8,
  "gmt_modified" int8
)
;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS "users";
CREATE TABLE "users" (
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "enabled" bool NOT NULL
)
;

COMMENT ON COLUMN "users"."username" IS '登录名';
COMMENT ON COLUMN "users"."password" IS '密码';
COMMENT ON COLUMN "users"."enabled" IS 'enabled';

-- ----------------------------
-- Indexes structure for table config_info
-- ----------------------------
CREATE UNIQUE INDEX "uk_configinfo_datagrouptenant" ON "config_info" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_info
-- ----------------------------
ALTER TABLE "config_info" ADD CONSTRAINT "config_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_info_gray
-- ----------------------------
CREATE INDEX "idx_dataid_gmt_modified" ON "config_info_gray" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "gmt_create" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);
CREATE INDEX "idx_gmt_modified" ON "config_info_gray" USING btree (
  "gmt_modified" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_configinfogray_datagrouptenantgray" ON "config_info_gray" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "gray_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_info_gray
-- ----------------------------
ALTER TABLE "config_info_gray" ADD CONSTRAINT "config_info_gray_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table config_tags_relation
-- ----------------------------
CREATE INDEX "idx_tenant_id" ON "config_tags_relation" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_configtagrelation_configidtag" ON "config_tags_relation" USING btree (
  "id" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "tag_name" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tag_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table config_tags_relation
-- ----------------------------
ALTER TABLE "config_tags_relation" ADD CONSTRAINT "config_tag_relation_pkey" PRIMARY KEY ("nid");

-- ----------------------------
-- Indexes structure for table group_capacity
-- ----------------------------
CREATE UNIQUE INDEX "uk_group_id" ON "group_capacity" USING btree (
  "group_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table group_capacity
-- ----------------------------
ALTER TABLE "group_capacity" ADD CONSTRAINT "group_capacity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table his_config_info
-- ----------------------------
CREATE INDEX "idx_did" ON "his_config_info" USING btree (
  "data_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_gmt_create" ON "his_config_info" USING btree (
  "gmt_create" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);
CREATE INDEX "idx_gmt_modify" ON "his_config_info" USING btree (
  "gmt_modified" "pg_catalog"."timestamptz_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table his_config_info
-- ----------------------------
ALTER TABLE "his_config_info" ADD CONSTRAINT "his_config_info_pkey" PRIMARY KEY ("nid");

-- ----------------------------
-- Indexes structure for table permissions
-- ----------------------------
CREATE UNIQUE INDEX "uk_role_permission" ON "permissions" USING btree (
  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "resource" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "action" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table roles
-- ----------------------------
CREATE INDEX "idx_user_role" ON "roles" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "role" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table tenant_capacity
-- ----------------------------
CREATE UNIQUE INDEX "uk_tenant_id" ON "tenant_capacity" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table tenant_capacity
-- ----------------------------
ALTER TABLE "tenant_capacity" ADD CONSTRAINT "tenant_capacity_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table tenant_info
-- ----------------------------
CREATE INDEX "idx_tenant" ON "tenant_info" USING btree (
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE UNIQUE INDEX "uk_tenant_info_kp_tenant" ON "tenant_info" USING btree (
  "kp" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "tenant_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table tenant_info
-- ----------------------------
ALTER TABLE "tenant_info" ADD CONSTRAINT "tenant_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table users
-- ----------------------------
ALTER TABLE "users" ADD CONSTRAINT "users_pkey" PRIMARY KEY ("username");
