import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;


public final class Utilities {
	/**
	 * Reads the transactions from a text file and adds them to a priority queue
	 * 
	 * @param pgmFile is the filename of the text file
	 * 
	 *                TIME COMPLEXITY REQUIREMENT: O(N)
	 * 
	 */
	public static PriorityLine<Transaction> loadTransactions(String pgmFile) {


		PriorityLine<Transaction> line = new PriorityLine<>();


		try {
			Scanner scanner = new Scanner(new File(pgmFile));


			// read all values
			while (scanner.hasNext()) {
				Transaction transaction = new Transaction(scanner.next(), scanner.next(),
						scanner.nextInt(), scanner.nextInt());
				line.enqueue(transaction);
			}


			// close scanner
			scanner.close();


		} catch (Exception e) {
			throw new RuntimeException("File not found");
		}


		return line;
	}


	/**
	 * @param t             is the transaction that we want to verify it's contained
	 *                      in a certain block
	 * @param blockRootHash is the root hash code stored in the respective block
	 * @param proof         is the list of hashes extracted with the method
	 *                      extractProof
	 * @return true if the transaction is verified, false otherwise
	 * 
	 *         TIME COMPLEXITY REQUIREMENT: O(logN)
	 */
	public static boolean verifyTransaction(Transaction t, SinglyLinkedList<String> proof,
			String blockRootHash) {
		
		String generatedHash = cryptographicHashFunction(t.toString());


		for (String hash : proof) {


			generatedHash = cryptographicHashFunction(generatedHash, hash);


		}
		return generatedHash.equals(blockRootHash) ;
	}


	/**
	 **************************** DO NOT EDIT BELOW THIS LINE **************************************
	 */


	/**
	 * SHA-256 cryptographic hash function for a single input
	 */
	public static String cryptographicHashFunction(String input) {
		StringBuilder hexString = null;


		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
			hexString = new StringBuilder(2 * encodedhash.length);
			for (int i = 0; i < encodedhash.length; i++) {
				String hex = Integer.toHexString(0xff & encodedhash[i]);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}


		return hexString.toString();
	}


	/**
	 * SHA-256 cryptographic hash function for a pair of inputs It uses the XOR
	 * bitwise operator to merge the two hash codes
	 */
	public static String cryptographicHashFunction(String input1, String input2) {
		StringBuilder hexString = null;


		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedhash1 = digest.digest(input1.getBytes(StandardCharsets.UTF_8));
			byte[] encodedhash2 = digest.digest(input2.getBytes(StandardCharsets.UTF_8));
			hexString = new StringBuilder(2 * encodedhash1.length);
			for (int i = 0; i < encodedhash1.length; i++) {
				String hex = Integer.toHexString(0xff & (encodedhash1[i] ^ encodedhash2[i]));
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}


		return hexString.toString();
	}


}





