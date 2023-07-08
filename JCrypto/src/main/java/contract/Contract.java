package contract;

import org.apache.tuweni.crypto.sodium.Sodium;

import java.nio.ByteBuffer;

public class Contract {

    public static void runCalypsoWriteCheck() {


        Integer.valueOf(4);

        Write w = new Write(17);

        byte[] X = new byte[Write.ED25519_CORE_BYTES];
        Sodium.crypto_core_ed25519_random(X);

        byte[] key = new byte[Write.KEY_SIZE];
        Sodium.randombytes(key, Write.KEY_SIZE);

        byte[] seedBytes = ByteBuffer.allocate(4).putInt(23).array();
        w.newWrite(X, key, Write.KEY_SIZE, seedBytes);

        System.out.println("======= Verify write proof =========");
        System.out.println(w.checkProof());

    }

    public static void main(String[] args) {
        runCalypsoWriteCheck();
    }
}
