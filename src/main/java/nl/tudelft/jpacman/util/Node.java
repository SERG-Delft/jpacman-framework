package nl.tudelft.jpacman.util;

/**
 * Created by Angeall on 26/02/2016.
 * Class Node to be used with {@link DoubleLinkedList}.
 * It can contain data, a next Node and a previous node.
 */
public class Node<E> {
    private E data;
    private Node<E> next;
    private Node<E> previous;

    /**
     * Instantiate a lone Node. Its next and previous are null
     * @param data
     *              The data to stock inside the Node
     */
    public Node(E data){
        this.data = data;
    }

    /**
     * Instantiate a Node.
     * @param data
     *              The data to stock inside the Node
     * @param next
     *              The next Node that is linked with this one
     * @param previous
     *              The previous Node that is linked with this one
     */
    public Node(E data, Node<E> next, Node<E> previous){
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    /**
     * Get the data stocked into the Node.
     * @return The data stocked inside the Node
     */
    public E getData() {
        return data;
    }

    /**
     * Set the data to be stocked inside the Node
     * @param data
     *              The data to stock inside the Node
     */
    public void setData(E data) {
        this.data = data;
    }

    /**
     * Get the next Node.
     * @return The next Node that is linked with this one
     */
    public Node<E> getNext() {
        return next;
    }

    /**
     * Returns if this node is linked to a next Node
     * @return <code>true</code> if this node has a next Node
     */
    public boolean hasNext() {
        return next != null;
    }

    /**
     * Set the Node next to this one
     * @param next
     *              The next Node that will be linked with this one
     */
    public void setNext(Node<E> next) {
        this.next = next;
    }

    /**
     * Get the previous Node.
     * @return The previous Node that is linked with this one
     */
    public Node<E> getPrevious() {
        return previous;
    }

    /**
     * Returns if this node is linked to a previous Node
     * @return <code>true</code> if this node has a previous Node
     */
    public boolean hasPrevious(){
        return previous != null;
    }

    /**
     * Set the previous Node to be linked to this one
     * @param previous
     *              The previous Node that will be linked with this one
     */
    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }
}
