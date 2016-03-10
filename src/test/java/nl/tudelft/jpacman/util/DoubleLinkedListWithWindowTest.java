package nl.tudelft.jpacman.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by Angeall on 28/02/2016.
 */
public class DoubleLinkedListWithWindowTest {
    private DoubleLinkedListWithWindow<String> linkedList = new DoubleLinkedListWithWindow<>();

    /**
     * Clear the list before each test
     */
    @Before
    public void setUp(){
        linkedList.clear();
    }

    /**
     * Tests that the window is well set
     */
    @Test
    public void windowTest(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        int headIndex = 1;
        int tailIndex = 3;
        ArrayList<Node<String>> window = linkedList.setWindow(headIndex, tailIndex);
        assertEquals("b", window.get(0).getData()); // assert the data of the window head is "b"
        assertEquals(window.get(0), linkedList.getWindow().get(0)); // assert the window is well set
        assertEquals(headIndex,  linkedList.getWindowHeadIndex());
        assertEquals("d", window.get(1).getData()); // assert the data of the window tail is "d"
        assertEquals(window.get(1), linkedList.getWindow().get(1)); // assert the window is well set
        assertEquals(tailIndex,  linkedList.getWindowTailIndex());
    }

    /**
     * Tests if the window is well updated when removing the last element (inside the window)
     */
    @Test
    public void windowTestWithRemoveLastInWindow(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(1, 3);
        linkedList.removeLast(); // remove the "d" => the window will be "b" <=> "c"
        assertEquals("b", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "b"
        assertEquals("c", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "c"
    }

    /**
     * Tests if the window remains the same when removing the last element of the list (outside the window)
     */
    @Test
    public void windowTestWithRemoveLastOutWindow(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(0, 2);
        linkedList.removeLast(); // remove the "d" => the window remains unchanged
        assertEquals("a", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "b"
        assertEquals("c", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "d"
    }

    /**
     * Tests if the window is well updated when removing the first element (inside the window)
     */
    @Test
    public void windowTestWithRemoveFirstInWindow(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(0, 2);
        linkedList.removeFirst(); // remove the "a" => the window will be "b" <=> "c"
        assertEquals("b", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "b"
        assertEquals("c", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "c"
    }

    /**
     * Tests if the window remains the same when removing the first element of the list (outside the window)
     */
    @Test
    public void windowTestWithRemoveFirstOutWindow(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(1, 3);
        linkedList.removeFirst(); // remove the "a" => the window remains unchanged
        assertEquals("b", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "b"
        assertEquals("d", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "d"
    }

    /**
     * Tests if the window is well updated when removing inside the window
     */
    @Test
    public void windowTestWithRemoveInsideWindow(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(1, 3);
        linkedList.remove("c"); // remove the "a" => the window remains unchanged
        assertEquals("b", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "b"
        assertEquals("d", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "d"
        assertEquals("d", linkedList.getWindow().get(0).getNext().getData()); // assert that "c" is well deleted
        assertEquals("b", linkedList.getWindow().get(1).getPrevious().getData()); // assert that "c" is well deleted
        assertEquals(1, linkedList.getWindowHeadIndex());
        assertEquals(2, linkedList.getWindowTailIndex());
        assertEquals(2, linkedList.getWindowSize());
    }

    /**
     * Tests if the window can properly slide to the right
     */
    @Test
    public void windowSlideTestRight(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(0, 1);
        linkedList.slideWindowRight(); // slide the windows to the right
        assertEquals("b", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "b"
        assertEquals("c", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "c"
        assertEquals(1, linkedList.getWindowHeadIndex());
        assertEquals(2, linkedList.getWindowTailIndex());
    }

    /**
     * Tests if the window can properly slide to the left
     */
    @Test
    public void windowSlideTestLeft(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(1, 2);
        linkedList.slideWindowLeft(); // slide the windows to the left
        assertEquals("a", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "a"
        assertEquals("b", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "b"
        assertEquals(0, linkedList.getWindowHeadIndex());
        assertEquals(1, linkedList.getWindowTailIndex());
    }

    /**
     * Tests if the window remains unchanged when sliding to the right
     *      as the window tail is the tail of the linked list
     */
    @Test
    public void windowSlideTestRightRemainsUnchanged(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(2, 3);
        linkedList.slideWindowRight(); // slide the windows to the right
        assertEquals("c", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "c"
        assertEquals("d", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "d"
        assertEquals(2, linkedList.getWindowHeadIndex());
        assertEquals(3, linkedList.getWindowTailIndex());
    }

    /**
     * Tests if the window remains unchanged when sliding to the left
     *      as the window head is the head of the linked list
     */
    @Test
    public void windowSlideTestLeftRemainsUnchanged(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(0, 1);
        linkedList.slideWindowLeft(); // slide the windows to the left
        assertEquals("a", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "a"
        assertEquals("b", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "b"
        assertEquals(0, linkedList.getWindowHeadIndex());
        assertEquals(1, linkedList.getWindowTailIndex());
    }

    /**
     * Tests if the window remains unchanged when sliding to the right
     *      in a list of size 1
     */
    @Test
    public void windowSlideTestRightOutLimit(){
        linkedList.add("d");
        linkedList.setWindow(0, 0);
        linkedList.slideWindowRight(); // slide the windows to the right
        assertEquals("d", linkedList.getWindow().get(0).getData()); // assert the head of the window till contains "d"
        assertEquals("d", linkedList.getWindow().get(1).getData()); // assert the tail of the window still contains "d"
        assertEquals(linkedList.getWindowHeadIndex(), linkedList.getWindowTailIndex());
        assertEquals(0, linkedList.getWindowHeadIndex());
        assertEquals(1, linkedList.getWindowSize());
    }

    /**
    * Tests if the window remains unchanged when sliding to the left
    *      in a list of size 1
     */
    @Test
    public void windowSlideTestLeftOutLimit(){
        linkedList.add("a");
        linkedList.setWindow(0, 0);
        linkedList.slideWindowLeft(); // slide the windows to the left
        assertEquals("a", linkedList.getWindow().get(0).getData()); // assert the head of the window still contains "a"
        assertEquals("a", linkedList.getWindow().get(1).getData()); // assert the tail of the window stil contains "a"
        assertEquals(linkedList.getWindowHeadIndex(), linkedList.getWindowTailIndex());
        assertEquals(0, linkedList.getWindowHeadIndex());
    }

    /**
     * Tests if the array constructor instantiate a proper list and window inside it
     */
    @Test
    public void testArrayConstructorWithWindow(){
        String[] strings = {"a", "b", "c", "d", "e", "f"};
        DoubleLinkedListWithWindow<String> linkedList = new DoubleLinkedListWithWindow<>(Arrays.asList(strings),
                                                                                         0, strings.length-1);
        assertEquals("f", linkedList.getWindowTail().getData());

        assertEquals("a", linkedList.getWindowHead().getData());
        assertEquals(6, linkedList.getWindowSize());
    }
}