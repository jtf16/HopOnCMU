package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions;

import pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.Response;

public class ExceptionResponse implements Response {

    private String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
