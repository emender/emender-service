create table job_operations (
    id          integer primary key asc,
    datetime    text,
    job         text,
    url         text,
    branch      text,
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
    url         text,
    branch      text,
    results     text
);

create table errors (
    id          integer primary key asc,
    datetime    text,
    job         text,
    url         text,
    branch      text,
    message     text,
    stacktrace  text
);

create table log (
    id          integer primary key asc,
    datetime    text,
    job         text,
    url         text,
    branch      text,
    operation   text,
    message     text
);

