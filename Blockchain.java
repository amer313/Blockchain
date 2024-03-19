import java.util.Iterator;

/**
 * - The class represents the entire blockchain - You must implement all the
 * public methods in this template plus the method(s) required by the Iterable
 * interface - Anything else you add must be private - Do not modify the
 * provided signatures
 */

public class Blockchain implements Iterable<Block> {
	private SinglyLinkedList<Block> blockchainLinkedList;

	/**
	 * The concstructor takes a priority queue and creates the linked list of blocks
	 * 
	 * @param threshold is the minimum amount of cumulative fees that is required to
	 *                  create a new block. The block must contain the minimum
	 *                  number of transactions that satisfy the threshold criterion
	 * 
	 *                  TIME COMPLEXITY REQUIREMENT: O(N)
	 */
	public Blockchain(PriorityLine<Transaction> queue, int threshold) {
		blockchainLinkedList = new SinglyLinkedList<Block>();
		int cummulativeFees = 0;

		while (queue.size() != 0) {
			cummulativeFees = 0;
			Block newBlock = new Block();

			while (cummulativeFees < threshold) {
				cummulativeFees += queue.peek().getFee();
				newBlock.addTransaction(queue.dequeue());
				if(queue.isEmpty()){
					break;
				}
			}
			blockchainLinkedList.add(newBlock);
		}
	}

	@Override
	public Iterator<Block> iterator() {
		return blockchainLinkedList.iterator();
	}

}
