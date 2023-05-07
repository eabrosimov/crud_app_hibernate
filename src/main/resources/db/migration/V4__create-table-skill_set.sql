create table skill_set(
    skill_id int references skill(id),
    developer_id int references developer(id) on delete cascade,
    primary key(skill_id, developer_id)
)