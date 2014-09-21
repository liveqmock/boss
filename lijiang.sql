prompt Importing table t_systemprivilegeresource...
set feedback off
set define off
insert into t_systemprivilegeresource (ACTION, PRIVID, DT_CREATE, DT_LASTMOD)
values ('add_fapiao_serialno.do', 233, '14-7月 -08 11.38.13.993415 下午', '14-7月 -08 11.38.13.993415 下午');

prompt Done.
create table T_FAPIAOSERIALNO
(
  no VARCHAR2(50) not null
)
alter table t_accountitem add fapiaohaoma varchar2(50);