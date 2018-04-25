package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class SetUpCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;

    public SetUpCommand() {

    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
