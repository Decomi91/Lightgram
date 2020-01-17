drop table All_users;

create table All_users(
	id			varchar2(20) primary key,
	password	varchar2(20) not null,
	name		varchar2(40) default '',
	email		varchar2(40) unique not null,
	profileImg	varchar2(50)
);

create table hashTags(
	hashTag		varchar2(80) not null,
	usedWhere	varchar2(20)
)

create table locations(
	location		varchar2(80) not null,
	usedWhere		varchar2(20)
)

create table banned(
	id		varchar2(20),
	email	varchar2(40)
)

insert into All_users values('admin', '1234', 'admin', 'admin@admin.com', '');
select * from tab; 
select * from hashTags;
select * from all_users;