package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.InvalidQuizExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.PasswordExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.SessionExceptionResponse;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions.UsernameExceptionResponse;

public interface ResponseHandler {
    public void handle(DownloadQuizResponse downloadQuizResponse);

    public void handle(HelloResponse helloResponse);

    public void handle(InvalidQuizExceptionResponse invalidQuizExceptionResponse);

    public void handle(LoginResponse loginResponse);

    public void handle(MonumentResponse monumentResponse);

    public void handle(PubKeyExchangeResponse pubKeyExchangeResponse);

    public void handle(PasswordExceptionResponse passwordExceptionResponse);

    public void handle(RankingResponse rankingResponse);

    public void handle(SessionExceptionResponse sessionExceptionResponse);

    public void handle(SetUpResponse setUpResponse);

    public void handle(SignUpResponse signUpResponse);

    public void handle(SubmitQuizResponse submitQuizResponse);

    public void handle(UsernameExceptionResponse usernameExceptionResponse);
}
