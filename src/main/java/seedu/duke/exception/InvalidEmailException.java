package seedu.duke.exception;

import static seedu.duke.Messages.MESSAGE_INVALID_EMAIL;

//@@author OVReader
/**
 * Represents exception when incorrect email format is given when adding client.
 */
public class InvalidEmailException extends DukeParseException {
    @Override
    public String toString() {
        return MESSAGE_INVALID_EMAIL;
    }
}
