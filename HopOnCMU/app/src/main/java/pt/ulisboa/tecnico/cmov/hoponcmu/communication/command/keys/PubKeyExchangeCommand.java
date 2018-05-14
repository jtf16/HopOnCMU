package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.keys;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.Command;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class PubKeyExchangeCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private String username;
    private byte[] publicKey;

    public PubKeyExchangeCommand(String username, byte[] publicKey) {
        this.username = username;
        this.publicKey = publicKey;
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }
}
