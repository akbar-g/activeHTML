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