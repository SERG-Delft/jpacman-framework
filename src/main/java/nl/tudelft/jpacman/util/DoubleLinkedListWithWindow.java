package nl.tudelft.jpacman.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angeall on 27/02/2016.
 */
public class DoubleLinkedListWithWindow<E> extends DoubleLinkedList<E> {
    protected Node<E> windowHead;
    protected int windowHeadIndex;
    protected Node<E> windowTail;
    protected int windowTailIndex;

    public DoubleLinkedListWithWindow(List<E> list) {
        super(list);
        initWindow();
    }

    public DoubleLinkedListWithWindow(List<E> list, int windowHeadIndex, int windowTailIndex) {
        super(list);
        setWindow(windowHeadIndex, windowTailIndex);
    }

    public DoubleLinkedListWithWindow() {
        super();
    }

    public DoubleLinkedListWithWindow(Node<E> head, Node<E> tail) {
        super(head, tail);
        initWindow();
    }

    /**
     * Initialize the window head and tail and their index
     */
    private void initWindow() {
        if(!isEmpty()){
            windowHeadIndex = 0;
            windowTailIndex = size-1;
        }
        else{
            windowHeadIndex = -1;
            windowTailIndex = -1;
        }
        this.windowHead = this.head;
        this.windowTail = this.tail;
    }

    @Override
    public void addFirst(E element){
        super.addFirst(element);
        this.windowHeadIndex++;
        this.windowTailIndex++;
    }

    @Override
    public E removeFirst() {
        Node<E> temp = null;
        boolean headsEqual = this.windowHead == this.head;
        if(this.head != null) temp = this.head.getNext();
        E data = super.removeFirst();
        if (headsEqual) {
            this.windowHead = temp;
            this.windowTailIndex -= 1;
        }
        return data;
    }

    @Override
    public E removeLast() {
        Node<E> temp = null;
        boolean tailsEqual = this.windowTail == this.tail;
        if (this.tail != null) temp = this.tail.getPrevious();
        E data =  super.removeLast();
        if (tailsEqual) {
            this.windowTail = temp;
            this.windowTailIndex -=1;
        }
        return data;
    }

    @Override
    public void clear() {
        super.clear();
        initWindow();
    }

    @Override
    protected void deleteNode(Node<E> cursor) {
        super.deleteNode(cursor);
        if(this.windowHead == this.windowTail && this.windowHead == cursor){
            this.windowHead = null;
            this.windowTail = null;
        }
        else if(size != 0) {
            if (size != 1) {
                if (cursor == this.windowHead) {
                    this.windowHead = cursor.getNext();
                }
                else if(cursor == this.windowTail){
                    this.windowTail = cursor.getPrevious();
                }
            }
            else{
                this.windowHead = this.head;
                this.windowTail = this.tail;
            }
        }
    }

    /**
     * Set the window inside the double linked list.
     * The window is a double linked list inside the double linked list.
     * @param windowHeadIndex
     *                          The index of the head of the window
     * @param windowTailIndex
     *                          The index of the tail of the window
     * @return An ArrayList containing the head and the tail (in this order) of the window.
     */
    public ArrayList<Node<E>> setWindow(int windowHeadIndex, int windowTailIndex){
        assert windowHeadIndex <= windowTailIndex;
        assert windowHeadIndex >= 0 && windowTailIndex >= 0;
        if(windowHeadIndex >= size || windowTailIndex >= size) throw new IndexOutOfBoundsException();
        this.windowHead = getNodeAt(windowHeadIndex);
        this.windowHeadIndex = windowHeadIndex;
        this.windowTail = getNodeAt(windowTailIndex);
        this.windowTailIndex = windowTailIndex;
        return getWindow();
    }

    /**
     * Get the head and the tail of the window inside the double linked list
     * @return An ArrayList containing the head and the tail (in this order) of the window.
     */
    public ArrayList<Node<E>> getWindow(){
        ArrayList<Node<E>> list = new ArrayList<>();
        list.add(this.windowHead);
        list.add(this.windowTail);
        return list;
    }

    /**
     * Slide the window to the right. If the tail is already the end of the double linked list,
     * the window remains unchanged
     * @return An ArrayList containing the head and the tail (in this order) of the slided window.
     */
    public ArrayList<Node<E>> slideWindowRight(){
        if(this.windowTailIndex != size-1) {
            if (windowHead != null && windowHead.hasNext()) {
                this.windowHead = this.windowHead.getNext();
                this.windowHeadIndex++;
            }
            if (windowTail != null) {
                this.windowTailIndex++;
                this.windowTail = this.windowTail.getNext();
            }
        }
        return getWindow();
    }

    /**
     * Slide the window to the left. If the head is already the start of the double linked list,
     * the window remains unchanged
     * @return An ArrayList containing the head and the tail (in this order) of the slided window.
     */
    public ArrayList<Node<E>> slideWindowLeft(){
        if(this.windowHeadIndex != 0) {
            if(windowHead != null){
                this.windowHeadIndex--;
                this.windowHead = this.windowHead.getPrevious();
            }
            if(windowTail != null && windowTail.hasPrevious()){
                this.windowTail = this.windowTail.getPrevious();
                this.windowTailIndex--;
            }
        }
        return getWindow();
    }


    /**
     * Get the head of the window inside the double linked list
     * @return The head of the window
     */
    public Node<E> getWindowHead() {
        return windowHead;
    }

    /**
     * Get the tail of the window inside the double linked list
     * @return The tail of the window
     */
    public Node<E> getWindowTail() {
        return windowTail;
    }

    /**
     * Get the current index of the window head node
     * @return The current index of the window head node
     */
    public int getWindowHeadIndex() {
        return windowHeadIndex;
    }

    /**
     * Get the current index of the window tail node
     * @return The current index of the window tail node
     */
    public int getWindowTailIndex() {
        return windowTailIndex;
    }

    public E getFromWindow(int x) {
        if(x > getWindowSize()-1) throw new IndexOutOfBoundsException();
       Node<E> explorerNode = windowHead;
        int i = 0;
        while(i < x){
            explorerNode = explorerNode.getNext();
            i++;
        }
        return explorerNode.getData();
    }

    public int getWindowSize(){
        return windowTailIndex - windowHeadIndex + 1;
    }
}
