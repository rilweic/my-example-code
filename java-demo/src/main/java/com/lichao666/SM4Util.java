package com.lichao666;

import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.Base64;

public class SM4Util {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static String encrypt(String plainText, String key, String iv) throws CryptoException {
        byte[] keyBytes = key.getBytes();
        byte[] ivBytes = iv.getBytes();
        byte[] plainTextBytes = plainText.getBytes();

        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new SM4Engine()), new PKCS7Padding());
        cipher.init(true, new ParametersWithIV(new KeyParameter(keyBytes), ivBytes));

        byte[] out = new byte[cipher.getOutputSize(plainTextBytes.length)];
        int outputLen = cipher.processBytes(plainTextBytes, 0, plainTextBytes.length, out, 0);
        outputLen += cipher.doFinal(out, outputLen);

        return Base64.getEncoder().encodeToString(out);
    }

    public static String decrypt(String encryptedText, String key, String iv) throws CryptoException {
        byte[] keyBytes = key.getBytes();
        byte[] ivBytes = iv.getBytes();
        byte[] encryptedTextBytes = Base64.getDecoder().decode(encryptedText);

        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new SM4Engine()), new PKCS7Padding());
        cipher.init(false, new ParametersWithIV(new KeyParameter(keyBytes), ivBytes));

        byte[] out = new byte[cipher.getOutputSize(encryptedTextBytes.length)];
        int outputLen = cipher.processBytes(encryptedTextBytes, 0, encryptedTextBytes.length, out, 0);
        outputLen += cipher.doFinal(out, outputLen);

        return new String(out, 0, outputLen);
    }

    public static void main(String[] args) throws CryptoException {
        String key = "6aiWkioHOkKcIPXZ"; // 16 byte key for SM4
        String iv = "rbt2Hodlcz8jiQhb";  // 16 byte IV for SM4
        String plainText = "这个是明文";

        String encrypted = encrypt(plainText, key, iv);
        String decrypted = decrypt(encrypted, key, iv);

        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
