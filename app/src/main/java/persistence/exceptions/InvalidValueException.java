package persistence.exceptions;

/**
 * Exception to handle invalid values, such as characters when only numbers were expected.
 */
public class InvalidValueException extends Exception {

    private final String name;

    /**
     * Constructor, creates a new instance of this exception.
     *
     * @param value the name of the field/variable, whose value was invalid
     */
    public InvalidValueException(String value) {
        super("Invalid value(s) in " + value + ".");
        name = value;
    }

    /**
     * Getter for the variable name.
     *
     * @return a String containing the name of the value which is invalid.
     */
    public String getName() {
        return this.name;
    }
}
