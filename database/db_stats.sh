#!/bin/sh

DATABASE=emender-service.db

echo "Log"
sqlite3 ../${DATABASE} "select count(*) from log"
echo ""

echo "Errors"
sqlite3 ../${DATABASE} "select count(*) from errors"
echo ""

echo "Requests"
sqlite3 ../${DATABASE} "select count(*) from requests"
echo ""

echo "Job operations"
sqlite3 ../${DATABASE} "select count(*) from job_operations"
echo ""

echo "Results"
sqlite3 ../${DATABASE} "select count(*) from results"
echo ""

