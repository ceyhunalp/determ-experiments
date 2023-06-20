//
// Created by Enis Ceyhun Alp on 28.09.22.
//

//#define _POSIX_C_SOURCE 199309L
#include <stdlib.h>
#include <stdio.h>
#include "nan.h"
#include <string.h>
#include <time.h>
#include <assert.h>
#include <errno.h>

void print_matrix(double *m, int dim) {
    for (int i = 0; i < dim; i++) {
        for (int j = 0; j < dim; j++) {
            printf("%.5f ", m[i * dim + j]);
        }
        printf("\n");
    }
    printf("-----\n");
}

void print_measurements(double *times, int exec_count, int dim, int nan_rate, char *outfile) {
    printf("File: %s\n", outfile);
    FILE *fp;
    errno = 0;
    fp = fopen(outfile, "w");
    if (errno != 0) {
        perror("cannot open file\n");
        exit(EXIT_FAILURE);
    }
    for (int i = 0; i < exec_count; ++i) {
        fprintf(fp, "%.5f\n", times[i]);
    }
    fclose(fp);
}

void populate_matrix(double *m, int dim, int nan_rate) {
    if (nan_rate == 100) {
        for (int i = 0; i < dim * dim; i++) {
            m[i] = 0.0 / 0.0;
        }
    } else {
        for (int i = 0; i < dim * dim; i++) {
            m[i] = (double) rand() / RAND_MAX;
        }
        if (nan_rate != 0) {
            int *nan_idxs = get_sequence(dim, nan_rate);
            if (nan_idxs == NULL) {
                fprintf(stderr, "Invalid dim-nan_rate pair\n");
                exit(EXIT_FAILURE);
            }
            int sz = (dim / 100) * nan_rate;
            for (int i = 0; i < dim; ++i) {
                for (int j = 0; j < sz; ++j) {
                    m[i * dim + nan_idxs[j]] = 0.0 / 0.0;
                }
            }
        }
    }
}

void matrixMultiply(int dim, int nan_rate, int exec_count, int warmup_count, int seed) {
    double *m1 = (double *) malloc(dim * dim * sizeof(double));
    double *m2 = (double *) malloc(dim * dim * sizeof(double));
    double *result = (double *) malloc(dim * dim * sizeof(double));
    unsigned char *buf_result = (unsigned char *) malloc(dim * dim * sizeof(double));

    double *exec_times = (double *) malloc(sizeof(double) * exec_count);
    struct timespec start, end;

//    srand(seed);
    srand(time(NULL));
    populate_matrix(m1, dim, nan_rate);
    populate_matrix(m2, dim, nan_rate);

    double tmp;
    for (int cnt = 0; cnt < warmup_count + exec_count; cnt++) {
//        double *result = (double *) malloc(dim * dim * sizeof(double));
//        unsigned char *buf_result = (unsigned char *) malloc(dim * dim * sizeof(double));
        clock_gettime(CLOCK_MONOTONIC, &start);
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                tmp = 0;
                for (int k = 0; k < dim; k++) {
                    tmp += m1[i * dim + k] * m2[j + k * dim];
                }
                result[i * dim + j] = tmp;
            }
        }
        for (int i = 0; i < dim * dim; i++) {
            memcpy(&buf_result[i], (unsigned char *) &result[i], sizeof(double));
        }
        clock_gettime(CLOCK_MONOTONIC, &end);
        if (cnt >= warmup_count) {
            exec_times[cnt - warmup_count] = (end.tv_nsec - start.tv_nsec) / 1000000000.0;
            exec_times[cnt - warmup_count] += (end.tv_sec - start.tv_sec);
        }
//        free(result);
//        free(buf_result);
    }
    free(m1);
    free(m2);
    free(result);
    free(buf_result);
    for (int i = 0; i < exec_count; i++) {
        printf("%.5f\n", exec_times[i]);
    }
    free(exec_times);
}

int main(int argc, char *argv[]) {
    if (argc != 6) {
        fprintf(stderr, "Missing argument\n");
        exit(EXIT_FAILURE);
    }

    int dim = atoi(argv[1]);
    int nan_rate = atoi(argv[2]);
    int warmup_count = atoi(argv[3]);
    int exec_count = atoi(argv[4]);
    int seed = atoi(argv[5]);

    assert(nan_rate == 0 || nan_rate == 1 || nan_rate == 10 || nan_rate == 100);
    matrixMultiply(dim, nan_rate, exec_count, warmup_count, seed);
}
