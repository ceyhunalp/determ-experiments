#!/bin/sh

rm -v ./out/emcc/js/*.js ./out/emcc/stand/*.wasm ./out/wasi/*.wasm

emcc -O0 ./mm.c -o ./out/emcc/js/mm.js
emcc -O0 -D ./mm.c -o ./out/emcc/js/denan.js
emcc -O0 -C ./mm.c -o ./out/emcc/js/canon.js
#emcc -O0 ./mm.c -o ./out/emcc/js/mm.js -s ALLOW_MEMORY_GROWTH=1
#emcc -O0 -D ./mm.c -o ./out/emcc/js/denan.js -s ALLOW_MEMORY_GROWTH=1
#emcc -O0 -C ./mm.c -o ./out/emcc/js/canon.js -s ALLOW_MEMORY_GROWTH=1

emcc -O0 ./mm.c -o ./out/emcc/stand/mm.wasm
emcc -O0 -D ./mm.c -o ./out/emcc/stand/denan.wasm
emcc -O0 -C ./mm.c -o ./out/emcc/stand/canon.wasm

CC="${WASI_SDK_PATH}/bin/clang --sysroot=${WASI_SDK_PATH}/share/wasi-sysroot"
$CC ./mm.c -o ./out/wasi/mm.wasm
wasm-opt ./out/wasi/mm.wasm -o ./out/wasi/denan.wasm --denan
wasm-opt ./out/wasi/mm.wasm -o ./out/wasi/canon.wasm --canon
