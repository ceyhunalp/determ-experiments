package contract;

import org.apache.tuweni.crypto.sodium.Sodium;

import java.util.Arrays;

import static contract.Write.ED25519_CORE_BYTES;
import static contract.Write.MAX_EMBED_SZ;

public class Point {

    public static int embedData(byte[] data, int n, byte[] buf, byte[] seed) {
        int dl = MAX_EMBED_SZ;
        if (dl > n) {
            dl = n;
        }

        byte[] randomBuf = new byte[1024 * ED25519_CORE_BYTES];
        Sodium.randombytes_buf_deterministic(randomBuf,
                1024L * ED25519_CORE_BYTES, seed);

        int i = 0;
        while (i < 1023) {
            Arrays.fill(buf, (byte) 0);
            if (ED25519_CORE_BYTES >= 0) {
                System.arraycopy(randomBuf, i, buf, 0, ED25519_CORE_BYTES);
                i += ED25519_CORE_BYTES;
            }
            if (n > 0) {
                buf[0] = (byte) (dl);
                if (dl >= 0) System.arraycopy(data, 0, buf, 1, dl);
                if (Sodium.crypto_core_ed25519_is_valid_point(buf) == 1) {
                    return 0;
                }
            }
        }
        return -1;
    }

    public static void printHex(byte[] buf) {
        for (byte b :
                buf) {
            System.out.printf("%02X", b);
        }
        System.out.println();
    }
}
