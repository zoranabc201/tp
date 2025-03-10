package seedu.duke.exception;

public class ExistingPairException extends DukeCommandException {

    private final String message;

    public ExistingPairException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
