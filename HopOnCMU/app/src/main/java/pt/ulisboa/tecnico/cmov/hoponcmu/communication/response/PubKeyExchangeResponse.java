package pt.ulisboa.tecnico.cmov.hoponcmu.communication.response;

public class PubKeyExchangeResponse implements Response {

    private static final long serialVersionUID = 734457624276534179L;
    private byte[] publicKey;

    public PubKeyExchangeResponse(byte[] publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }
}
