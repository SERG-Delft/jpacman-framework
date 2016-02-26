package nl.tudelft.jpacman.util;

import java.util.*;

/**
 * Created by Angeall on 26/02/2016.
 * Class defining a double linked list composed of {@link Node} containing data.
 */
public class DoubleLinkedList<E> implements Iterable<E>, Collection<E>, Deque<E>, List<E>, Queue<E> {
    private static final String EMPTY_MESSAGE = "The list is empty";
    private int size;
    private Node<E> head;
    private Node<E> current;
    private Node<E> tail;

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
        int index = indexOf(o);
        if(index == -1) return false;
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Node<E> iteratorCurrent = head;
            @Override
            public boolean hasNext() {
                return iteratorCurrent.hasNext();
            }

            @Override
            public E next() {
                E data = iteratorCurrent.getData();
                iteratorCurrent = iteratorCurrent.getNext();
                return data;
            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            Node<E> iteratorCurrent = tail;
            @Override
            public boolean hasNext() {
                return iteratorCurrent.hasPrevious();
            }

            @Override
            public E next() {
                E data = iteratorCurrent.getData();
                iteratorCurrent = iteratorCurrent.getPrevious();
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
    public <T> T[] toArray(T[] a) throws ClassCastException {
        assert a.length == size;
        int i = 0;
        for (E data : this) {
            a[i] = (T) data;
            i++;
        }
        return a;
    }

    @Override
    public void addFirst(E e) {
        this.head = new Node<>(e, this.head, null);
        this.size++;
    }

    @Override
    public void addLast(E e) {
        this.tail = new Node<>(e, null, this.tail);
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
        E data = getFirst();
        this.head = this.head.getNext();
        this.head.setPrevious(null);
        this.size--;
        return data;
    }

    @Override
    public E removeLast() {
        E data = getLast();
        this.tail = this.tail.getPrevious();
        this.tail.setNext(null);
        this.size--;
        return data;
    }

    @Override
    public E pollFirst() {
        try{
            return removeFirst();
        } catch(IllegalStateException exc){
            return null;
        }
    }

    @Override
    public E pollLast() {
        try{
            return removeLast();
        } catch(IllegalStateException exc){
            return null;
        }
    }

    @Override
    public E getFirst() {
        if(isEmpty()) throw new IllegalStateException(EMPTY_MESSAGE);
        return this.head.getData();
    }

    @Override
    public E getLast() {
        if(isEmpty()) throw new IllegalStateException(EMPTY_MESSAGE);
        return this.tail.getData();
    }

    @Override
    public E peekFirst() {
        try{
            return getFirst();
        } catch(IllegalStateException exc){
            return null;
        }
    }

    @Override
    public E peekLast() {
        try{
            return getLast();
        } catch(IllegalStateException exc){
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
                explorer.getNext().setPrevious(explorer.getPrevious());
                size--;
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
        else if(o.equals(head.getData())){
            removeFirst();
            return true;
        }
        Node<E> explorer = this.head;
        while(explorer.hasNext()){
            explorer = explorer.getNext();
            if(o.equals(explorer.getData())){
                explorer.getPrevious().setNext(explorer.getNext());
                size--;
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
        this.stream().filter(data -> contained.keySet().contains(data)).forEach(data -> {
            contained.put(data, true);
        });
        if(contained.values().contains(false)){
            return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        try {
            for (E e : c) {
                addLast(e);
            }
            return true;
        }catch(Exception exc){
            return false;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            addFirst(element);
            return;
        }
        if(index == size-1){
            addLast(element);
            return;
        }
        Node<E> explorer = this.head;
        int i = 0;
        while(explorer.hasNext()){
            explorer = explorer.getNext();
            i++;
            if(index == i){
                Node<E> newNode = new Node<E>(element, explorer, explorer.getPrevious());
                explorer.getPrevious().setNext(newNode);
                explorer.getNext().setPrevious(newNode);
                break;
            }
        }
    }

    @Override
    public E remove(int index) {
        if(index >= size || index < 0){
            throw new IndexOutOfBoundsException();
        }
        if(index == 0){
            return removeFirst();
        }
        if(index == size-1){
            return removeLast();
        }
        Node<E> explorer = this.head;
        int i = 0;
        while(explorer.hasNext()){
            explorer = explorer.getNext();
            i++;
            if(index == i){
                explorer.getPrevious().setNext(explorer.getNext());
                explorer.getNext().setPrevious(explorer.getPrevious());
                return explorer.getData();
            }
        }
        return null;
    }

    @Override
    public int indexOf(Object o) {
        int i = 0;
        boolean found = false;
        for(E data: this){
            if(o.equals(data)){
                found = true;
                break;
            }
            i++;
        }
        if(found){
            return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if(o.equals(this.tail.getData())) return this.size-1;
        int i = size-1;
        boolean found = false;
        Node<E> explorer = this.tail;
        while(tail.hasPrevious()){
            explorer = explorer.getPrevious();
            i--;
            if(o.equals(explorer.getData())){
                found = true;
                break;
            }
        }
        if(found){
            return i;
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListIterator<E>() {
            Node<E> iteratorCurrent = getNodeAt(index);
            boolean start = true;
            int i = 0;
            Boolean previous = null;

            @Override
            public boolean hasNext() {
                return iteratorCurrent.hasNext();
            }

            @Override
            public E next() {
                E data = iteratorCurrent.getData();
                iteratorCurrent = iteratorCurrent.getNext();
                i++;
                previous = false;
                return data;
            }

            @Override
            public boolean hasPrevious() {
                return iteratorCurrent.hasPrevious();
            }

            @Override
            public E previous() {
                E data = iteratorCurrent.getData();
                iteratorCurrent = iteratorCurrent.getPrevious();
                previous = true;
                i--;
                return data;
            }

            @Override
            public int nextIndex() {
                return i;
            }

            @Override
            public int previousIndex() {
                return i;
            }

            @Override
            public void remove() {
                if(previous != null){
                    if(previous){
                        iteratorCurrent.getNext().getNext().setPrevious(iteratorCurrent);
                        iteratorCurrent.setNext(iteratorCurrent.getNext().getNext());
                    }
                    else{
                        iteratorCurrent.getPrevious().getPrevious().setNext(iteratorCurrent);
                        iteratorCurrent.setPrevious(iteratorCurrent.getPrevious().getPrevious());
                    }
                }
            }

            @Override
            public void set(E e) {
                if(previous != null){
                    if(previous){
                        iteratorCurrent.getNext().setData(e);
                    }
                    else{
                        iteratorCurrent.getPrevious().setData(e);
                    }
                }
            }

            @Override
            public void add(E e) {
                Node<E> newNode = new Node<>(e, iteratorCurrent, iteratorCurrent.getPrevious());
                iteratorCurrent.getPrevious().setNext(iteratorCurrent);
                iteratorCurrent.setPrevious(iteratorCurrent.getPrevious());
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        List<E> list = new ArrayList<>();
        Node<E> explorer = getNodeAt(fromIndex);
        for(int i=fromIndex; i<toIndex; i++){
            list.add(explorer.getData());
        }
        return list;
    }

    public Node<E> getNodeAt(int index) {
        Node<E> currentNode = this.head;
        for(int i = 1; i<=index; i++){
            currentNode = currentNode.getNext();
        }
        return currentNode;
    }
}
