#!/bin/sh

#emcc -O0 ./mm.c ./nan.c -o ./compiled/emcc/mm.wasm -s TOTAL_MEMORY=2048MB
#emcc -O0 -D ./mm.c ./nan.c -o ./compiled/emcc/denan.wasm -s TOTAL_MEMORY=2048MB
#emcc -O0 -C ./mm.c ./nan.c -o ./compiled/emcc/canon.wasm -s TOTAL_MEMORY=2048MB

CC="${WASI_SDK_PATH}/bin/clang --sysroot=${WASI_SDK_PATH}/share/wasi-sysroot"
$CC -O0 ./mm.c ./nan.c -o ./compiled/wasi/mm.wasm
if [ "$1" = "1" ]; then
        echo "--inlining-optimizing"
        wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/denan.wasm --denan --inlining-optimizing
        wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/canon.wasm --canon --inlining-optimizing
else
        wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/denan.wasm --denan
        wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/canon.wasm --canon
fi
