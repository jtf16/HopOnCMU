package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.User;

public class RankingResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private User[] users;

    public RankingResponse(User[] users) {
        this.users = users;
    }

    public User[] getUsers() {
        return users;
    }
}
