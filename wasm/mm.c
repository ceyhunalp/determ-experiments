//
// Created by Enis Ceyhun Alp on 28.09.22.
//

//#define _POSIX_C_SOURCE 199309L
#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <stdbool.h>
#include <time.h>
#include <assert.h>

#define WARMUP_COUNT 1
#define EXEC_COUNT 2

void printMatrix(double* m, int r, int c) {
    for (int i = 0; i < r; i++) {
        for (int j = 0; j < c; j++) {
            printf("%.5f ", m[i*c + j]);
        }
        printf("\n");
    }
    printf("-----\n");
}

void matrixMultiply(int r1, int c1, int r2, int c2, bool warmup) {
    double tmp;
    double* m1 = (double*) malloc(r1*c1*sizeof(double));
    double* m2 = (double*) malloc(r2*c2*sizeof(double));
    double* result = (double*) malloc(r1*c2*sizeof(double));

    unsigned long exec_time;
    struct timespec start, end;

    srand(time(NULL));
    for (int i = 0; i < r1 * c1; i++) {
        m1[i] = (double)rand() / RAND_MAX;
    }
    for (int i = 0; i < r2 * c2; i++) {
        m2[i] = (double)rand() / RAND_MAX;
    }

//    printMatrix(m1, r1, c1);
//    printMatrix(m2, r2, c2);

//    char data[sizeof(double)];
//    for (int i = 0; i < r1 * c1; i++) {
//        double tmp;
//        memcpy(data, &m1[i], sizeof(double));
//        memcpy(&tmp, data, sizeof(double));
//    }

    if (warmup) {
        for (int cnt = 0; cnt < WARMUP_COUNT; cnt++) {
            for (int i = 0; i < r1; i++) {
                for (int j = 0; j < c2; j++) {
                    tmp = 0;
                    for (int k = 0; k < c1; k++) {
                        tmp += m1[i*c1 + k] * m2[j + k*c2];
                    }
                    result[i*c2 + j] = tmp;
                }
            }
        }
    }

    for (int cnt = 0; cnt < EXEC_COUNT; cnt++) {
        clock_gettime(CLOCK_MONOTONIC_RAW, &start);
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                tmp = 0;
                for (int k = 0; k < c1; k++) {
                    tmp += m1[i*c1 + k] * m2[j + k*c2];
                }
                result[i*c2 + j] = tmp;
            }
        }
        clock_gettime(CLOCK_MONOTONIC_RAW, &end);
        exec_time = (end.tv_sec - start.tv_sec) * 1e9;
        exec_time += (end.tv_nsec - start.tv_nsec);
        printf("%lu\n", exec_time);
    }
    free(m1);
    free(m2);
    free(result);
}

int main(int argc, char* argv[]) {
    int r1 = 4;
    int c1 = 5;
    int r2 = 5;
    int c2 = 7;
    matrixMultiply(r1, c1, r2, c2, false);

//    if (argc != 6) {
//        fprintf(stderr, "Missing argument\n");
//        exit(EXIT_FAILURE);
//    }
//
//    bool warmup = true;
//    if (strcmp(argv[5], "-w") != 0) {
//        warmup = false;
//    }
//    int r1 = atoi(argv[1]);
//    int c1 = atoi(argv[2]);
//    int r2 = atoi(argv[3]);
//    int c2 = atoi(argv[4]);
//    assert(c1 == r2);
//    matrixMultiply(r1, c1, r2, c2, warmup);
}
