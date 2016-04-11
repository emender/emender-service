#!/bin/sh

DATABASE=emender_service.db

cat create_database.sql | sqlite3 ../${DATABASE}

