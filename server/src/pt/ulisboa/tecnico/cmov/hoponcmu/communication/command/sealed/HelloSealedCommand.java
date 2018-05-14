package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.HelloCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class HelloSealedCommand extends SealedCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public HelloSealedCommand(String username, SecretKey key, String message) {
        super(username, key, new HelloCommand(message));
    }

    public HelloSealedCommand(String username, SecretKey key, HelloCommand helloCommand) {
        super(username, key, helloCommand);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
