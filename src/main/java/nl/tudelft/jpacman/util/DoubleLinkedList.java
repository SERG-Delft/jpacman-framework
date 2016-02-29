package nl.tudelft.jpacman.util;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Angeall on 26/02/2016.
 * Class defining a double linked list composed of {@link Node} containing data.
 * The list is defined by a head and a tail node. Every other node in the list can be accessed through the
 * head or the tail by exploring their next or previous attributes. \n\n
 *
 * The node linked "previously" to the head is null.
 * The node linked "next" to the tail is null\n\n
 *
 * Each node has a next and a previous node linked to it (that can be null)
 * and each node can contain a Data of the generic type E.\n\n
 *
 * The implemented methods mostly retrieve data, hence, type E.
 * But some method allow to retrieve the nodes that define this list : see {getNodeAt(int index)},
 * {getHead()} and {getTail()}
 */
@SuppressWarnings("NullableProblems")
public class DoubleLinkedList<E> implements Iterable<E>, Collection<E>, Deque<E>, Queue<E> {
    protected static final String EMPTY_MESSAGE = "The list is empty";
    protected int size;
    protected Node<E> head;
    protected Node<E> tail;
    protected Integer hash;

    public DoubleLinkedList(List<E> list) {
        super();
        addAll(list);
    }

    public DoubleLinkedList() {
        super();
    }

