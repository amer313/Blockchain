import java.util.Iterator;
/**
* - Represents the Merkle Tree of a single Block - You must implement all the
* public methods in this template - Anything else you add must be private - Do
* not modify the provided signatures
*/
public class MerkleTree {
	/**
	 * @param block is the Block that the Merkle Tree will be created for
	 *
	 * - The constructor first creates the Merkle Tree in memory. This
	 * MUST be done recursively; zero points if it's not recursive!
	 *
	 * - You MUST maintain a pointer to the root because the tree is
	 * built only once but it's needed many times (e.g. for traversals)
	 *
	 * - After the tree is constructed, the constructor sends the hash
	 * of the root to the block object by invoking the
	 * block.setRootHash() method
	 *
	 * TIME COMPLEXITY REQUIREMENT: O(N) SPACE COMPLEXITY REQUIREMENT:
	 * O(N)
	 */
	private Node root;
	private int innerNodes;
	private int height;
	public MerkleTree(Block block) {
		if (block.numOfTransactions() < 0) {
			throw new RuntimeException("You must have at least one transaction");
		}
		root = null;
		int blockSize = block.numOfTransactions();
		int numOfNodes = 0;
		boolean isPowerOfTwo = false;
		// check if power of 2
		isPowerOfTwo = ((blockSize & (blockSize - 1)) == 0);
		// blockSize > 0 &&
		if (isPowerOfTwo) {
			numOfNodes = 2 * blockSize - 1;
			innerNodes = numOfNodes - blockSize;
		} else {
			numOfNodes = 2 * findNextPowerOfTwo(blockSize) - 1;
			innerNodes = numOfNodes - findNextPowerOfTwo(blockSize);
		}
		// works with any binary tree, even if not perfect
		height = (int) Math.ceil(Math.log(numOfNodes + 1) / Math.log(2)) - 1;
		// similar result
		// height = (int) Math.floor(Math.log(numOfNodes) / Math.log(2));
		SinglyLinkedList<Integer> tempList = new SinglyLinkedList<>();
		SinglyLinkedList<String> hashList = new SinglyLinkedList<>();
		// create transactions hashes
		for (Transaction transaction : block) {
			hashList.add(Utilities.cryptographicHashFunction(transaction.toString()));
		}
		// add dummy nodes if needed
		if (!isPowerOfTwo) {
			for (int i = 0; i < findNextPowerOfTwo(blockSize) - blockSize; i++) {
				hashList.add(Utilities.cryptographicHashFunction("DUMMY"));
			}
		}
		// build empty list
		for (int i = 0; i < numOfNodes; i++) {
			tempList.add(i);
		}
		// create the tree
		root = buildTree(tempList, 0, numOfNodes - 1, null, hashList);
		// set root hash
		block.setRootHash(root.data);
	}
	private int findNextPowerOfTwo(int number) {
		int result = 1;
		while (result < number)
			result *= 2;
		return result;
	}
	private boolean isLeafNode(Node node) {
		return node.left == null && node.right == null;
	}
	private Node buildTree(SinglyLinkedList<Integer> list, int start, int end, Node parent,
			SinglyLinkedList<String> hashList) {
		// Base Case
		if (start > end) {
			return null;
		}
		// Get the middle node and make it parent
		int mid = (start + end) / 2;
		// Node node = new Node(Integer.toString(list.get(mid)));
		Node node = new Node(null);
		// build the left subtree
		node.left = buildTree(list, start, mid - 1, node, hashList);
		// build the right subtree
		node.right = buildTree(list, mid + 1, end, node, hashList);
		node.parent = parent;
		if (isLeafNode(node)) {
			node.data = hashList.remove(0);
		} else {
			node.data = Utilities.cryptographicHashFunction(node.left.data, node.right.data);
			node.left.sibling = node.right;
			node.right.sibling = node.left;
		}
		return node;
	}
	/**
	 * @return the height of the tree
	 *
	 * TIME COMPLEXITY REQUIREMENT: O(1)
	 */
	public int height() {
		return height;
	}
	/**
	 * @return the number of inner nodes in the tree
	 *
	 * TIME COMPLEXITY REQUIREMENT: O(1)
	 */
	public int innerNodes() {
		return innerNodes;
	}
	/**
	 * @return a list of the hash codes contained in the tree by walking the tree in
	 * a level-order
	 *
	 * TIME COMPLEXITY REQUIREMENT: O(N)
	 */
	public SinglyLinkedList<String> breadthFirstTraversal() {
		return breadthFirstTraversal(root);
	}
	private SinglyLinkedList<String> breadthFirstTraversal(Node node) {
		SinglyLinkedList<String> list = new SinglyLinkedList<>();
		if (node == null) {
			return null;
		}
		LocalLinkedList<Node> line = new LocalLinkedList<>();
		line.add(node);
		while (!line.isEmpty()) {
			Node current = line.remove(0);
			list.add(current.data);
			if (current.left != null) {
				line.add(current.left);
			}
			if (current.right != null) {
				line.add(current.right);
			}
		}
		return list;
	}
	/**
	 * @return a list of the hash codes contained in the tree by walking the tree in
	 * a certain order
	 *
	 * @param order is an enumeration representing the three possible depth-first
	 * traversals
	 *
	 * You MUST use recursion for this method; zero points if it's not
	 * recursive!
	 *
	 * TIME COMPLEXITY REQUIREMENT: O(N)
	 */
	public SinglyLinkedList<String> depthFirstTraversal(Order order) {
		SinglyLinkedList<String> returnList = new SinglyLinkedList<>();
		switch (order) {
		case PREORDER: {
			preOrder(root, returnList);
			break;
		}
		case INORDER: {
			inOrder(root, returnList);
			break;
		}
		case POSTORDER: {
			postOrder(root, returnList);
			break;
		}
		default:
			throw new RuntimeException("Unexpected value: " + order);
		}
		return returnList;
	}
	private void inOrder(Node node, SinglyLinkedList<String> list) {
		if (node == null) {
			return;
		}
		inOrder(node.left, list);
		list.add(node.data);
		inOrder(node.right, list);
	}
	private void preOrder(Node node, SinglyLinkedList<String> list) {
		if (node == null) {
			return;
		}
		list.add(node.data);
		preOrder(node.left, list);
		preOrder(node.right, list);
	}
	private void postOrder(Node node, SinglyLinkedList<String> list) {
		if (node == null) {
			return;
		}
		postOrder(node.left, list);
		postOrder(node.right, list);
		list.add(node.data);
	}
	private Node findNode(Node node, String hash) {
		// base case
		if (node == null || node.data.equals(hash))
			return node;
		Node findLeft = findNode(node.left, hash);
		if (findLeft != null) {
			return findLeft;
		}
		Node findRight = findNode(node.right, hash);
		if (findRight != null) {
			return findRight;
		}
		return null;
	}
	/**
	 * @return a list of the hash codes that are required to prove that a
	 * transaction is contained in the block that this Merkle Tree encodes.
	 * In the example depicted in Figure 4 of the project description, the
	 * content of this list would be [F] -> [L] -> [M] The head of the list
	 * is the deepest hash code and the tail of the list is the top-most
	 * hash code required for the proof. The root hash code must NOT be
	 * added to this list because it's already stored inside each Block
	 *
	 * You MUST use recursion for this method; zero points if it's not
	 * recursive!
	 *
	 * TIME COMPLEXITY REQUIREMENT: O(N)
	 */
	public SinglyLinkedList<String> extractProof(Transaction t) {
		SinglyLinkedList<String> returnList = new SinglyLinkedList<>();
		String transactionHash = Utilities.cryptographicHashFunction(t.toString());
		Node leafNode = findNode(root, transactionHash);
		if (leafNode != null) {
			generateList(leafNode, returnList);
		}
		return returnList;
	}
	private void generateList(Node currentNode, SinglyLinkedList<String> returnList) {
		// base case root node
		if (currentNode.parent == null) {
			return;
		}
		// recursive case
		returnList.add(currentNode.sibling.data);
		generateList(currentNode.parent, returnList);
	}
	// private node class
	private class Node {
		String data;
		Node left;
		Node right;
		Node parent;
		Node sibling;
		public Node(String data) {
			this.data = data;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.sibling = null;
		}
	}
	private class LocalLinkedList<T> implements Iterable<T> {
		private Node<T> headNode;
		private Node<T> tail;
		private int size;
		LocalLinkedList() {
			headNode = null;
			tail = null;
			size = 0;
		}
		void add(T value) {
			Node<T> newNode = new Node<T>(value);
			if (isEmpty()) {
				headNode = newNode;
				tail = newNode;
			} else {
				tail.setNext(newNode);
				tail = newNode;
			}
			size++;
		}
		T remove(int index) {
			if (index < 0 || index >= size) {
				throw new RuntimeException("Index is invalid");
			}
			T returnValue = null;
			int localIndex = 0;
			Node<T> currentNode = headNode;
			Node<T> priorNode = null;
			// first node
			if (index == 0) {
				returnValue = headNode.getValue();
				headNode = headNode.getNext();
				if (size == 1) {
					tail = null;
				}
			} else {
				while (localIndex != index) {
					priorNode = currentNode;
					currentNode = currentNode.getNext();
					localIndex++;
				}
				returnValue = currentNode.getValue();
				priorNode.setNext(currentNode.getNext());
				// if last node, move tail
				if (index == size - 1) {
					tail = priorNode;
				}
			}
			size--;
			return returnValue;
		}
		boolean isEmpty() {
			return size == 0;
		}
		private class Node<R> {
			private R data;
			private Node<R> next;
			Node(R value) {
				data = value;
				next = null;
			}
			R getValue() {
				return data;
			}
			Node<R> getNext() {
				return next;
			}
			void setNext(Node<R> p) {
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
			};
			/**
			 * Returning iterator
			 */
			return iterator;
		}
	}
	public static void main(String[] args) {
		Block block = new Block();
		block.addTransaction(new Transaction("sender4", "receiver4", 45123, 9));
		block.addTransaction(new Transaction("sender7", "receiver7", 12045, 7));
		block.addTransaction(new Transaction("sender5", "receiver5", 51234, 6));
		block.addTransaction(new Transaction("sender6", "receiver6", 53234, 8));
		MerkleTree tree = new MerkleTree(block);
	}
}

