package persistence.exceptions;

public class PersistenceException extends Exception {

    /**
     * Constructor, creates a new instance of the exception.
     *
     * @param value a String containing the message of the persistence' exception
     */
    public PersistenceException(String value) {
        super("Persistence exception: " + value);
    }

}
