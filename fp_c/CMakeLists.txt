cmake_minimum_required(VERSION 3.22)
project(fp_c C)

set(CMAKE_C_STANDARD 99)

include_directories("/usr/local/include")

add_executable(fp_test fp_test.c)

target_link_directories(fp_test PUBLIC "/usr/local/lib")
target_link_libraries(fp_test -lmpfr -lgmp)