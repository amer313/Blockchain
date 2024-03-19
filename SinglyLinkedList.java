import java.util.Iterator;

import javax.management.RuntimeErrorException;

/**
    - Implements a singly linked list.
    - You must implement all the public methods in this template plus the method(s) required by the Iterable interface
    - Anything else you add must be private
    - Do not modify the provided signatures
*/
public class SinglyLinkedList<T extends Comparable<T>> implements Iterable<T>
{
    private Node<T> headNode;
    private Node<T> tail;
    private int size;

    public SinglyLinkedList()
    {
        headNode = new Node<>(null);
        tail = new Node<>(null);
        size = 0;
    }

    /**
        adds a value to the end of the list

        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public void add(T value)
    {
        Node<T> newNode = new Node<>(value);

        if(isEmpty()){
            headNode = newNode;
        }else{
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;
    }

    /**
        Inserts a value to the proper location in the list so that the list order is preserved (in descending order)

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public void insert(T newValue)
    {
    	if(isEmpty()){
    		add(newValue);
    		return;
    	}
    	
    	
    	Node<T> currentNode = headNode;
    	Node<T> newNode = new Node<T>(newValue);
    	Node<T> priorNode = null;
    	
    	while(currentNode != null && newValue.compareTo(currentNode.getValue()) < 0){
    		priorNode = currentNode;
    		currentNode = currentNode.getNext();
    	}
    	
    	if(currentNode == headNode){
    		newNode.setNext(currentNode);
    		headNode = newNode;
    		size++;
    		return;
    	}
    	if(currentNode == null) {
    		priorNode.setNext(newNode);
    		tail = newNode;
    		size++;
    		return;
    	}
    	
    	priorNode.setNext(newNode);
    	newNode.setNext(currentNode);
    	size++;
    	
    }

    /**
        Removes a single item from the list based on its index

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public T remove(int index)
    {
    	if(index <0 || index >= size) {
    		throw new RuntimeException("Index is invalid");
    	}
    	T returnValue = null;
    	int localIndex = 0;
    	Node<T> currentNode = headNode;
    	Node<T> priorNode = null;
    	
    	if(index == 0) {
    		returnValue = headNode.getValue();
    		headNode = headNode.getNext();
    		if(size == 1) {
    			tail = null;
    		}
    	}else {
			while(localIndex != index) {
				priorNode = currentNode;
				currentNode = currentNode.getNext();
				localIndex++;
			}
			returnValue = currentNode.getValue();
			priorNode.setNext(currentNode.getNext());
			if(index== size-1){
				tail = priorNode;
			}
    	}
    	
    	
    	size--;
        return returnValue;
    }

    /**
        Returns (without removing) a single item from the list based on its index

        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public T get(int index)
    {
    	if(index <0 || index >= size) {
    		throw new RuntimeException("Index is invalid");
    	}
    	
    	T returnValue = null;
    	int localIndex = 0;
    	for (T value : this) {
			if(localIndex == index) {
				returnValue = value;
				break;
			}
			localIndex++;
		}
        return returnValue;

    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int size()
    {
        return size;
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public boolean isEmpty()
    {
        return size == 0;
    }
    private class Node<R> {
        private R data;
        private Node<R> next;
        public Node(R value) {
            data = value;
            next = null;
        }
        public void setValue(R value) {
            data = value;
        }
        public R getValue() {
            return data;
        }
        public Node<R> getNext() {
            return next;
        }
        public void setNext(Node<R> p) {
            next = p;
        }
    }
    @Override
    public Iterator<T> iterator() {
        // An anonymous class
        Iterator<T> iterator = new Iterator<T>() {
            Node<T> currentNode = headNode;
            /**
             * implementing has next
             *
             * @return
             */
            @Override
            public boolean hasNext() {
                return currentNode != null;
            }
            /**
             * Implementing next by getting the next node
             *
             * @return
             */
            @Override
            public T next() {
                if (hasNext()) {
                    T returnValue = currentNode.getValue();
                    currentNode = currentNode.getNext();
                    return returnValue;
                }
                return null;
            }
            /**
             * remove method throws exception
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        /**
         * Returning iterator
         */
        return iterator;
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (T value : this) {
            str.append(value.toString());
            str.append("--˃");
        }
        str.append("\nHead: " + headNode.getValue());
        str.append("\nTail: " + tail.getValue());
        str.append("\nSize: " + size);
        return str.toString();
    }

    public static void main(String[] args) {
        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        SinglyLinkedList<Integer> lst2 = new SinglyLinkedList<>();
        
        lst2.add(1);
        lst2.remove(0);
        
        lst.insert(0);
        lst.insert(1);
        lst.insert(2);
        lst.insert(3);
        lst.insert(4);
        
        lst.remove(0);
        lst.remove(3);
        lst.remove(1);
        
        lst.get(1);
    }

}

