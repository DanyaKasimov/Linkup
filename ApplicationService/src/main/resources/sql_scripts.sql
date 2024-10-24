create table avatars (
    id uuid default gen_random_uuid() not null,
    url varchar(255)                  not null,
    user_id uuid                      not null ,
    primary key (id),
    foreign key (user_id) references users(id)
);

create table friends (
    id uuid default gen_random_uuid() not null,
    user_to_id uuid                   not null,
    user_from_id uuid                 not null,
    is_accepted boolean               not null,
    time timestamp                    not null,
    primary key (id),
    foreign key (user_to_id) references users(id),
    foreign key (user_from_id) references users(id)
);

