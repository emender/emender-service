#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d "@results.json" -v "localhost:3000/v1/job-results/doc-Other-Book+(test)"

