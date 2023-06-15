//
// Created by Enis Ceyhun Alp on 15.06.23.
//

#include "nan.h"
#include <stdlib.h>

const int m100_1[] = {37};

const int m100_10[] = {2, 12, 23, 43, 44, 53, 73, 75, 76, 91};

const int m500_1[] = {10, 273, 282, 389, 475};

const int m500_10[] = {7, 8, 10, 13, 16, 19, 24, 29, 30, 74, 108, 120, 153, 188, 189, 191, 202, 204, 208, 210, 212, 223,
                       229, 232, 238, 254, 262, 277, 279, 285, 288, 290, 292, 296, 308, 315, 317, 322, 330, 334, 338,
                       380, 410, 445, 447, 460, 466, 469, 470, 497};

const int m1000_1[] = {70, 73, 117, 323, 610, 648, 703, 718, 865, 934};

const int m1000_10[] = {21, 28, 33, 38, 51, 55, 65, 66, 70, 82, 83, 84, 86, 100, 144, 145, 146, 169, 174, 176, 178, 189,
                        190, 194, 195, 198, 208, 218, 220, 244, 287, 297, 299, 304, 308, 313, 316, 342, 345, 347, 349,
                        367, 371, 381, 395, 396, 398, 413, 444, 487, 493, 517, 518, 520, 533, 545, 547, 549, 566, 569,
                        571, 573, 597, 599, 602, 613, 626, 637, 659, 660, 689, 709, 718, 720, 737, 746, 752, 761, 767,
                        774, 781, 804, 835, 839, 859, 867, 868, 871, 883, 887, 889, 894, 895, 902, 921, 922, 927, 968,
                        973, 981};

const int m5000_1[] = {44, 112, 365, 446, 535, 566, 807, 886, 903, 1040, 1111, 1137, 1277, 1367, 1394, 1462, 1576, 1639,
                       1683, 1692, 1700, 1785, 1820, 2051, 2284, 2458, 2472, 2592, 2620, 2774, 2848, 3026, 3190, 3281,
                       3355, 3360, 3362, 3591, 3651, 3742, 3797, 3865, 3887, 4137, 4183, 4373, 4499, 4511, 4606, 4752};

