#!/bin/sh

PWD="`pwd`"
dim=$1
nan_rate=$2
warmup_count=$3
exec_count=$4
seed=$5
inline=$6

if [ "$inline" = "1" ]; then
        OUTDIR=${PWD}/out/wasi/inline
else
        OUTDIR=${PWD}/out/wasi/noinline
fi

# Run experiments

echo "wasi wasmer"
OUTFILE=${OUTDIR}/base_$dim\_$nan_rate.csv
wasmer ${PWD}/compiled/wasi/mm.wasm $dim $nan_rate $warmup_count $exec_count $seed > $OUTFILE
OUTFILE=${OUTDIR}/denan_$dim\_$nan_rate.csv
wasmer ${PWD}/compiled/wasi/denan.wasm $dim $nan_rate $warmup_count $exec_count $seed > $OUTFILE
OUTFILE=${OUTDIR}/canon_$dim\_$nan_rate.csv
wasmer ${PWD}/compiled/wasi/canon.wasm $dim $nan_rate $warmup_count $exec_count $seed > $OUTFILE

#echo "emcc wasmer"
#OUTFILE=${PWD}/out/emcc/base_$dim\_$nan_rate.csv
#wasmer ${PWD}/compiled/emcc/mm.wasm $dim $nan_rate $warmup_count $exec_count $seed > $OUTFILE
#OUTFILE=${PWD}/out/emcc/denan_$dim\_$nan_rate.csv
#wasmer ${PWD}/compiled/emcc/denan.wasm $dim $nan_rate $warmup_count $exec_count $seed > $OUTFILE
#OUTFILE=${PWD}/out/emcc/canon_$dim\_$nan_rate.csv
#wasmer ${PWD}/compiled/emcc/canon.wasm $dim $nan_rate $warmup_count $exec_count $seed > $OUTFILE
