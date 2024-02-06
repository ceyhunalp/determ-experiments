#!/bin/sh

inline=$1

mkdir -p out/emcc out/wasi/noinline out/wasi/inline
mkdir -p out/emcc out/wasi
mkdir -p compiled/wasi compiled/emcc

./compile.sh $inline

# FORMAT: ./exec.sh $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED $inline

#./exec.sh 100 0 0 20 61 $inline
#./exec.sh 100 1 0 20 61 $inline
#./exec.sh 100 10 0 20 61 $inline
#./exec.sh 100 100 0 20 61 $inline

#./exec.sh 200 0 0 20 61 $inline
#./exec.sh 200 1 0 20 61 $inline
#./exec.sh 200 10 0 20 61 $inline
#./exec.sh 200 100 0 20 61 $inline

#./exec.sh 500 0 0 20 61 $inline
#./exec.sh 500 1 0 20 61 $inline
#./exec.sh 500 10 0 20 61 $inline
#./exec.sh 500 100 0 20 61 $inline

./exec.sh 1000 0 0 20 61 $inline
./exec.sh 1000 1 0 20 61 $inline
./exec.sh 1000 10 0 20 61 $inline
./exec.sh 1000 100 0 20 61 $inline

#./exec.sh 2000 0 0 20 61 $inline
#./exec.sh 2000 1 0 20 61 $inline
#./exec.sh 2000 10 0 20 61 $inline
#./exec.sh 2000 100 0 20 61 $inline
