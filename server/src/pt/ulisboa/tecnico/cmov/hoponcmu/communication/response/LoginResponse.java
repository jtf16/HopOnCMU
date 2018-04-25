package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class LoginResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private User user;
    private long sessionId;

    public LoginResponse(User user, long sessionId) {
        this.user = user;
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public long getSessionId() {
        return sessionId;
    }
}
