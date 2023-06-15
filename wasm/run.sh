#!/bin/sh

mkdir -p out/emcc out/wasi
rm -rv ./compiled/
mkdir -p compiled/emcc compiled/wasi

./compile.sh

./exec.sh 100 0 5 5
./exec.sh 100 1 5 5
./exec.sh 100 10 5 5
./exec.sh 100 100 5 5

#./exec.sh 500 0 5 5
#./exec.sh 500 1 5 5
#./exec.sh 500 10 5 5
#./exec.sh 500 100 5 5

#./exec.sh 1000 0 5 5
#./exec.sh 1000 1 5 5
#./exec.sh 1000 10 5 5
#./exec.sh 1000 100 5 5

#./exec.sh 5000 0 5 5
#./exec.sh 5000 1 5 5
#./exec.sh 5000 10 5 5
#./exec.sh 5000 100 5 5

#./exec.sh 10000 0 5 5
#./exec.sh 10000 1 5 5
#./exec.sh 10000 10 5 5
#./exec.sh 10000 100 5 5
