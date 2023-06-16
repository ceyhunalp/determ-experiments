#!/bin/sh

emcc -O0 ./mm.c ./nan.c -o ./compiled/emcc/mm.wasm -s TOTAL_MEMORY=1024MB
emcc -O0 -D ./mm.c ./nan.c -o ./compiled/emcc/denan.wasm -s TOTAL_MEMORY=1024MB
emcc -O0 -C ./mm.c ./nan.c -o ./compiled/emcc/canon.wasm -s TOTAL_MEMORY=1024MB

CC="${WASI_SDK_PATH}/bin/clang --sysroot=${WASI_SDK_PATH}/share/wasi-sysroot"
$CC ./mm.c ./nan.c -o ./compiled/wasi/mm.wasm
wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/denan.wasm --denan
wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/canon.wasm --canon
