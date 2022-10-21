package persistence.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.exceptions.InvalidValueException;

/**
 * Validator for String-lengths.
 */
public class TextValidator {

    private static final Logger LOG = LoggerFactory.getLogger(TextValidator.class);

    public void validText(long length, String text, String name) throws InvalidValueException {
        if (text.length() > length) {
            LOG.error("Invalid value in {}", name);
            throw new InvalidValueException(name + ": length");
        }
    }
}
