# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table channel_config (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  password                  varchar(255),
  server_id                 bigint,
  constraint pk_channel_config primary key (id))
;

create table chat_log (
  id                        bigint auto_increment not null,
  channel                   varchar(255),
  sender                    varchar(255),
  message                   varchar(255),
  timestamp                 datetime,
  message_type              integer,
  constraint pk_chat_log primary key (id))
;

create table fmbot_script (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  script                    longtext,
  enabled                   tinyint(1) default 0,
  last_updated              datetime,
  default_script            tinyint(1) default 0,
  constraint pk_fmbot_script primary key (id))
;

create table server_config (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  nick                      varchar(255),
  login                     varchar(255),
  nickserv_user             varchar(255),
  nickserv_password         varchar(255),
  server_ip                 varchar(255),
  server_port               integer,
  server_ssl                tinyint(1) default 0,
  auto_reconnect            tinyint(1) default 0,
  enabled                   tinyint(1) default 0,
  constraint pk_server_config primary key (id))
;

alter table channel_config add constraint fk_channel_config_server_1 foreign key (server_id) references server_config (id) on delete restrict on update restrict;
create index ix_channel_config_server_1 on channel_config (server_id);



# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table channel_config;

drop table chat_log;

drop table fmbot_script;

drop table server_config;

SET FOREIGN_KEY_CHECKS=1;

