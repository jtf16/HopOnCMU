package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.sealed;

import java.io.IOException;
import java.security.InvalidKeyException;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.security.SecurityManager;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.*;

public class SealedResponse implements Response{

    private static final long serialVersionUID = -8807331723807741905L;

    private byte[] digest;
    private SealedObject sealedContent;

    public SealedResponse(SecretKey key, Object object) {
        try {
            this.digest = SecurityManager.digestObject(object);
            this.sealedContent = SecurityManager.encryptObject(object, key);
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public byte[] getDigest() {
        return digest;
    }

    public SealedObject getSealedContent() {
        return sealedContent;
    }
}
