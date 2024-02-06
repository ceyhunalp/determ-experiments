#!/bin/sh

mvn clean

./build.sh

PWD="`pwd`"
dsdir=${PWD}/../movielens/ml-100k
outdir=${PWD}/out
mkdir -p ${outdir}

## params: dsdir foldnum wc ec libname outdir

## EXECUTE WIHTOUT INSTRUMENTATION

for i in {1..1}
do
        #java -XX:+UnlockDiagnosticVMOptions -XX:+PrintIntrinsics -cp ./target:./jars/*: benchmark.CFBenchmark $dsdir $i 1 1 math $outdir
        java -Xbatch -XX:+PrintCompilation -cp ./target:./jars/*: benchmark.CFBenchmark $dsdir $i 3 1 smath $outdir
done

## EXECUTE WITH INSTRUMENTATION

#mvn clean
#./build.sh

#rwdir="/Users/alp/workspace/dedis/determ-sandbox/jvm_sandbox"
#target=$rwdir/target/
#jars=$rwdir/jars/*

#java -cp $target:$jars: launcher.Launcher -m rewriter -l mpfr -p target/cf/CF.class

#for i in {1..1}
#do
        #java -cp ./target:./jars/*:$target: benchmark.CFBenchmark $dsdir $i 0 5 mpfr $outdir
#done
