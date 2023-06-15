#!/bin/sh

PWD="`pwd`"
#SIZE=$1
#CNT=1000

DIM=$1
NAN_RATE=$2
EXEC_COUNT=$3
WARMUP_COUNT=$4

# Run experiments

echo "wasi wasmer"
OUTFILE=${PWD}/out/wasi/base_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/wasi/mm.wasm $DIM $NAN_RATE $EXEC_COUNT $WARMUP_COUNT $OUTFILE
OUTFILE=${PWD}/out/wasi/denan_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/wasi/denan.wasm $DIM $NAN_RATE $EXEC_COUNT $WARMUP_COUNT $OUTFILE
OUTFILE=${PWD}/out/wasi/canon_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/wasi/canon.wasm $DIM $NAN_RATE $EXEC_COUNT $WARMUP_COUNT $OUTFILE

echo "emcc wasmer"
OUTFILE=${PWD}/out/emcc/base_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/emcc/mm.wasm $DIM $NAN_RATE $EXEC_COUNT $WARMUP_COUNT $OUTFILE
OUTFILE=${PWD}/out/emcc/denan_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/emcc/denan.wasm $DIM $NAN_RATE $EXEC_COUNT $WARMUP_COUNT $OUTFILE
OUTFILE=${PWD}/out/emcc/canon_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/emcc/canon.wasm $DIM $NAN_RATE $EXEC_COUNT $WARMUP_COUNT $OUTFILE

# Run emcc-js
#echo "node"
#node ${PWD}/compiled/emcc/js/mm.js $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/js/mm_${SIZE}_${CNT}.csv
#node ${PWD}/compiled/emcc/js/denan.js $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/js/denan_${SIZE}_${CNT}.csv
#node ${PWD}/compiled/emcc/js/canon.js $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/js/canon_${SIZE}_${CNT}.csv

# Run standalone wasm (compiled with emcc) with wasmtime and wasmer
#echo "emcc wasmtime"
#wasmtime ${PWD}/compiled/emcc/stand/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wt_mm_${SIZE}_${CNT}.csv
#wasmtime ${PWD}/compiled/emcc/stand/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wt_denan_${SIZE}_${CNT}.csv
#wasmtime ${PWD}/compiled/emcc/stand/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wt_canon_${SIZE}_${CNT}.csv

#echo "emcc wasmer"
#wasmer ${PWD}/compiled/emcc/stand/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wasmer_mm_${SIZE}_${CNT}.csv
#wasmer ${PWD}/compiled/emcc/stand/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wasmer_denan_${SIZE}_${CNT}.csv
#wasmer ${PWD}/compiled/emcc/stand/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wasmer_canon_${SIZE}_${CNT}.csv

# Run standalone wasm (compiled with wasi-sdk) with wasmtime and wasmer
#echo "wasi wasmtime"
#wasmtime ${PWD}/compiled/wasi/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wt_mm_${SIZE}_${CNT}.csv
#wasmtime ${PWD}/compiled/wasi/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wt_denan_${SIZE}_${CNT}.csv
#wasmtime ${PWD}/compiled/wasi/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wt_canon_${SIZE}_${CNT}.csv

#echo "wasi wasmer"
#wasmer ${PWD}/compiled/wasi/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wasmer_mm_${SIZE}_${CNT}.csv
#wasmer ${PWD}/compiled/wasi/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wasmer_denan_${SIZE}_${CNT}.csv
#wasmer ${PWD}/compiled/wasi/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wasmer_canon_${SIZE}_${CNT}.csv
