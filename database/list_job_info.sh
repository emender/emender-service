#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select * from results where id = 1"

