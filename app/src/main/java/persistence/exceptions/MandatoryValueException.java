package persistence.exceptions;

/**
 * Exception to handle mandatory entries, which were not given.
 */
public class MandatoryValueException extends Exception {

    /**
     * Constructor, creates a new instance of the exception.
     *
     * @param value the name of the field/variable, whose value is mandatory but not given (empty)
     */
    public MandatoryValueException(String value) {
        super("Value(s) " + value + " must be given.");
    }
}
