#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select * from results order by id"

