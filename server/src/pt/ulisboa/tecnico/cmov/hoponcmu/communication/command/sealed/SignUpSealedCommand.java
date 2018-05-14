package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.SignUpCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class SignUpSealedCommand extends SealedCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public SignUpSealedCommand(String username, SecretKey key, User user) {
        super(username, key, new SignUpCommand(user));
    }

    public SignUpSealedCommand(String username, SecretKey key, SignUpCommand signUpCommand) {
        super(username, key, signUpCommand);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
