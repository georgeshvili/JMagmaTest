package ru.bk252.kkso16;



import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class MagmaTest {

    Magma m = new Magma(new int[]{0xffeeddcc, 0xbbaa9988, 0x77665544, 0x33221100, 0xf0f1f2f3, 0xf4f5f6f7, 0xf8f9fafb, 0xfcfdfeff});

    @Test
    public void magmaWorkingRight() {
        long encryptionResult = m.encryptBlock(0xfedcba9876543210L);
        long decryptionResult = m.decryptBlock(encryptionResult);
        assertEquals(0xfedcba9876543210L, decryptionResult);
    }
}
