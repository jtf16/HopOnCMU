package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.LoginCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class LoginSealedCommand extends SealedCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public LoginSealedCommand(String username, SecretKey key, User user) {
        super(username, key, new LoginCommand(user));
    }

    public LoginSealedCommand(String username, SecretKey key, LoginCommand loginCommand) {
        super(username, key, loginCommand);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
