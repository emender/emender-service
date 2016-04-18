#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d "@results.json" -v "localhost:3000/api/v1/job-results/doc-Red_Hat_Developer_Toolset-1-Release_Notes-en-US+(test)"