const int m5000_10[] = {5, 6, 8, 16, 17, 35, 56, 69, 71, 87, 94, 100, 114, 118, 128, 135, 138, 144, 152, 154, 157, 165,
                        173, 191, 197, 210, 215, 256, 266, 269, 278, 296, 312, 315, 328, 342, 344, 352, 368, 375, 376,
                        380, 414, 424, 425, 427, 428, 432, 435, 444, 450, 463, 468, 478, 487, 491, 497, 499, 511, 515,
                        528, 530, 559, 575, 579, 609, 627, 641, 649, 668, 677, 681, 689, 691, 712, 715, 739, 748, 750,
                        768, 776, 801, 807, 821, 835, 852, 858, 859, 862, 869, 911, 917, 922, 928, 931, 944, 946, 954,
                        957, 976, 978, 984, 1012, 1013, 1014, 1020, 1035, 1042, 1050, 1056, 1065, 1069, 1115, 1131,
                        1150, 1175, 1178, 1192, 1211, 1222, 1227, 1238, 1240, 1242, 1261, 1266, 1267, 1292, 1305, 1309,
                        1312, 1316, 1320, 1323, 1324, 1326, 1329, 1334, 1336, 1347, 1348, 1358, 1374, 1387, 1388, 1389,
                        1408, 1410, 1412, 1427, 1435, 1442, 1444, 1468, 1470, 1474, 1477, 1526, 1538, 1566, 1577, 1620,
                        1642, 1647, 1649, 1653, 1655, 1659, 1677, 1685, 1703, 1713, 1742, 1757, 1767, 1779, 1788, 1800,
                        1803, 1821, 1827, 1834, 1841, 1849, 1864, 1872, 1876, 1882, 1886, 1889, 1893, 1919, 1922, 1945,
                        1948, 1956, 1959, 1967, 1988, 1993, 2008, 2013, 2016, 2024, 2056, 2101, 2113, 2122, 2133, 2146,
                        2158, 2170, 2178, 2183, 2185, 2190, 2197, 2207, 2214, 2216, 2223, 2231, 2233, 2243, 2245, 2247,
                        2271, 2274, 2279, 2293, 2307, 2313, 2316, 2318, 2330, 2337, 2340, 2347, 2353, 2354, 2359, 2391,
                        2404, 2406, 2413, 2430, 2448, 2455, 2461, 2482, 2491, 2493, 2496, 2513, 2528, 2535, 2542, 2557,
                        2568, 2581, 2589, 2597, 2599, 2601, 2618, 2619, 2649, 2661, 2669, 2678, 2681, 2690, 2710, 2717,
                        2721, 2727, 2729, 2732, 2734, 2736, 2741, 2776, 2787, 2796, 2808, 2819, 2836, 2839, 2860, 2862,
                        2868, 2885, 2891, 2900, 2905, 2907, 2920, 2936, 2937, 2955, 2956, 2976, 2990, 2998, 2999, 3011,
                        3041, 3044, 3046, 3060, 3065, 3072, 3075, 3104, 3105, 3108, 3113, 3114, 3115, 3120, 3146, 3149,
                        3161, 3169, 3176, 3186, 3202, 3207, 3219, 3224, 3239, 3243, 3247, 3250, 3301, 3304, 3309, 3326,
                        3339, 3342, 3356, 3357, 3380, 3396, 3399, 3404, 3406, 3416, 3424, 3444, 3487, 3498, 3514, 3547,
                        3566, 3599, 3601, 3614, 3621, 3632, 3645, 3648, 3651, 3652, 3675, 3687, 3713, 3715, 3725, 3726,
                        3730, 3731, 3757, 3765, 3776, 3781, 3798, 3800, 3807, 3810, 3828, 3845, 3847, 3855, 3864, 3894,
                        3922, 3934, 3963, 3969, 3982, 3994, 3999, 4003, 4007, 4013, 4033, 4058, 4059, 4063, 4067, 4079,
                        4090, 4121, 4142, 4173, 4193, 4195, 4197, 4208, 4213, 4218, 4220, 4225, 4233, 4236, 4242, 4247,
                        4254, 4256, 4257, 4259, 4267, 4271, 4276, 4278, 4288, 4294, 4304, 4315, 4328, 4329, 4335, 4371,
                        4380, 4385, 4391, 4393, 4406, 4432, 4449, 4458, 4462, 4463, 4473, 4474, 4501, 4504, 4512, 4520,
                        4524, 4548, 4549, 4555, 4604, 4613, 4637, 4641, 4669, 4671, 4676, 4686, 4690, 4714, 4716, 4721,
                        4725, 4736, 4745, 4755, 4756, 4761, 4765, 4767, 4780, 4782, 4785, 4790, 4806, 4808, 4815, 4819,
                        4828, 4835, 4847, 4870, 4880, 4882, 4883, 4892, 4896, 4909, 4953, 4962, 4971, 4976, 4977, 4986,
                        4988, 4997};

const int m10000_1[] = {47, 73, 105, 330, 431, 455, 485, 515, 673, 841, 917, 1017, 1028, 1181, 1273, 1306, 1312, 1487,
                        1534, 1642, 1718, 1797, 1852, 2146, 2172, 2258, 2384, 2400, 2461, 2505, 2566, 2577, 2601, 2625,
                        2632, 2770, 2897, 3039, 3188, 3209, 3273, 3396, 3547, 3587, 3598, 3769, 3910, 3918, 4016, 4042,
                        4403, 4418, 4575, 4691, 4709, 4761, 4787, 4844, 4952, 5044, 5050, 5125, 5231, 5380, 5720, 5891,
                        5993, 6022, 6137, 6206, 6479, 6608, 6729, 6780, 6808, 6977, 7063, 7138, 7284, 7709, 7850, 8346,
                        8354, 8390, 8800, 8845, 8956, 9194, 9251, 9266, 9376, 9451, 9470, 9519, 9611, 9759, 9794, 9835,
                        9846, 9967};