    /**
     * Create a double linked list with the head and the tail already set (it is expected that the links
     * between head and tail are well defined)
     * @param head
     *              The head of the chain (must be linked as the x th previous of tail)
     * @param tail
     *              The tail of the chain (must be linked as the x th next of head)
     */
    public DoubleLinkedList(Node<E> head, Node<E> tail){
        if((head == null || tail == null) && head != tail) {
            throw new IllegalArgumentException("Head and Tail are inconsistent");
        }

        this.head = head;
        this.tail = tail;
        this.size = 0;
        Node<E> explorer = head;
        boolean tailReached = false;
        while(explorer!=null && !tailReached){
            if(explorer == tail) tailReached = true;
            explorer = explorer.getNext();
            size++;
        }
        if(!tailReached) throw new IllegalArgumentException("Head and Tail are inconsistent");
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for(E e : this){
            if(o == null) {
                if(e == null) return true;
            }
            else if(o.equals(e)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> lastNodeReturned;
            Node<E> cursor = head;
            @Override
            public boolean hasNext() {
                return size != 0 && cursor != null;
            }

            @Override
            public E next() {
                if(cursor==null || size == 0) {
                    throw new NoSuchElementException();
                }
                E data = cursor.getData();
                lastNodeReturned = cursor;
                cursor = cursor.getNext();
                return data;
            }

            @Override
            public void remove(){
                if(lastNodeReturned == null || size == 0){
                    throw new IllegalStateException();
                }
                deleteNode(lastNodeReturned);
                lastNodeReturned = null;
                }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            Node<E> cursor = tail;
            @Override
            public boolean hasNext() {
                return size != 0 && cursor != null;
            }

            @Override
            public E next() {
                if(cursor==null || size == 0) {
                    throw new NoSuchElementException();
                }
                E data = cursor.getData();
                cursor = cursor.getPrevious();
                return data;
            }
        };
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for(E data : this){
            array[i] = data;
            i++;
        }
        return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) throws ClassCastException {
        int size = size();
        T[] array = a.length >= size
                ? a
                : (T[]) Array.newInstance(a.getClass().getComponentType(), size);
        int i = 0;
        for (E data : this) {
            array[i] = (T) data;
            i++;
        }
        if(i < array.length){
            array[i] = null;
        }
        return array;
    }

    @Override
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e, this.head, null);
        if(this.head != null) this.head.setPrevious(newNode);
        this.head = newNode;
        if(size == 0) this.tail = this.head;
        this.size++;
    }

    @Override
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e, null, this.tail);
        if(this.tail != null) this.tail.setNext(newNode);
        this.tail = newNode;
        if(size == 0) this.head = this.tail;
        this.size++;
        }

    @Override
    public boolean offerFirst(E e) {
        boolean succeeded = true;
        try{
            addFirst(e);
        } catch (Exception exc) {
            succeeded = false;
        }
        return succeeded;
    }

    @Override
    public boolean offerLast(E e) {
        boolean succeeded = true;
        try{
            addLast(e);
        } catch (Exception exc) {
            succeeded = false;
        }
        return succeeded;
    }

    @Override
    public E removeFirst() {
        if(isEmpty()) throw new NoSuchElementException(EMPTY_MESSAGE);
        E data = getFirst();
        this.size--;
        if(size == 0){
            clear();
            return data;
        }
        this.head = this.head.getNext();
        this.head.setPrevious(null);
        return data;
    }

    @Override
    public E removeLast() {
        if(isEmpty()) throw new NoSuchElementException(EMPTY_MESSAGE);
        E data = getLast();
        this.size--;
        if(size == 0){
            clear();
            return data;
        }
        this.tail = this.tail.getPrevious();
        this.tail.setNext(null);
        return data;
    }

    @Override
    public E pollFirst() {
        try{
            return removeFirst();
        } catch(NoSuchElementException exc){
            return null;
        }
    }

    @Override
    public E pollLast() {
        try{
            return removeLast();
        } catch(NoSuchElementException exc){
            return null;
        }
    }

    @Override
    public E getFirst() {
        if(isEmpty()) throw new NoSuchElementException();
        return this.head.getData();
    }

    @Override
    public E getLast() {
        if(isEmpty()) throw new NoSuchElementException();
        return this.tail.getData();
    }

    @Override
    public E peekFirst() {
        try{
            return getFirst();
        } catch(NoSuchElementException exc){
            return null;
        }
    }

    @Override
    public E peekLast() {
        try{
            return getLast();
        } catch(NoSuchElementException exc){
            return null;
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        return remove(o);
    }

    @Override

    public boolean removeLastOccurrence(Object o) {
        if(isEmpty()) return false;
        else if(o == tail.getData()){
            removeLast();
            return false;
        }
        Node<E> explorer = this.tail;
        while(explorer.hasPrevious()){
            explorer = explorer.getPrevious();
            if(o.equals(explorer.getData())){
                deleteNode(explorer);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(E e) {
        if(contains(e)){
            return false;
        }
        else{
            addLast(e);
            return true;
        }
    }

    @Override
    public boolean offer(E e) {
        try{
            return add(e);
        } catch(Exception exc){
            return false;
        }
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        if(isEmpty()) return false;
        else if((o == null && this.head.getData() == null) || (o != null && o.equals(head.getData()))){
            removeFirst();
            return true;
        }
        Node<E> explorer = this.head;
        while(explorer.hasNext()){
            explorer = explorer.getNext();
            if(o == null){
                if(explorer.getData() == null){
                    deleteNode(explorer);
                    return true;
                }
            }
            else if(o.equals(explorer.getData())){
                deleteNode(explorer);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        HashMap<Object, Boolean> contained = new HashMap<>();
        for(Object o : c){
            contained.put(o, false);
        }
        this.stream().filter(data -> contained.keySet().contains(data)).forEach(data -> contained.put(data, true));
        return !contained.values().contains(false);
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
    public boolean removeAll(Collection<?> c) {
        if(size == 0) return false;
        Node<E> explorer = this.head;
        boolean modified = false;
        while(explorer != null){
            E data = explorer.getData();
            if(c.contains(data)){
                deleteNode(explorer);
                modified = true;
            }
            explorer = explorer.getNext();
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if(size == 0) return false;
        Node<E> explorer = this.head;
        boolean modified = false;
        while(explorer != null){
            E data = explorer.getData();
            if(!c.contains(data)){
                deleteNode(explorer);
                modified = true;
            }
            explorer = explorer.getNext();
        }
        return modified;
    }

    /**
     * Get the data at position index
     * @param index
     *                  The index of the object to retrieve
     * @return The data of generic type E located at the given index
     */
    public E get(int index){
        return getNodeAt(index).getData();
    }

    @Override
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }


    @Override
    public String toString(){
        if(isEmpty()) {
            return "[]";
        }
        String str = "";
        for(E data : this){
            if(data == null) str += "[null]";
            else str += "[" + data.toString() + "]";
        }

        return str;
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof DoubleLinkedList){
            HashMap<Object, Boolean> container = new HashMap<>();
            for(Object object : (DoubleLinkedList) o){
                container.put(object, false);
            }
            for(E element : this){
                if(!container.containsKey(element)){
                    return false;
                }
                else{
                    container.put(element, true);
                }
            }
            return !container.values().contains(false);
        }
        else{
            ArrayList<E> arrayList = this.stream().collect(Collectors.toCollection(ArrayList::new));
            return arrayList.equals(o);
        }
    }

    @Override
    public int hashCode(){
        if (hash == null) {
            ArrayList<E> arrayList = this.stream().collect(Collectors.toCollection(ArrayList::new));
            hash = arrayList.hashCode();
        }
        return hash;
    }

    /**
     * Deletes a node inside the double linked list.
     * Does not check that the node was indeed in the list.
     * @param cursor
     *                  The node to delete inside the list
     */
    protected void deleteNode(Node<E> cursor) {
        if(cursor==null) {
            throw new IllegalStateException();
        }
        else{
            size --;
            if (cursor.hasNext()){
                cursor.getNext().setPrevious(cursor.getPrevious());
            }
            if (cursor.hasPrevious()) cursor.getPrevious().setNext(cursor.getNext());
            if(size == 0){
                clear();
            }
            else if (size == 1){
                if(cursor.hasNext()) {
                    this.head = cursor.getNext();
                    this.tail = cursor.getNext();
                }
                else{
                    this.head = cursor.getPrevious();
                    this.tail = cursor.getPrevious();
                }
            }
            if(cursor == this.head){
                this.head = cursor.getNext();
            }
            if(cursor == this.tail) {
                this.tail = cursor.getPrevious();
            }
        }
    }


    /**
     * Get the node located at the given index. See the {@link Node} class for more information
     * @param index
     *                  The index of the node to retrieve
     * @return The node located at the given index
     */
    public Node<E> getNodeAt(int index) {
        Node<E> currentNode;
        if(index > size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index > size/2) {
            currentNode = this.head;
            for (int i = 1; i <= index; i++) {
                currentNode = currentNode.getNext();
            }
        }
        else{
            currentNode = this.tail;
            for (int i = size-1; i > index; i--) {
                currentNode = currentNode.getPrevious();
            }
        }
        return currentNode;
    }

    /**
     * Get the tail node of the double linked list
     * @return The tail node of the linked list
     */
    public Node<E> getTail() {
        return tail;
    }

    /**
     * Get the head node of the double linked list
     * @return The head node of the linked list
     */
    public Node<E> getHead() {
        return head;
    }
}
