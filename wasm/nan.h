//
// Created by Enis Ceyhun Alp on 15.06.23.
//

#ifndef WASM_NAN_H
#define WASM_NAN_H

extern const int m100_1[];
extern const int m500_1[];
extern const int m1000_1[];
extern const int m2000_1[];
extern const int m5000_1[];
extern const int m10000_1[];

extern const int m100_10[];
extern const int m500_10[];
extern const int m1000_10[];
extern const int m2000_10[];
extern const int m5000_10[];
extern const int m10000_10[];

extern int *get_sequence(int dim, int rate);

#endif //WASM_NAN_H
