create table transaction_history (
	id                  uuid         not null default gen_random_uuid(),
	cc_num              varchar(60),
	merchant_name       varchar(60),
	date_time           varchar(60),
	category            varchar(60),
	amount              REAL,
	trans_num           varchar(60),
	unix_time           timestamptz,
	is_fraud            bool,
    migrated    bool
);




