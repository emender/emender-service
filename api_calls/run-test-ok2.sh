#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d "@testinfo-ok2.json" -v "localhost:3000/v1/run-test/doc-Test-Book2+(test)"

