package bank;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private String transactionType;
    private double amount;
    private Date transactionDate;

    public Transaction(String transactionType, double amount, Date transactionDate) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(transactionDate) + " - " + transactionType + ": ₹" + amount;
    }
}
