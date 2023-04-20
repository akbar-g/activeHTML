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


