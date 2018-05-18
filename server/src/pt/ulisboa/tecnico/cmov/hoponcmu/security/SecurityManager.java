package pt.ulisboa.tecnico.cmov.hoponcmu.security;

import java.io.*;

import java.security.*;
import java.security.spec.*;
import java.security.interfaces.*;

import java.util.*;
import java.nio.ByteBuffer;

import javax.crypto.*;
import javax.crypto.spec.*;

public class SecurityManager {

    private static final String ALGORITHM = "AES";
    private static final String ASYMMETRIC_KEYS_ALGORITHM = "EC";
    private static final String KEY_AGREEMENT_INSTANCE = "ECDH";
    private static final String DIGEST_INSTANCE = "SHA-256";
    private static KeyPair keyPair;

    public static KeyPair getNewKeyPair() {
        try {
            return getNewKeyPair(ASYMMETRIC_KEYS_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyPair getNewKeyPair(String algorithm) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
        kpg.initialize(256);
        keyPair = kpg.generateKeyPair();
        return keyPair;
    }

    public static KeyPair getKeyPair() {
        try {
            return getKeyPair(ASYMMETRIC_KEYS_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyPair getKeyPair(String algorithm) throws NoSuchAlgorithmException {
        if (keyPair == null) {
            return getNewKeyPair(algorithm);
        }
        return keyPair;
    }

    public static PublicKey byteArrayToPubKey(byte[] publicKey) {
        try {
            return byteArrayToPubKey(publicKey, ASYMMETRIC_KEYS_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PublicKey byteArrayToPubKey(byte[] publicKey, String algorithm)
            throws NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec pkSpec = new X509EncodedKeySpec(publicKey);
        try {
            return kf.generatePublic(pkSpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyAgreement getKeyAgreement(PrivateKey privateKey, PublicKey publicKey)
            throws InvalidKeyException {
        try {
            return getKeyAgreement(privateKey, publicKey, KEY_AGREEMENT_INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static KeyAgreement getKeyAgreement(PrivateKey privateKey, PublicKey publicKey,
                                               String algorithm)
            throws NoSuchAlgorithmException, InvalidKeyException {
        KeyAgreement keyAgreement = KeyAgreement.getInstance(algorithm);
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(publicKey, true);
        return keyAgreement;
    }

    public static SecretKey generateSharedSecret(PrivateKey privateKey, byte[] publicKey)
            throws InvalidKeyException {
        try {
            return generateSharedSecret(privateKey, publicKey, KEY_AGREEMENT_INSTANCE, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKey generateSharedSecret(PrivateKey privateKey, byte[] publicKey,
                                                 String keyAgreementAlgorithm,
                                                 String algorithm)
            throws NoSuchAlgorithmException, InvalidKeyException {
        PublicKey pubKey = byteArrayToPubKey(publicKey);

        if (pubKey != null) {
            KeyAgreement keyAgreement = getKeyAgreement(privateKey, pubKey, keyAgreementAlgorithm);
            if (keyAgreement != null) {
                byte[] encodedKey = keyAgreement.generateSecret();
                return new SecretKeySpec(encodedKey, 0, encodedKey.length, algorithm);
            }
            return null;
        }
        return null;
    }

    public static Cipher getCipher(SecretKey key, int cipherMode)
            throws InvalidKeyException {
        try {
            return getCipher(key, cipherMode, ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Cipher getCipher(SecretKey key, int cipherMode, String algorithm)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(cipherMode, key);
        return cipher;
    }

    public static SealedObject encryptObject(Object object, SecretKey key)
            throws IOException, IllegalBlockSizeException, InvalidKeyException {
        return new SealedObject((Serializable) object, getCipher(key, Cipher.ENCRYPT_MODE));
    }

    public static Object decryptObject(SealedObject sealedObject, SecretKey key)
            throws InvalidKeyException, ClassNotFoundException, BadPaddingException,
            IllegalBlockSizeException, IOException {
        return sealedObject.getObject(getCipher(key, Cipher.DECRYPT_MODE));
    }

    public static byte[] digestObject(Object object) {
        try {
            return digestObject(object, DIGEST_INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] digestObject(Object object, String algorithm) 
            throws NoSuchAlgorithmException {
        byte[] data = null;
        try {
            data = convertToBytes((Serializable) object);
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            return digest.digest(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verifyDigest(Object object, byte[] digestObject) {
        try {
            MessageDigest digest = MessageDigest.getInstance(DIGEST_INSTANCE);
            return digest.isEqual(digestObject, digestObject(object));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static byte[] convertToBytes(Object object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(object);
            return bos.toByteArray();
        }
    }
}
