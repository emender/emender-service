#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d "@testinfo-bad-repo.json" -v "localhost:3000/api/v1/run-test/doc-Broken-Book+(test)"

