#!/bin/env bash
curl -X POST --header "Content-Type: application/json" -d "@testinfo1.json" -v "localhost:3000/v1/run-test/doc-Other-Book+(test)"

