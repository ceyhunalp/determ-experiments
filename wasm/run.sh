#!/bin/sh

mkdir -p out/emcc out/wasi
rm -rv ./compiled/
mkdir -p compiled/emcc compiled/wasi

./compile.sh

# FORMAT: ./exec.sh $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED

./exec.sh 100 0 0 10 61
./exec.sh 100 1 0 10 61
./exec.sh 100 10 0 10 61
./exec.sh 100 100 0 10 61

./exec.sh 200 0 0 10 61
./exec.sh 200 1 0 10 61
./exec.sh 200 10 0 10 61
./exec.sh 200 100 0 10 61

./exec.sh 500 0 0 10 61
./exec.sh 500 1 0 10 61
./exec.sh 500 10 0 10 61
./exec.sh 500 100 0 10 61

./exec.sh 1000 0 0 10 61
./exec.sh 1000 1 0 10 61
./exec.sh 1000 10 0 10 61
./exec.sh 1000 100 0 10 61

./exec.sh 2000 0 0 10 61
./exec.sh 2000 1 0 10 61
./exec.sh 2000 10 0 10 61
./exec.sh 2000 100 0 10 61
