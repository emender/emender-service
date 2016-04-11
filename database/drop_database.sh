#!/bin/sh

DATABASE=emender-service.db

cat drop_database.sql | sqlite3 ../${DATABASE}

