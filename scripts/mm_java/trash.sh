#!/bin/sh

dim=$1
nan_rate=$2
warmup_count=$3
exec_count=$4
seed=$5
canon=$6
outdir=$7

argstr="\"$dim $nan_rate $warmup_count $exec_count $seed $canon $outdir\""

#CMD_BASE="mvn exec:java -Dexec.mainClass=\"benchmark.MMBenchmark\"" 

mvn exec:java -Dexec.mainClass=benchmark.MMBenchmark -Dexec.args=$argstr -f ../../FPBenchmark/pom.xml
