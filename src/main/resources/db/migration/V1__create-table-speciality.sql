create table specialty(
    id int generated by default as identity primary key,
    name varchar(25) not null unique
)