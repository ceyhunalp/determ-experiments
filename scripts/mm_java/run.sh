#!/bin/sh

#mvn exec:java -f ../../FPBenchmark/pom.xml
#mvn exec:java -Dexec.args="100 0 1000 10 9 y $outdir" -f ../../FPBenchmark/pom.xml

PWD="`pwd`"
outdir=${PWD}/../../FPBenchmark/out/mm
mkdir -p ${outdir}

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 0 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 1 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 10 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="100 100 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 0 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 1 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 10 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="200 100 2000 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 0 500 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 1 500 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 10 500 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="500 100 500 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 0 15 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 1 15 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 10 15 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="1000 100 15 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml

mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 0 10 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 1 10 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 10 10 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
mvn exec:java -Dexec.mainClass="benchmark.MMBenchmark" -Dexec.args="2000 100 10 10 -1 -y $outdir" -f ../../FPBenchmark/pom.xml
