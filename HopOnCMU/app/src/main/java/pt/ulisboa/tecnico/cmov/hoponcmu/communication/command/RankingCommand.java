package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class RankingCommand implements Command {

    private static final long serialVersionUID = -8807331723807741905L;

    public RankingCommand() {
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
