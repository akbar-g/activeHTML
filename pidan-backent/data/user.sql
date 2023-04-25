-- auto-generated definition
create table user
(
    username     varchar(256)                       null comment '用户昵称',
    id           bigint auto_increment comment 'id'
        primary key,
    userAccount  varchar(256)                       null comment '账号',
    avatarUrl    varchar(1024)                      null comment '用户头像',
    gender       tinyint                            null comment '性别',
    userPassword varchar(512)                       not null comment '密码',
    phone        varchar(128)                       null comment '电话',
    email        varchar(512)                       null comment '邮箱',
    userStatus   int      default 0                 not null comment '状态 0 - 正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete     tinyint  default 0                 not null comment '是否删除',
    userRole     int      default 0                 not null comment '用户角色 0 - 普通用户 1 - 管理员',
    tags        varchar(1024)                       null comment '标签 ["Java","Python"]'
)
    comment '用户';

use myproject;
create table news
(
    id          bigint auto_increment comment '新闻id'
        primary key,
    herf        varchar(512)                       null comment '新闻链接',
    title       varchar(512)                       null comment '新闻标题',
    publishTime varchar(512)                       null comment '发布时间',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    comment '新闻表';

create index idx_title
    on news (title);


create table posts
(
    id          bigint auto_increment comment '帖子id'
        primary key,
    userId      bigint                             null comment '用户id',
    title       varchar(512)                       null comment '帖子标题',
    content     text                               null comment '帖子内容',
    publishTime datetime                           null comment '发布时间',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete    tinyint  default 0                 not null comment '是否删除',
    tags        varchar(1024)                      null comment '''标签列表（json 数组）'''
)
    comment '新闻表';



-- auto-generated definition
create table tag
(
    id         bigint auto_increment comment 'id'
        primary key,
    tagName    varchar(256)                       null comment '标签名称',
    userId     bigint                             null comment '用户ID',
    parentId   bigint                             null comment '父标签id',
    isParent   bigint                             null comment '0 - 不是 ，1 -父标签',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除',
    constraint unique_tagName
        unique (tagName)
)
    comment '标签表';

create index idx_userId
    on tag (userId);


-- auto-generated definition
create table team
(
    id          bigint auto_increment comment 'id'
        primary key,
    name        varchar(256)                       not null comment '队伍名称',
    description varchar(1024)                      null comment '描述',
    maxNum      int      default 1                 not null comment '最大人数',
    expireTime  datetime                           null comment '过期时间',
    userId      bigint                             null comment '用户id',
    status      int      default 0                 not null comment '0 - 公开，1 - 私有，2 - 加密',
    password    varchar(512)                       null comment '密码',
    createTime  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete    tinyint  default 0                 not null comment '是否删除'
)
    comment '队伍';


-- auto-generated definition
create table user_team
(
    id         bigint auto_increment comment 'id'
        primary key,
    userId     bigint                             null comment '用户id',
    teamId     bigint                             null comment '队伍id',
    joinTime   datetime                           null comment '加入时间',
    createTime datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    isDelete   tinyint  default 0                 not null comment '是否删除'
)
    comment '用户队伍关系';

-- auto-generated definition
create table user_message
(
    id         bigint auto_increment comment 'message_id'
        primary key,
    fromName   varchar(50)                        null comment '发送人',
    message    varchar(2048)                      null comment '消息',
    toName     varchar(50)                        null comment '接收人',
    updateTime datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    comment '聊天记录表';

create table friend
(
    id           bigint auto_increment comment 'id' primary key,
    fromUserId     bigint                     null comment '发送方用户Id',
    toUserId     bigint                     null comment '接收方用户Id',
    remark 				varchar(512)							null comment '备注信息',
    friendStatus   int      default 0                 not null comment '拉黑状态 0 - 正常, 1-拉黑',
    friendBlack   int      default 0                 not null comment '删除状态 0 - 正常, 1-删除',
    accepted     int      default 0                 not null comment '是否接受好友请求，0-未接受，1-已接受',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
)
    comment '好友关系表';