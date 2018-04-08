package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

public interface ResponseHandler {
    public void handle(HelloResponse helloResponse);

    public void handle(SignUpResponse signUpResponse);

    public void handle(LoginResponse loginResponse);

    public void handle(MonumentResponse monumentResponse);

    public void handle(DownloadQuizResponse downloadQuizResponse);
}
