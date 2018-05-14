package pt.ulisboa.tecnico.cmov.hoponcmu.security;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import pt.ulisboa.tecnico.cmov.hoponcmu.activities.LoginActivity;

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

    public static KeyPair getNewKeyPair(@NonNull String algorithm) throws NoSuchAlgorithmException {
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

    public static KeyPair getKeyPair(@NonNull String algorithm) throws NoSuchAlgorithmException {
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

    public static PublicKey byteArrayToPubKey(byte[] publicKey, @NonNull String algorithm)
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

    public static PrivateKey byteArrayToPrivateKey(byte[] privateKey) {
        try {
            return byteArrayToPrivateKey(privateKey, ASYMMETRIC_KEYS_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PrivateKey byteArrayToPrivateKey(byte[] privateKey, @NonNull String algorithm)
            throws NoSuchAlgorithmException {
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec pkSpec = new PKCS8EncodedKeySpec(privateKey);
        try {
            return kf.generatePrivate(pkSpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SecretKey byteArrayToSecretKey(byte[] secretKey) {
        return byteArrayToSecretKey(secretKey, ASYMMETRIC_KEYS_ALGORITHM);
    }

    public static SecretKey byteArrayToSecretKey(byte[] secretKey, @NonNull String algorithm) {
        return new SecretKeySpec(secretKey, 0, secretKey.length, algorithm);
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
                                               @NonNull String algorithm)
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
                                                 @NonNull String keyAgreementAlgorithm,
                                                 @NonNull String algorithm)
            throws NoSuchAlgorithmException, InvalidKeyException {

        PublicKey pubKey = byteArrayToPubKey(publicKey);

        if (pubKey != null) {
            KeyAgreement keyAgreement = getKeyAgreement(privateKey, pubKey, keyAgreementAlgorithm);
            return keyAgreement != null ? keyAgreement.generateSecret(algorithm) : null;
        }
        return null;
    }

    public static SecretKey getSecretKey(SharedPreferences preferences) {

        byte[] sharedSecretArray = Base64.decode(
                preferences.getString(LoginActivity.SHARED_SECRET, ""), Base64.DEFAULT);

        return SecurityManager.byteArrayToSecretKey(sharedSecretArray);
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

    public static Cipher getCipher(SecretKey key, int cipherMode, @NonNull String algorithm)
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
        byte[] data = SerializationUtils.serialize((Serializable) object);

        MessageDigest digest = MessageDigest.getInstance(algorithm);
        return digest.digest(data);
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
}
