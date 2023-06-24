#!/bin/sh

#mvn exec:java -f ../../FPBenchmark/pom.xml
#mvn exec:java -Dexec.args="100 0 1000 10 9 y $outdir" -f ../../FPBenchmark/pom.xml

mvn clean -f ../../FPBenchmark/pom.xml
mvn package -f ../../FPBenchmark/pom.xml

PWD="`pwd`"
outdir=${PWD}/../../FPBenchmark/out/mm
mkdir -p ${outdir}

canon=$1

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 0 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 1 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 10 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 100 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 0 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 1 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 10 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 100 2000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 0 1000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 1 1000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 10 1000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 100 1000 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 0 100 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 1 100 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 10 100 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 100 100 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 0 50 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 1 50 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 10 50 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 100 50 10 -1 $canon $outdir" -f ../../FPBenchmark/pom.xml
