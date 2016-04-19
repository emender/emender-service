#!/bin/env bash

cat results.html results.json > results.all

#curl -X POST -d "@results.all" -v "localhost:3000/api/v1/job-results-html/doc-Red_Hat_Developer_Toolset-1-Release_Notes-en-US+(test)"
curl -X POST --header "Content-Type: text/plain" --data-binary "@results.all" -v "localhost:3000/api/v1/job-results-html/doc-Red_Hat_Developer_Toolset-1-Release_Notes-en-US+(test)"

