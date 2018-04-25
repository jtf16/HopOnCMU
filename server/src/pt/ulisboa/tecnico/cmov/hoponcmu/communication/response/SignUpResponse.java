package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class SignUpResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private User user;

    public SignUpResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
