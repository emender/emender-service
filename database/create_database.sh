#!/bin/sh

DATABASE=emender-service.db

cat create_database.sql | sqlite3 ../${DATABASE}

