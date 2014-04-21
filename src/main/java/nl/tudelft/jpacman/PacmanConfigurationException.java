package nl.tudelft.jpacman;

/**
 * Exception that is thrown when JPacman cannot be properly loaded
 * from its resources.
 * 
 * @author Arie van Deursen, 2014
 *
 */
public class PacmanConfigurationException extends RuntimeException {

	public PacmanConfigurationException(String message) {
		super(message);
	}

	public PacmanConfigurationException(Throwable cause) {
		super(cause);
	}

	public PacmanConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
