package nl.tudelft.jpacman;

/**
 * Exception that is thrown when JPacman cannot be properly loaded
 * from its resources.
 *
 * @author Arie van Deursen, 2014
 *
 */
public class PacmanConfigurationException extends RuntimeException {

    /**
     * A configuration exception with a direct message.
     *
     * @param message The exception message.
     */
    public PacmanConfigurationException(String message) {
        super(message);
    }

    /**
     * A configuration exception with a root cause and additional explanation.
     *
     * @param message The explanation.
     * @param cause The root cause.
     */
    public PacmanConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
