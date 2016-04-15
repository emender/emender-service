#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "select id, datetime, job, url, branch from results order by id"

