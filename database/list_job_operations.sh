#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select * from jobs order by id"

