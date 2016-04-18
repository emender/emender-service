#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d "@testinfo-ok.json" -v "localhost:3000/api/v1/run-test/doc-Test-Book1+(test)"

