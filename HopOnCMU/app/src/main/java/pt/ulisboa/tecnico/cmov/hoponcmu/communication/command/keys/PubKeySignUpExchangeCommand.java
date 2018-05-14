package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.keys;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class PubKeySignUpExchangeCommand extends PubKeyExchangeCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public PubKeySignUpExchangeCommand(String username, byte[] publicKey) {
        super(username, publicKey);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
