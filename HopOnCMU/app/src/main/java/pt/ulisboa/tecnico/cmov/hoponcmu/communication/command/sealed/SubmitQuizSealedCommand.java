package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed;

import java.util.List;

import javax.crypto.SecretKey;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.CommandHandler;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.SubmitQuizCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Question;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

public class SubmitQuizSealedCommand extends SealedCommand {

    private static final long serialVersionUID = -8807331723807741905L;

    public SubmitQuizSealedCommand(String username, SecretKey key, long sessionID,
                                   UserQuiz userQuiz, List<Question> questions) {
        super(username, key, new SubmitQuizCommand(sessionID, userQuiz, questions));
    }

    public SubmitQuizSealedCommand(String username, SecretKey key,
                                   SubmitQuizCommand submitQuizCommand) {
        super(username, key, submitQuizCommand);
    }

    @Override
    public Response handle(CommandHandler chi) {
        return chi.handle(this);
    }
}
