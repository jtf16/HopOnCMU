package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.RankingCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class RankingSealedCommand extends SealedCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public RankingSealedCommand(String username, SecretKey key) {
        super(username, key, new RankingCommand());
    }

    public RankingSealedCommand(String username, SecretKey key, RankingCommand rankingCommand) {
        super(username, key, rankingCommand);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
