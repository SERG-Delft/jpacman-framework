package nl.tudelft.jpacman.util;

import com.google.common.collect.testing.*;
import com.google.common.collect.testing.features.CollectionFeature;
import com.google.common.collect.testing.features.CollectionSize;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.junit.runners.Suite;

import java.util.Arrays;
import java.util.Queue;

/**
 * Your test class must be annotated with {@link RunWith} to specify that it's a
 * test suite and not a single test.
 */
@RunWith(value=AllTests.class)
/**
 * We need to use static inner classes as JUnit only allows for empty "holder"
 * suite classes.
 */
@Suite.SuiteClasses(value={DoubleLinkedListTest.class,
                           DoubleLinkedListTest.OtherTests.class})


/**
 * Created by Angeall on 27/02/2016.
 */
public class DoubleLinkedListTest {
        public static class OtherTests extends TestCase {
            public void testHeadTail() {
                DoubleLinkedList<String> linkedList = new DoubleLinkedList<>();
                linkedList.add("a");
                assertEquals("a", linkedList.getHead().getData());
                assertEquals("a", linkedList.getTail().getData());
            }

            public void testHeadTailWithRemoveFirst() {
                DoubleLinkedList<String> linkedList = new DoubleLinkedList<>();
                linkedList.add("a");
                linkedList.add("b");
                linkedList.add("c");
                linkedList.removeFirst();
                assertEquals("b", linkedList.getHead().getData());
                assertEquals("c", linkedList.getTail().getData());
            }

            public void testHeadTailWithRemoveLast() {
                DoubleLinkedList<String> linkedList = new DoubleLinkedList<>();
                linkedList.add("a");
                linkedList.add("b");
                linkedList.add("c");
                linkedList.removeLast();
                assertEquals("a", linkedList.getHead().getData());
                assertEquals("b", linkedList.getTail().getData());
            }

            public void testGetNextGetPreviousWithRemove(){
                DoubleLinkedList<String> linkedList = new DoubleLinkedList<>();
                linkedList.add("a");
                linkedList.add("b");
                linkedList.add("c");
                linkedList.remove("b");
                assertEquals("c", linkedList.getHead().getNext().getData());
                assertEquals("a", linkedList.getTail().getPrevious().getData());
            }

            public void testHeadTailWithRemoveOfHead(){
                DoubleLinkedList<String> linkedList = new DoubleLinkedList<>();
                linkedList.add("a");
                linkedList.add("b");
                linkedList.add("c");
                linkedList.remove("a");
                assertEquals("b", linkedList.getHead().getData());
                assertEquals(null, linkedList.getHead().getPrevious());
            }

            public void testHeadTailWithRemoveOfTail(){
                DoubleLinkedList<String> linkedList = new DoubleLinkedList<>();
                linkedList.add("a");
                linkedList.add("b");
                linkedList.add("c");
                linkedList.remove("c");
                assertEquals("b", linkedList.getTail().getData());
                assertEquals(null, linkedList.getTail().getNext());
            }

        }
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
            TestSuite testSuite = QueueTestSuiteBuilder
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
                    .named("DoubleLinkedList tests")
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
            testSuite.addTestSuite(DoubleLinkedListTest.OtherTests.class);
            return testSuite;
        }
}