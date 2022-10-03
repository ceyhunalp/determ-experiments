#!/bin/sh

PWD="`pwd`"
SIZE=$1
CNT=1000

# Run experiments

# Run emcc-js
echo "node"
node ${PWD}/out/emcc/js/mm.js $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/js/mm_${SIZE}_${CNT}.csv
node ${PWD}/out/emcc/js/denan.js $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/js/denan_${SIZE}_${CNT}.csv
node ${PWD}/out/emcc/js/canon.js $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/js/canon_${SIZE}_${CNT}.csv

# Run standalone wasm (compiled with emcc) with wasmtime and wasmer
echo "emcc wasmtime"
wasmtime ${PWD}/out/emcc/stand/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wt_mm_${SIZE}_${CNT}.csv
wasmtime ${PWD}/out/emcc/stand/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wt_denan_${SIZE}_${CNT}.csv
wasmtime ${PWD}/out/emcc/stand/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wt_canon_${SIZE}_${CNT}.csv

echo "emcc wasmer"
wasmer ${PWD}/out/emcc/stand/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wasmer_mm_${SIZE}_${CNT}.csv
wasmer ${PWD}/out/emcc/stand/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wasmer_denan_${SIZE}_${CNT}.csv
wasmer ${PWD}/out/emcc/stand/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/emcc/stand/wasmer_canon_${SIZE}_${CNT}.csv

# Run standalone wasm (compiled with wasi-sdk) with wasmtime and wasmer
echo "wasi wasmtime"
wasmtime ${PWD}/out/wasi/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wt_mm_${SIZE}_${CNT}.csv
wasmtime ${PWD}/out/wasi/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wt_denan_${SIZE}_${CNT}.csv
wasmtime ${PWD}/out/wasi/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wt_canon_${SIZE}_${CNT}.csv

echo "wasi wasmer"
wasmer ${PWD}/out/wasi/mm.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wasmer_mm_${SIZE}_${CNT}.csv
wasmer ${PWD}/out/wasi/denan.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wasmer_denan_${SIZE}_${CNT}.csv
wasmer ${PWD}/out/wasi/canon.wasm $SIZE $SIZE $SIZE $SIZE y > ${PWD}/results/wasi/wasmer_canon_${SIZE}_${CNT}.csv
