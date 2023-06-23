import sys
import pandas as pd
from os.path import join

BASE_DIR = "./prelim/"
OUT_DIR = join(BASE_DIR, "stats/")
SMATH = "smath"
MPFR = "mpfr"

def compute_stats(fname):
    in_smath = join(BASE_DIR, SMATH + "_" + fname + ".csv")
    in_mpfr = join(BASE_DIR, MPFR + "_" + fname + ".csv")
    # outfile = join(OUT_DIR, fname + ".txt")
    outfile = join(OUT_DIR, fname + ".csv")

    fd_smath = pd.read_csv(in_smath, header=None, names=['time'])
    fd_mpfr = pd.read_csv(in_mpfr, header=None, names=['time'])
    sm_vals = [fd_smath['time'].mean(), fd_smath['time'].std()]
    mpfr_vals = [fd_mpfr['time'].mean(), fd_mpfr['time'].std()]

    f = open(outfile, "w")
    f.write("%s,%f (%f),%f (%f)\n" % (fname, sm_vals[0], sm_vals[1], mpfr_vals[0],
        mpfr_vals[1]))

    # print("%s %f (%f) %f (%f)" % (fname, sm_vals[0], sm_vals[1], mpfr_vals[0], mpfr_vals[1]))

def main():
    args = sys.argv
    if len(args) != 2:
        sys.exit("Missing argument")
    compute_stats(sys.argv[1])

if __name__ == "__main__":
    main()
