#!/bin/sh

DATABASE=emender_service.db

cat drop_database.sql | sqlite3 ../${DATABASE}

