package nl.tudelft.jpacman.util;

import com.google.common.collect.testing.*;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import com.google.common.collect.testing.features.ListFeature;
import junit.framework.TestSuite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

/**
 * Your test class must be annotated with {@link RunWith} to specify that it's a
 * test suite and not a single test.
 */
@RunWith(Suite.class)
/**
 * We need to use static inner classes as JUnit only allows for empty "holder"
 * suite classes.
 */
@Suite.SuiteClasses({
        DoubleLinkedListTest.GuavaTests.class,
        //DoubleLinkedListTest.AdditionalTests.class,
})


/**
 * Created by Angeall on 27/02/2016.
 */
public class DoubleLinkedListTest {
    public static class GuavaTests {

        /**
         * The method responsible for returning the {@link TestSuite} generated
         * by one of the {@link FeatureSpecificTestSuiteBuilder} classes.
         * This method must be public static method with no arguments named
         * "suite()".
         *
         * @return An instance of {@link TestSuite} for collection testing.
         */
        public static TestSuite suite() {
            /**
             * guava-testlib has a host of test suite builders available,
             * all descending from {@link FeatureSpecificTestSuiteBuilder}.
             * The
             * {@link FeatureSpecificTestSuiteBuilder#usingGenerator(Object)}
             * is the main entry point in that collections are tested by
             * implementing {@link TestCollectionGenerator} and providing an
             * instance of the interface to the test suite builder via the
             * #usingGenerator(Object) method. Most of suite builder classes
             * provide a convenience method such as
             * {@link ListTestSuiteBuilder.using()} that streamline the process
             * of creating a builder.
             *
             */
            return QueueTestSuiteBuilder
                    // The create method is called with an array of elements
                    // that should populate the collection.
                    .using(new TestStringQueueGenerator() {

                        @Override
                        protected Queue<String> create(String[] strings) {
                            return new DoubleLinkedList<>(Arrays.asList(strings));
                        }
                    })
                    // You can optionally give a name to your test suite. This
                    // name is used by JUnit and other tools during report
                    // generation.
                    .named("ArrayList tests")
                    // Guava has a host of "features" in the
                    // com.google.common.collect.testing.features package. Use
                    // them to specify how the collection should behave, and
                    // what operations are supported.
                    .withFeatures(
                            CollectionFeature.SUPPORTS_ADD,
                            CollectionFeature.SUPPORTS_REMOVE,
                            CollectionFeature.SUPPORTS_ITERATOR_REMOVE,
                            CollectionFeature.ALLOWS_NULL_VALUES,
                            CollectionFeature.GENERAL_PURPOSE,
                            CollectionSize.ANY
                    ).createTestSuite();
        }


    }

}