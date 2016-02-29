package nl.tudelft.jpacman.util;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Angeall on 27/02/2016.
 */
public class DoubleLinkedListWithWindow<E> extends DoubleLinkedList<E> {
    protected Node<E> windowHead;
    protected Node<E> windowTail;

    public DoubleLinkedListWithWindow(List<E> list) {
        super(list);
        this.windowHead = this.head;
        this.windowTail = this.tail;
    }

    public DoubleLinkedListWithWindow() {
        super();
    }

    public DoubleLinkedListWithWindow(Node<E> head, Node<E> tail) {
        super(head, tail);
        this.windowHead = this.head;
        this.windowTail = this.tail;
    }

    @Override
    public E removeFirst() {
        Node<E> temp = null;
        boolean headsEqual = this.windowHead == this.head;
        if(this.head != null) temp = this.head.getNext();
        E data = super.removeFirst();
        if (headsEqual) this.windowHead = temp;
        return data;
    }

    @Override
    public E removeLast() {
        Node<E> temp = null;
        boolean tailsEqual = this.windowTail == this.tail;
        if (this.tail != null) temp = this.tail.getPrevious();
        E data =  super.removeLast();
        if (tailsEqual) this.windowTail = temp;
        return data;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if(c.isEmpty()) return false;
        try {
            c.forEach(this::addLast);
            return true;
        }catch(Exception exc){
            return false;
        }
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.windowHead = null;
        this.windowTail = null;
        this.size = 0;
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

    public ArrayList<Node<E>> setWindow(int windowHeadIndex, int windowTailIndex){
        assert windowHeadIndex <= windowTailIndex;
        assert windowHeadIndex >= 0 && windowTailIndex >= 0;
        if(windowHeadIndex >= size || windowTailIndex >= size) throw new IndexOutOfBoundsException();
        this.windowHead = getNodeAt(windowHeadIndex);
        this.windowTail = getNodeAt(windowTailIndex);
        return getWindow();
    }

    public ArrayList<Node<E>> getWindow(){
        ArrayList<Node<E>> list = new ArrayList<>();
        list.add(this.windowHead);
        list.add(this.windowTail);
        return list;
    }

    public Node<E> getWindowHead() {
        return windowHead;
    }

    public Node<E> getWindowTail() {
        return windowTail;
    }

    public ArrayList<Node<E>> slideWindowRight(){
        if(windowHead != null && windowHead.hasNext()) this.windowHead = this.windowHead.getNext();
        if(windowTail != null && windowTail.hasNext()) this.windowTail = this.windowTail.getNext();
        return getWindow();
    }

    public ArrayList<Node<E>> slideWindowLeft(){
        if(windowHead != null && windowHead.hasPrevious()) this.windowHead = this.windowHead.getPrevious();
        if(windowTail != null && windowTail.hasPrevious()) this.windowTail = this.windowTail.getPrevious();
        return getWindow();
    }
}
