package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

public class MonumentResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private Monument[] monuments;

    public MonumentResponse(Monument[] monuments) {
        this.monuments = monuments;
    }

    public Monument[] getMonuments() {
        return monuments;
    }
}
