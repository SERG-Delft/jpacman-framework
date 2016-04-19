package nl.tudelft.jpacman.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A marker annotation for the auto-grader to identify a boundary testing solution.
 *
 * A Students Solution class for the boundary testing exercise should have the following:
 *
 * @ParameterizedAssignment
 * @RunWith(Parameterized.class)
 * public class WithinBordersTest {
 *     //tests
 * }
 */
@Retention(RetentionPolicy.CLASS)
public @interface ParameterizedAssignment {
}