const int m10000_10[] = {15, 23, 27, 44, 75, 86, 88, 116, 120, 122, 137, 138, 139, 199, 201, 209, 221, 227, 235, 265,
                         275, 285, 309, 310, 324, 325, 327, 328, 332, 339, 357, 371, 380, 386, 387, 388, 390, 402, 447,
                         450, 466, 467, 475, 476, 478, 479, 492, 507, 520, 528, 531, 537, 549, 555, 572, 577, 578, 593,
                         595, 606, 619, 622, 625, 666, 671, 690, 700, 710, 716, 726, 727, 743, 745, 800, 804, 810, 817,
                         826, 830, 833, 837, 843, 854, 862, 885, 893, 894, 921, 934, 938, 943, 950, 953, 970, 975, 986,
                         994, 1001, 1004, 1010, 1016, 1035, 1042, 1043, 1044, 1050, 1053, 1075, 1086, 1099, 1101, 1104,
                         1110, 1122, 1132, 1138, 1142, 1147, 1155, 1167, 1179, 1180, 1183, 1200, 1202, 1207, 1217, 1218,
                         1228, 1236, 1237, 1249, 1252, 1289, 1302, 1316, 1317, 1319, 1322, 1323, 1333, 1342, 1346, 1378,
                         1394, 1404, 1437, 1457, 1485, 1490, 1493, 1510, 1514, 1517, 1519, 1534, 1538, 1541, 1558, 1568,
                         1591, 1595, 1597, 1601, 1628, 1645, 1649, 1661, 1667, 1671, 1691, 1695, 1707, 1708, 1714, 1722,
                         1727, 1744, 1762, 1772, 1773, 1798, 1809, 1811, 1812, 1816, 1833, 1845, 1880, 1902, 1908, 1916,
                         1939, 1945, 1980, 1984, 1988, 2002, 2012, 2028, 2036, 2048, 2049, 2051, 2059, 2082, 2094, 2101,
                         2116, 2134, 2156, 2186, 2192, 2196, 2198, 2210, 2213, 2220, 2240, 2241, 2253, 2254, 2261, 2272,
                         2275, 2286, 2295, 2300, 2305, 2309, 2321, 2338, 2339, 2344, 2349, 2353, 2357, 2372, 2376, 2378,
                         2387, 2390, 2393, 2442, 2455, 2518, 2523, 2525, 2530, 2540, 2541, 2550, 2556, 2566, 2570, 2576,
                         2581, 2584, 2603, 2608, 2618, 2633, 2653, 2670, 2685, 2691, 2720, 2725, 2728, 2747, 2748, 2762,
                         2773, 2774, 2781, 2793, 2834, 2844, 2848, 2849, 2862, 2874, 2881, 2885, 2895, 2906, 2912, 2913,
                         2917, 2931, 2946, 2956, 2972, 2974, 2986, 3010, 3023, 3027, 3029, 3033, 3043, 3050, 3059, 3062,
                         3069, 3076, 3090, 3092, 3094, 3095, 3106, 3137, 3143, 3157, 3159, 3160, 3161, 3178, 3196, 3205,
                         3211, 3216, 3222, 3234, 3235, 3236, 3237, 3248, 3254, 3275, 3283, 3291, 3293, 3296, 3300, 3303,
                         3318, 3320, 3322, 3329, 3332, 3378, 3388, 3394, 3403, 3436, 3449, 3450, 3462, 3513, 3530, 3533,
                         3535, 3539, 3548, 3549, 3558, 3564, 3600, 3621, 3630, 3647, 3652, 3664, 3670, 3671, 3689, 3692,
                         3698, 3701, 3718, 3727, 3734, 3736, 3744, 3745, 3753, 3776, 3786, 3790, 3792, 3823, 3825, 3851,
                         3852, 3864, 3875, 3883, 3889, 3894, 3913, 3919, 3923, 3924, 3940, 3945, 3947, 3957, 3981, 3984,
                         3990, 3999, 4010, 4031, 4042, 4044, 4064, 4074, 4082, 4106, 4125, 4129, 4137, 4155, 4162, 4166,
                         4195, 4203, 4206, 4211, 4218, 4232, 4235, 4245, 4257, 4261, 4280, 4285, 4286, 4295, 4312, 4313,
                         4314, 4317, 4334, 4358, 4360, 4369, 4373, 4375, 4385, 4389, 4394, 4407, 4409, 4415, 4427, 4462,
                         4496, 4499, 4506, 4528, 4538, 4541, 4549, 4563, 4568, 4575, 4579, 4588, 4589, 4604, 4606, 4664,
                         4670, 4673, 4687, 4693, 4717, 4719, 4721, 4734, 4735, 4738, 4747, 4750, 4751, 4769, 4772, 4773,
                         4778, 4794, 4796, 4801, 4813, 4817, 4844, 4854, 4886, 4891, 4898, 4916, 4924, 4930, 4942, 4951,
                         4959, 4965, 4967, 4971, 4972, 4982, 4989, 5036, 5041, 5065, 5066, 5071, 5076, 5077, 5094, 5106,
                         5112, 5114, 5118, 5121, 5131, 5140, 5141, 5142, 5148, 5154, 5189, 5194, 5199, 5200, 5206, 5211,
                         5223, 5224, 5226, 5237, 5245, 5255, 5268, 5274, 5318, 5338, 5346, 5350, 5388, 5389, 5395, 5429,
                         5441, 5454, 5469, 5477, 5480, 5482, 5484, 5490, 5517, 5526, 5528, 5541, 5542, 5590, 5594, 5603,
                         5618, 5634, 5643, 5653, 5683, 5685, 5696, 5714, 5716, 5727, 5736, 5738, 5767, 5790, 5822, 5854,
                         5872, 5895, 5899, 5901, 5911, 5914, 5965, 5966, 5968, 5975, 5980, 5981, 5991, 5996, 6000, 6001,
                         6011, 6012, 6016, 6031, 6034, 6037, 6041, 6052, 6054, 6065, 6072, 6100, 6103, 6153, 6154, 6158,
                         6162, 6164, 6181, 6185, 6196, 6211, 6218, 6231, 6247, 6262, 6264, 6270, 6312, 6314, 6316, 6328,
                         6329, 6346, 6351, 6373, 6390, 6399, 6413, 6417, 6419, 6446, 6468, 6469, 6470, 6502, 6519, 6520,
                         6543, 6547, 6564, 6569, 6579, 6585, 6601, 6612, 6626, 6682, 6689, 6708, 6719, 6731, 6735, 6742,
                         6745, 6749, 6759, 6761, 6762, 6782, 6794, 6812, 6816, 6820, 6822, 6823, 6826, 6831, 6832, 6842,
                         6847, 6863, 6871, 6874, 6883, 6890, 6909, 6912, 6923, 6937, 6947, 6961, 6985, 6986, 6987, 7020,
                         7022, 7032, 7036, 7056, 7060, 7065, 7071, 7072, 7083, 7092, 7093, 7103, 7111, 7125, 7129, 7145,
                         7157, 7162, 7170, 7176, 7177, 7195, 7196, 7198, 7212, 7214, 7215, 7224, 7241, 7248, 7261, 7276,
                         7280, 7281, 7297, 7306, 7307, 7314, 7315, 7323, 7347, 7362, 7363, 7365, 7368, 7392, 7413, 7419,
                         7441, 7458, 7459, 7471, 7480, 7501, 7510, 7518, 7522, 7526, 7527, 7556, 7558, 7561, 7610, 7613,
                         7619, 7628, 7639, 7649, 7662, 7688, 7691, 7692, 7705, 7714, 7721, 7731, 7750, 7766, 7791, 7803,
                         7808, 7820, 7823, 7827, 7835, 7841, 7847, 7870, 7872, 7873, 7874, 7875, 7882, 7888, 7890, 7920,
                         7923, 7929, 7940, 7960, 7963, 7964, 7968, 7988, 7989, 7992, 7998, 7999, 8003, 8004, 8008, 8010,
                         8013, 8018, 8055, 8057, 8079, 8083, 8085, 8104, 8106, 8110, 8122, 8123, 8133, 8151, 8167, 8171,
                         8176, 8182, 8192, 8226, 8229, 8240, 8247, 8262, 8267, 8293, 8314, 8317, 8326, 8328, 8357, 8358,
                         8362, 8374, 8386, 8396, 8399, 8414, 8419, 8425, 8436, 8441, 8443, 8452, 8453, 8459, 8463, 8466,
                         8473, 8474, 8479, 8485, 8495, 8497, 8499, 8500, 8524, 8539, 8542, 8545, 8553, 8563, 8566, 8568,
                         8594, 8607, 8614, 8615, 8621, 8633, 8641, 8653, 8665, 8679, 8684, 8688, 8690, 8692, 8698, 8704,
                         8726, 8728, 8730, 8733, 8739, 8752, 8763, 8776, 8781, 8794, 8805, 8806, 8848, 8876, 8877, 8895,
                         8916, 8921, 8930, 8950, 8995, 9010, 9015, 9019, 9024, 9029, 9047, 9055, 9059, 9060, 9070, 9102,
                         9106, 9111, 9132, 9140, 9148, 9162, 9167, 9168, 9186, 9193, 9212, 9215, 9220, 9225, 9226, 9237,
                         9251, 9288, 9300, 9327, 9350, 9381, 9385, 9401, 9406, 9408, 9416, 9422, 9423, 9429, 9433, 9460,
                         9462, 9464, 9465, 9470, 9482, 9484, 9487, 9488, 9514, 9516, 9535, 9550, 9556, 9562, 9574, 9581,
                         9583, 9592, 9602, 9609, 9614, 9629, 9630, 9633, 9695, 9704, 9711, 9713, 9714, 9715, 9716, 9720,
                         9742, 9755, 9757, 9801, 9819, 9821, 9825, 9827, 9854, 9860, 9868, 9870, 9872, 9877, 9890, 9917,
                         9927, 9932, 9951, 9960, 9961, 9969, 9991, 9997};

int *get_sequence(int dim, int rate) {
    if (dim == 100) {
        if (rate == 1) {
            return (int *) m100_1;
        } else if (rate == 10) {
            return (int *) m100_10;
        } else {
            return NULL;
        }
    } else if (dim == 500) {
        if (rate == 1) {
            return (int *) m500_1;
        } else if (rate == 10) {
            return (int *) m500_10;
        } else {
            return NULL;
        }
    } else if (dim == 1000) {
        if (rate == 1) {
            return (int *) m1000_1;
        } else if (rate == 10) {
            return (int *) m1000_10;
        } else {
            return NULL;
        }
    } else if (dim == 5000) {
        if (rate == 1) {
            return (int *) m5000_1;
        } else if (rate == 10) {
            return (int *) m5000_10;
        } else {
            return NULL;
        }
    } else if (dim == 10000) {
        if (rate == 1) {
            return (int *) m10000_1;
        } else if (rate == 10) {
            return (int *) m10000_10;
        } else {
            return NULL;
        }
    } else {
        return NULL;
    }
}
