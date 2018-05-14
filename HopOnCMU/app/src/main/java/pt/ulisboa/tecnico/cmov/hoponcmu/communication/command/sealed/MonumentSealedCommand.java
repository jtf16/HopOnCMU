package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.MonumentCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class MonumentSealedCommand extends SealedCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public MonumentSealedCommand(String username, SecretKey key) {
        super(username, key, new MonumentCommand());
    }

    public MonumentSealedCommand(String username, SecretKey key, MonumentCommand monumentCommand) {
        super(username, key, monumentCommand);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}