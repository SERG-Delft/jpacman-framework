package nl.tudelft.jpacman.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Entry point for running the the Cucumber tests in JUnit.
 *
 * @author Jan-Willem Gmelig Meyling
 */
@RunWith(Cucumber.class)
@CucumberOptions(
	plugin = {"pretty"}, 
	snippets = SnippetType.CAMELCASE, 
	features = "classpath:features")
public class CucumberTest {

	/**
	 * This class should be empty, step definitions should be in separate classes.
	 */

}
