create table users (
    id UUID default gen_random_uuid() not null,
    username VARCHAR(255) not null ,
    name VARCHAR(255) not null ,
    second_name VARCHAR(255) not null ,
    password VARCHAR(255) not null ,
    user_role VARCHAR(50) not null ,
    email_id uuid not null ,
    is_deleted boolean not null ,
    primary key (id),
    foreign key (email_id) references email(id)
);

create table email (
    id UUID default gen_random_uuid() not null,
    email varchar(255) not null ,
    is_accepted boolean not null ,
    primary key (id)
);

create table users_info (
    id UUID primary key,
    city varchar(255),
    birthday varchar(255),
    status varchar(255),
    about varchar(500),
    education varchar(255),
    gender varchar(20),
    work varchar(255),
    is_close boolean,
    foreign key (id) references users(id)
)