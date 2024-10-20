drop user userdata;
drop database equiposdata;

create user userdata with password  '123456';
create database equiposdata owner userdata;