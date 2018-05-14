package pt.ulisboa.tecnico.cmov.hoponcmu.communication.command;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.keys.PubKeyExchangeCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.keys.PubKeySignUpExchangeCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.DownloadQuizSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.HelloSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.LoginSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.MonumentSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.RankingSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.SignUpSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.sealed.SubmitQuizSealedCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public interface CommandHandler {
    public Response handle(DownloadQuizCommand downloadQuizCommand);

    public Response handle(DownloadQuizSealedCommand downloadQuizSealedCommand);

    public Response handle(HelloCommand helloCommand);

    public Response handle(HelloSealedCommand helloSealedCommand);

    public Response handle(LoginCommand loginCommand);

    public Response handle(LoginSealedCommand loginSealedCommand);

    public Response handle(MonumentCommand monumentCommand);

    public Response handle(MonumentSealedCommand monumentSealedCommand);

    public Response handle(PubKeyExchangeCommand pubKeyExchangeCommand);

    public Response handle(PubKeySignUpExchangeCommand pubKeySignUpExchangeCommand);

    public Response handle(RankingCommand rankingCommand);

    public Response handle(RankingSealedCommand rankingSealedCommand);

    public Response handle(SetUpCommand setUpCommand);

    public Response handle(SignUpCommand signUpCommand);

    public Response handle(SignUpSealedCommand signUpSealedCommand);

    public Response handle(SubmitQuizCommand submitQuizCommand);

    public Response handle(SubmitQuizSealedCommand submitQuizSealedCommand);
}
