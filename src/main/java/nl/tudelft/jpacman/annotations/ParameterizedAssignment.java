package nl.tudelft.jpacman.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A marker annotation for the auto-grader to identify a boundary testing solution.
 *
 * A Students Solution class for the boundary testing exercise should have the following:
 *
 * <pre class="code"><code class="java">
 * &#064;ParameterizedAssignment
 * &#064;RunWith(Parameterized.class)
 * public class WithinBordersTest {
 *     //tests
 * }
 * </code></pre>
 */
@Retention(RetentionPolicy.CLASS)
public @interface ParameterizedAssignment {
}
