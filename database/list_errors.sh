#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select * from errors order by id"

