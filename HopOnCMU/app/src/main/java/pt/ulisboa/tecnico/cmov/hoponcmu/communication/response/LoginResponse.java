package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class LoginResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private User user;
    private long sessionId;
    private boolean[] errors;

    public LoginResponse(User user, long sessionId, boolean[] errors) {
        this.user = user;
        this.sessionId = sessionId;
        this.errors = errors;
    }

    public User getUser() {
        return user;
    }

    public long getSessionId() {
        return sessionId;
    }

    public boolean[] getErrors() {
        return errors;
    }
}
