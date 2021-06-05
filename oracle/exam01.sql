create table tbl_student (
    sno varchar2(8) primary key,
    sname varchar2(10) not null,
    syear number(1) not null,
    gender varchar2(3) not null check(gender in ('³²', '¿©')),
    major varchar2(10) not null,
    score number(3) default 0 not null check(score between 0 and 100)
);