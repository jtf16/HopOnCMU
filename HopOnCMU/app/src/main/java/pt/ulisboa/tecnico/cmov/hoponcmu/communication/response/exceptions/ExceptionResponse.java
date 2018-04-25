package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class ExceptionResponse implements Response {

    public String message;

    public String getMessage() {
        return message;
    }
}
