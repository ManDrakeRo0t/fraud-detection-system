create table customer (
	id          uuid         not null default gen_random_uuid(),
	cc_num      varchar(60),
	first_name  varchar(40),
	last_name   varchar(40),
	gender      bool,
	birthday    date,
	street      varchar(60),
	city        varchar(60),
	state       varchar(60),
	zip         integer,
	job         varchar(60),
	city_pop    integer,
	lat         REAL ,
	long        REAL,
	migrated    bool
);


create table merchant (
	id          uuid         not null default gen_random_uuid(),
	name        varchar(60),
	lat         REAL ,
	long        REAL,
    migrated    bool
);


