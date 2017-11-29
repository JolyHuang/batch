CREATE TABLE BATCH_JOB (
   ID                       varchar(40) NOT NULL UNIQUE comment '主键id',
   PARENT_JOB_ID            varchar(40) comment '父级job id',
   LOOKUP_PATH              varchar(200) NOT NULL comment '交易名',
   PLAN_EXECUTE_TIME      	TIMESTAMP NOT NULL comment '计划执行时间',
   ACTUAL_EXECUTE_TIME      TIMESTAMP comment '实际执行时间',
   EXECUTE_COUNT            int NOT NULL comment '执行次数',
   CREATE_TIME				      TIMESTAMP NOT NULL comment '创建时间',
   STATUS      			        char(1) NOT NULL comment '失败次数',
   ERROR_MESSAGE_CODE       varchar(200) comment '错误码',
   ERROR_LOCALIZED_MESSAGE  varchar(500) comment '本地错误消息',
   ERROR_CAUSE              varchar(6000) comment '错误原因',
   PRIMARY KEY (ID)
);