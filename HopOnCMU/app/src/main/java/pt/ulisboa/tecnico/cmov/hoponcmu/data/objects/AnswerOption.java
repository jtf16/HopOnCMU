package pt.ulisboa.tecnico.cmov.hoponcmu.data.objects;

public enum AnswerOption {
    NULL(-1),
    OPTION_A(0),
    OPTION_B(1),
    OPTION_C(2),
    OPTION_D(3);

    private final int value;

    AnswerOption(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
