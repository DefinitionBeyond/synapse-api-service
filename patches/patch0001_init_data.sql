CREATE TABLE "login_tokens" (
    "user_id" TEXT NOT NULL,
    "device_id" TEXT NOT NULL,
    "login_token" TEXT NOT NULL,
    "create_time" TIME NOT NULL
)
;
CREATE INDEX "INDEX_login_token" ON login_tokens ("login_token");
CREATE UNIQUE INDEX "UNIQUE_INDEX" ON login_tokens ("user_id", "device_id");
COMMENT ON COLUMN "login_tokens"."user_id" IS 'Matrix用户ID';
COMMENT ON COLUMN "login_tokens"."device_id" IS '用户设备ID';
COMMENT ON COLUMN "login_tokens"."login_token" IS '用户可登录的token';
COMMENT ON COLUMN "login_tokens"."create_time" IS '记录创建时间';