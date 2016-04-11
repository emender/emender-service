#!/bin/sh

pushd ..
screen -S clojure bash -c 'lein repl'
popd

