package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.Command;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.security.SecurityManager;

public abstract class SealedCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;

    private String username;
    private byte[] digest;
    private SealedObject sealedContent;

    SealedCommand(String username, SecretKey key, Object object) {
        this.username = username;
        try {
            this.digest = SecurityManager.digestObject(object);
            this.sealedContent = SecurityManager.encryptObject(object, key);
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public abstract Response handle(CommandHandler ch);

    public String getUsername() {
        return username;
    }

    public byte[] getDigest() {
        return digest;
    }

    public SealedObject getSealedContent() {
        return sealedContent;
    }
}
