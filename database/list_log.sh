#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select * from log order by id"

