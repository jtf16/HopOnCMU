package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

public interface ResponseHandler {
    public void handle(HelloResponse helloResponse);

    public void handle(SignUpResponse signUpResponse);
}
