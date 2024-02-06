#!/bin/bash

mvn clean

./build.sh

PWD="`pwd`"
#mldir=${PWD}/../movielens/ml-100k
mldir=${PWD}/../movielens/ml-1m
outdir=${PWD}/out
mkdir -p ${outdir}

java -cp ./target:./jars/*: benchmark.CFBenchmark $mldir 1 0 1 math $outdir
