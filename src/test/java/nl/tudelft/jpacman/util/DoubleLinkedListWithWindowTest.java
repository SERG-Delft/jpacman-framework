package nl.tudelft.jpacman.util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by Angeall on 28/02/2016.
 */
public class DoubleLinkedListWithWindowTest {
    private DoubleLinkedListWithWindow<String> linkedList = new DoubleLinkedListWithWindow<>();

    @Before
    public void setUp(){
        linkedList.clear();
    }

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
    }

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

    @Test
    public void windowSlideTestLeft(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(1, 2);
        linkedList.slideWindowLeft(); // slide the windows to the right
        assertEquals("a", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "a"
        assertEquals("b", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "b"
        assertEquals(0, linkedList.getWindowHeadIndex());
        assertEquals(1, linkedList.getWindowTailIndex());
    }

    @Test
    public void windowSlideTestRightWithLossOfData(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(2, 3);
        linkedList.slideWindowRight(); // slide the windows to the right
        assertEquals("d", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "d"
        assertEquals("d", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "d"
        assertEquals(linkedList.getWindowHeadIndex(), linkedList.getWindowTailIndex());
        assertEquals(3, linkedList.getWindowHeadIndex());
    }

    @Test
    public void windowSlideTestLeftWithLossOfData(){
        linkedList.add("a");
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.setWindow(0, 1);
        linkedList.slideWindowLeft(); // slide the windows to the right
        assertEquals("a", linkedList.getWindow().get(0).getData()); // assert the head of the window contains "a"
        assertEquals("a", linkedList.getWindow().get(1).getData()); // assert the tail of the window contains "a"
        assertEquals(linkedList.getWindowHeadIndex(), linkedList.getWindowTailIndex());
        assertEquals(0, linkedList.getWindowHeadIndex());
    }

    @Test
    public void windowSlideTestRightOutLimit(){
        linkedList.add("d");
        linkedList.setWindow(0, 0);
        linkedList.slideWindowRight(); // slide the windows to the right
        assertEquals("d", linkedList.getWindow().get(0).getData()); // assert the head of the window till contains "d"
        assertEquals("d", linkedList.getWindow().get(1).getData()); // assert the tail of the window still contains "d"
        assertEquals(linkedList.getWindowHeadIndex(), linkedList.getWindowTailIndex());
        assertEquals(0, linkedList.getWindowHeadIndex());
    }

    @Test
    public void windowSlideTestLeftOutLimit(){
        linkedList.add("a");
        linkedList.setWindow(0, 0);
        linkedList.slideWindowLeft(); // slide the windows to the right
        assertEquals("a", linkedList.getWindow().get(0).getData()); // assert the head of the window still contains "a"
        assertEquals("a", linkedList.getWindow().get(1).getData()); // assert the tail of the window stil contains "a"
        assertEquals(linkedList.getWindowHeadIndex(), linkedList.getWindowTailIndex());
        assertEquals(0, linkedList.getWindowHeadIndex());
    }

}