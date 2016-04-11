#!/bin/sh

pushd ..
lein run -- --server --port=8888
popd

