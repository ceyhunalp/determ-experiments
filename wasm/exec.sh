#!/bin/sh

PWD="`pwd`"
DIM=$1
NAN_RATE=$2
WARMUP_COUNT=$3
EXEC_COUNT=$4
SEED=$5

# Run experiments

echo "wasi wasmer"
OUTFILE=${PWD}/out/wasi/base_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/wasi/mm.wasm $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED > $OUTFILE
OUTFILE=${PWD}/out/wasi/denan_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/wasi/denan.wasm $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED > $OUTFILE
OUTFILE=${PWD}/out/wasi/canon_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/wasi/canon.wasm $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED > $OUTFILE

echo "emcc wasmer"
OUTFILE=${PWD}/out/emcc/base_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/emcc/mm.wasm $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED > $OUTFILE
OUTFILE=${PWD}/out/emcc/denan_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/emcc/denan.wasm $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED > $OUTFILE
OUTFILE=${PWD}/out/emcc/canon_$DIM\_$NAN_RATE.csv
wasmer ${PWD}/compiled/emcc/canon.wasm $DIM $NAN_RATE $WARMUP_COUNT $EXEC_COUNT $SEED > $OUTFILE
