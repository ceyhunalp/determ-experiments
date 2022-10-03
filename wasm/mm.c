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

#define WARMUP_COUNT 10000
#define EXEC_COUNT 1000

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
    double* m1 = (double*) malloc(r1*c1*sizeof(double));
    double* m2 = (double*) malloc(r2*c2*sizeof(double));
    double* result = (double*) malloc(r1*c2*sizeof(double));
    unsigned char* buf_result = (unsigned char*) malloc(r1*c2*sizeof(double));

    unsigned long exec_times[EXEC_COUNT];
    struct timespec start, end;

    srand(time(NULL));
    for (int i = 0; i < r1 * c1; i++) {
        m1[i] = (double)rand() / RAND_MAX;
    }
    for (int i = 0; i < r2 * c2; i++) {
        m2[i] = (double)rand() / RAND_MAX;
    }

//    char data[sizeof(double)*r1*c2];
//    double* newres = (double*) malloc(r1*c2*sizeof(double));
//    for (int i = 0; i < r1 * c2; i++) {
//        memcpy(data+i*sizeof(double), &result[i], sizeof(double));
//        memcpy(newres+i, data+i*sizeof(double), sizeof(double));
//    }

    double tmp;
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
        clock_gettime(CLOCK_MONOTONIC, &start);
        for (int i = 0; i < r1; i++) {
            for (int j = 0; j < c2; j++) {
                tmp = 0;
                for (int k = 0; k < c1; k++) {
                    tmp += m1[i*c1 + k] * m2[j + k*c2];
                }
                result[i*c2 + j] = tmp;
            }
        }
        for (int i = 0; i < r1 * c2; i++) {
            memcpy(&buf_result[i], (unsigned char*)&result[i], sizeof(double));
        }
        clock_gettime(CLOCK_MONOTONIC, &end);
        exec_times[cnt] = (end.tv_sec - start.tv_sec) * 1000000000;
        exec_times[cnt] += (end.tv_nsec - start.tv_nsec);
    }
    free(m1);
    free(m2);
    free(result);
    for (int i = 0; i < EXEC_COUNT; i++) {
        printf("%lu\n", exec_times[i]);
    }
}

int main(int argc, char* argv[]) {
    /*int r1 = 1000;*/
    /*int c1 = 1000;*/
    /*int r2 = 1000;*/
    /*int c2 = 1000;*/
    /*matrixMultiply(r1, c1, r2, c2, false);*/

    if (argc != 6) {
        fprintf(stderr, "Missing argument\n");
        exit(EXIT_FAILURE);
    }

    bool warmup;
    if (strcmp(argv[5], "y") == 0) {
        warmup = true;
    } else {
        warmup = false;
    }
    int r1 = atoi(argv[1]);
    int c1 = atoi(argv[2]);
    int r2 = atoi(argv[3]);
    int c2 = atoi(argv[4]);
    assert(c1 == r2);
    matrixMultiply(r1, c1, r2, c2, warmup);
}
