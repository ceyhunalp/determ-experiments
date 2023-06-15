#!/bin/sh

#rm -v ./compiled/emcc/js/*.js ./compiled/emcc/stand/*.wasm ./compiled/wasi/*.wasm
#rm -rv ./compiled/
#mkdir -p compiled/emcc compiled/wasi

#emcc -O0 ./mm.c -o ./compiled/emcc/js/mm.js
#emcc -O0 -D ./mm.c -o ./compiled/emcc/js/denan.js
#emcc -O0 -C ./mm.c -o ./compiled/emcc/js/canon.js
#emcc -O0 ./mm.c -o ./compiled/emcc/js/mm.js -s ALLOW_MEMORY_GROWTH=1
#emcc -O0 -D ./mm.c -o ./compiled/emcc/js/denan.js -s ALLOW_MEMORY_GROWTH=1
#emcc -O0 -C ./mm.c -o ./compiled/emcc/js/canon.js -s ALLOW_MEMORY_GROWTH=1

emcc -O0 ./mm.c ./nan.c -o ./compiled/emcc/mm.wasm
emcc -O0 -D ./mm.c ./nan.c -o ./compiled/emcc/denan.wasm
emcc -O0 -C ./mm.c ./nan.c -o ./compiled/emcc/canon.wasm

CC="${WASI_SDK_PATH}/bin/clang --sysroot=${WASI_SDK_PATH}/share/wasi-sysroot"
$CC ./mm.c ./nan.c -o ./compiled/wasi/mm.wasm
wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/denan.wasm --denan
wasm-opt ./compiled/wasi/mm.wasm -o ./compiled/wasi/canon.wasm --canon
