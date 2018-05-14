package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class DownloadQuizSealedCommand extends SealedCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public DownloadQuizSealedCommand(String username, SecretKey key, long sessionID, Quiz quiz) {
        super(username, key, new DownloadQuizCommand(username, sessionID, quiz));
    }

    public DownloadQuizSealedCommand(String username, SecretKey key,
                                     DownloadQuizCommand downloadQuizCommand) {
        super(username, key, downloadQuizCommand);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
