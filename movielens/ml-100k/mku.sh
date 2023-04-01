#!/bin/sh

trap `rm -f tmp.$$; exit 1` 1 2 15

for i in 1 2 3 4 5
do
	ghead -`expr $i \* 20000` u.data | tail -20000 > tmp.$$
	sort -t"	" -k 1,1n -k 2,2n tmp.$$ > r$i.test
	ghead -`expr \( $i - 1 \) \* 20000` u.data > tmp.$$
	tail -`expr \( 5 - $i \) \* 20000` u.data >> tmp.$$
	sort -t"	" -k 1,1n -k 2,2n tmp.$$ > r$i.base
done

#./allbut.pl ra 1 10 100000 u.data
#sort -t"	" -k 1,1n -k 2,2n ra.base > tmp.$$
#mv tmp.$$ ra.base
#sort -t"	" -k 1,1n -k 2,2n ra.test > tmp.$$
#mv tmp.$$ ra.test

#./allbut.pl rb 11 20 100000 u.data
#sort -t"	" -k 1,1n -k 2,2n rb.base > tmp.$$
#mv tmp.$$ rb.base
#sort -t"	" -k 1,1n -k 2,2n rb.test > tmp.$$
#mv tmp.$$ rb.test
