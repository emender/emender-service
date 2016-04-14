#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select * from job_operations order by id"

