/**
    - This class represents a single transaction
    - Do NOT edit the provided methods
    - The only thing you MUST add in this class is the method required by the Comparable interface
    - Comparison between two transactions is based on the fee. The higher the fee the larger the transaction (i.e. it has higher priority)
*/
public class Transaction implements Comparable<Transaction>
{
    private String sender;
    private String receiver;
    private int amount;
    private int fee;
    
    public Transaction(String sender, String receiver, int amount, int fee)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.fee = fee;
    }

    public String toString()
    {
        return String.format("%s %s %d %d", sender, receiver, amount, fee);
    }

    public int getFee()
    {
        return fee;
    }

	@Override
	public int compareTo(Transaction o) {
		// TODO Auto-generated method stub
		return this.getFee() - o.getFee();
	}
}
