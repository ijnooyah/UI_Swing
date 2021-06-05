create user exam01 identified by 1234;
alter session set "_ORACLE_SCRIPT"=true;
grant resource, connect to exam01;