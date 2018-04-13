package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public interface CommandHandler {
    public Response handle(HelloCommand helloCommand);
    public Response handle(SignUpCommand signUpCommand);
    public Response handle(LoginCommand loginCommand);
    public Response handle(MonumentCommand monumentCommand);
    public Response handle(DownloadQuizCommand downloadQuizCommand);
    public Response handle(RankingCommand rankingCommand);
    public Response handle(SubmitQuizCommand submitQuizCommand);
}
