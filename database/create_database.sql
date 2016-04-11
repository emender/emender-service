create table jobs (
    id          integer primary key asc,
    datetime    text,
    job         text,
    operation   text
);

create table requests (
    id          integer primary key asc,
    uri         text,
    ipaddress   text,
    datetime    text,
    params      text,
    useragent   text
);

create table results (
    id          integer primary key asc,
    datetime    text,
    job         text,
    results     text
);

create table errors (
    id          integer primary key asc,
    datetime    text,
    message     text,
    stacktrace  text
);

