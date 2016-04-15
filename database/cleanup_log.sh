#!/bin/sh

DATABASE=emender-service.db

sqlite3 ../${DATABASE} "delete from log"

