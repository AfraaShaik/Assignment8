package bank;
import java.util.ArrayList;
import java.util.Date;

public class Account {
    private String accNum;
    private String name;
    private String accType;
    private double balance;
    private ArrayList<Transaction> transactions;

    public Account(String accNum, String name, String accType, double balance) {
        this.accNum = accNum;
        this.name = name;
        this.accType = accType;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accNum;
    }

    public String getAccountType() {
        return accType;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount, new Date()));
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        transactions.add(new Transaction("Withdrawal", amount, new Date()));
        return true;
    }

    public void addMonthlyInterest() {
        double interestRate = 0.03;
        balance += balance * interestRate;
    }

    public void printStatement() {
        System.out.println("Account Statement for " + accNum);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}
