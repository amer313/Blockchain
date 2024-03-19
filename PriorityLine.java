import java.util.Iterator;

/**
    - Implements a priority queue
    - You must implement all the public methods in this template plus the method(s) required by the Iterable interface
    - Anything else you add must be private
    - Do not modify the provided signatures
*/
public class PriorityLine<T extends Comparable<T>> implements Iterable<T>
{
	private SinglyLinkedList<T> line;
	
    public PriorityLine()
    {
    	line = new SinglyLinkedList<>();
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(N)
    */
    public void enqueue(T element)
    {
    	line.insert(element);
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public T dequeue()
    {
    	return line.remove(0);
    	
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public int size()
    {
    	return line.size();
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public boolean isEmpty()
    {
    	return line.isEmpty();
    }

    /**
        TIME COMPLEXITY REQUIREMENT: O(1)
    */
    public T peek()
    {
    	return line.get(0);
    }

	@Override
	public Iterator<T> iterator() {
		return line.iterator();
	}

}
