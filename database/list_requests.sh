#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select * from requests order by id"

