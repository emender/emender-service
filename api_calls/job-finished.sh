#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d '{"name":"doc-Red_Hat_Developer_Toolset-1-Release_Notes-en-US (test)"}' -v localhost:3000/v1/job-finished

