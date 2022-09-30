# determ-experiments

## WASM

#### Using the WASI-SDK

```
export WASI_VERSION=16
export WASI_VERSION_FULL=$(WASI_VERSION).0
export WASI_SDK_PATH={installation_path}/wasi-sdk-${WASI_VERSION_FULL}
CC="${WASI_SDK_PATH}/bin/clang --sysroot=${WASI_SDK_PATH}/share/wasi-sysroot"
$CC mm.c -o base.wasm
wasm-opt base.wasm -o denan.wasm --denan 
wasm-opt base.wasm -o canon.wasm --canon
```
