package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class SignUpCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;
    private User user;

    public SignUpCommand(User user) {
        this.user = user;
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }

    public User getUser() {
        return user;
    }
}
