#!/bin/sh

mvn clean
mvn package

./build.sh

PWD="`pwd`"
outdir=${PWD}/out/mm
mkdir -p ${outdir}

canon=$1

java -cp ./target:./jars/*: benchmark.MMBenchmark 100 0 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 100 1 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 100 10 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 100 100 2000 10 -1 $canon $outdir

java -cp ./target:./jars/*: benchmark.MMBenchmark 200 0 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 200 1 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 200 10 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 200 100 2000 10 -1 $canon $outdir

java -cp ./target:./jars/*: benchmark.MMBenchmark 500 0 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 500 1 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 500 10 2000 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 500 100 2000 10 -1 $canon $outdir

java -cp ./target:./jars/*: benchmark.MMBenchmark 1000 0 100 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 1000 1 100 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 1000 10 100 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 1000 100 100 10 -1 $canon $outdir

java -cp ./target:./jars/*: benchmark.MMBenchmark 2000 0 50 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 2000 1 50 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 2000 10 50 10 -1 $canon $outdir
java -cp ./target:./jars/*: benchmark.MMBenchmark 2000 100 50 10 -1 $canon $outdir
