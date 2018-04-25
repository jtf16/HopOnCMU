package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response.exceptions;

public class InvalidQuizExceptionResponse extends ExceptionResponse {

    private static final long serialVersionUID = 734457624276534179L;

    public InvalidQuizExceptionResponse(String message) {
        this.message = message;
    }
}
