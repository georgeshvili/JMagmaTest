package ru.bk252.kkso16;

public class Magma {

    final private static byte[] piSubstitution = {
            0xc, 0x4, 0x6, 0x2, 0xa, 0x5, 0xb, 0x9, 0xe, 0x8, 0xd, 0x7, 0x0, 0x3, 0xf, 0x1,
            0x6, 0x8, 0x2, 0x3, 0x9, 0xa, 0x5, 0xc, 0x1, 0xe, 0x4, 0x7, 0xb, 0xd, 0x0, 0xf,
            0xb, 0x3, 0x5, 0x8, 0x2, 0xf, 0xa, 0xd, 0xe, 0x1, 0x7, 0x4, 0xc, 0x9, 0x6, 0x0,
            0xc, 0x8, 0x2, 0x1, 0xd, 0x4, 0xf, 0x6, 0x7, 0x0, 0xa, 0x5, 0x3, 0xe, 0x9, 0xb,
            0x7, 0xf, 0x5, 0xa, 0x8, 0x1, 0x6, 0xd, 0x0, 0x9, 0x3, 0xe, 0xb, 0x4, 0x2, 0xc,
            0x5, 0xd, 0xf, 0x6, 0x9, 0x2, 0xc, 0xa, 0xb, 0x7, 0x8, 0x1, 0x4, 0x3, 0xe, 0x0,
            0x8, 0xe, 0x2, 0x5, 0x6, 0x9, 0x1, 0xc, 0xf, 0x4, 0xb, 0x0, 0xd, 0xa, 0x3, 0x7,
            0x1, 0x7, 0xe, 0xd, 0x0, 0x5, 0x8, 0x3, 0x4, 0xf, 0xa, 0x6, 0x9, 0xc, 0xb, 0x2
    };

    final private int[] key;

    public Magma(int[] key) {
        if (key.length != 8) {
            throw new IllegalArgumentException("Invalid key size");
        }
        this.key = key;
    }

    private int transformRound(int roundKeyIndex, int a1, int a0) {
        int g = a0 + this.key[roundKeyIndex];

        int t =
                (Magma.piSubstitution[0 * 16 + ((g >> 0 * 4) & 0xf)] << 0 * 4) |
                        (Magma.piSubstitution[1 * 16 + ((g >> 1 * 4) & 0xf)] << 1 * 4) |
                        (Magma.piSubstitution[2 * 16 + ((g >> 2 * 4) & 0xf)] << 2 * 4) |
                        (Magma.piSubstitution[3 * 16 + ((g >> 3 * 4) & 0xf)] << 3 * 4) |
                        (Magma.piSubstitution[4 * 16 + ((g >> 4 * 4) & 0xf)] << 4 * 4) |
                        (Magma.piSubstitution[5 * 16 + ((g >> 5 * 4) & 0xf)] << 5 * 4) |
                        (Magma.piSubstitution[6 * 16 + ((g >> 6 * 4) & 0xf)] << 6 * 4) |
                        (Magma.piSubstitution[7 * 16 + ((g >> 7 * 4) & 0xf)] << 7 * 4);

        return a1 ^ ((t << 11) | (t >>> 21));
    }

    public long encryptBlock(long block) {
        int a0 = (int) (block & 0xffffffffL);
        int a1 = (int) ((block >> 32) & 0xffffffffL);

        a1 = this.transformRound(0, a1, a0);
        a0 = this.transformRound(1, a0, a1);
        a1 = this.transformRound(2, a1, a0);
        a0 = this.transformRound(3, a0, a1);
        a1 = this.transformRound(4, a1, a0);
        a0 = this.transformRound(5, a0, a1);
        a1 = this.transformRound(6, a1, a0);
        a0 = this.transformRound(7, a0, a1);

        a1 = this.transformRound(0, a1, a0);
        a0 = this.transformRound(1, a0, a1);
        a1 = this.transformRound(2, a1, a0);
        a0 = this.transformRound(3, a0, a1);
        a1 = this.transformRound(4, a1, a0);
        a0 = this.transformRound(5, a0, a1);
        a1 = this.transformRound(6, a1, a0);
        a0 = this.transformRound(7, a0, a1);

        a1 = this.transformRound(0, a1, a0);
        a0 = this.transformRound(1, a0, a1);
        a1 = this.transformRound(2, a1, a0);
        a0 = this.transformRound(3, a0, a1);
        a1 = this.transformRound(4, a1, a0);
        a0 = this.transformRound(5, a0, a1);
        a1 = this.transformRound(6, a1, a0);
        a0 = this.transformRound(7, a0, a1);

        a1 = this.transformRound(7, a1, a0);
        a0 = this.transformRound(6, a0, a1);
        a1 = this.transformRound(5, a1, a0);
        a0 = this.transformRound(4, a0, a1);
        a1 = this.transformRound(3, a1, a0);
        a0 = this.transformRound(2, a0, a1);
        a1 = this.transformRound(1, a1, a0);
        a0 = this.transformRound(0, a0, a1);

        return ((long) a0 << 32) | ((long) a1 & 0xffffffffL);
    }

    public long decryptBlock(long block) {
        int a0 = (int) (block & 0xffffffffL);
        int a1 = (int) ((block >> 32) & 0xffffffffL);

        a1 = this.transformRound(0, a1, a0);
        a0 = this.transformRound(1, a0, a1);
        a1 = this.transformRound(2, a1, a0);
        a0 = this.transformRound(3, a0, a1);
        a1 = this.transformRound(4, a1, a0);
        a0 = this.transformRound(5, a0, a1);
        a1 = this.transformRound(6, a1, a0);
        a0 = this.transformRound(7, a0, a1);

        a1 = this.transformRound(7, a1, a0);
        a0 = this.transformRound(6, a0, a1);
        a1 = this.transformRound(5, a1, a0);
        a0 = this.transformRound(4, a0, a1);
        a1 = this.transformRound(3, a1, a0);
        a0 = this.transformRound(2, a0, a1);
        a1 = this.transformRound(1, a1, a0);
        a0 = this.transformRound(0, a0, a1);

        a1 = this.transformRound(7, a1, a0);
        a0 = this.transformRound(6, a0, a1);
        a1 = this.transformRound(5, a1, a0);
        a0 = this.transformRound(4, a0, a1);
        a1 = this.transformRound(3, a1, a0);
        a0 = this.transformRound(2, a0, a1);
        a1 = this.transformRound(1, a1, a0);
        a0 = this.transformRound(0, a0, a1);

        a1 = this.transformRound(7, a1, a0);
        a0 = this.transformRound(6, a0, a1);
        a1 = this.transformRound(5, a1, a0);
        a0 = this.transformRound(4, a0, a1);
        a1 = this.transformRound(3, a1, a0);
        a0 = this.transformRound(2, a0, a1);
        a1 = this.transformRound(1, a1, a0);
        a0 = this.transformRound(0, a0, a1);

        return ((long) a0 << 32) | ((long) a1 & 0xffffffffL);
    }
}