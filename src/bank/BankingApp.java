package bank;
import java.util.*;

public class BankingApp {
    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n**** Banking Application ****");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Choose an option");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> register();
                case 2 -> login();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        //checking if the user already exists
        if (getUser(username) != null) {
            System.out.println("Username already exists! Please choose a different username.");
            return;
        }
        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        User user = getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            loggedInUser = user;
            System.out.println("Login successful!");
            userMenu();
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    private static void userMenu() {
        while (loggedInUser != null) {
            System.out.println("\n**** User Menu ****");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Generate Statement");
            System.out.println("5. Calculate Interest (Savings Account)");
            System.out.println("6. Check Balance");
            System.out.println("7. Logout");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> openAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> generateStatement();
                case 5 -> calculateInterest();
                case 6 -> checkBalance();
                case 7 -> logout();
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private static User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private static void openAccount() {
        System.out.print("Enter account holder name: ");
        String name = sc.nextLine();
        System.out.print("Enter account type (savings/checking): ");
        String type = sc.nextLine();
        System.out.print("Enter initial deposit: ");
        double initialDeposit = sc.nextDouble();
        sc.nextLine();

        //Generating an unique identifier
        String accountNumber = UUID.randomUUID().toString();
        Account newAccount = new Account(accountNumber, name, type, initialDeposit);
        loggedInUser.addAccount(newAccount);
        System.out.println("Account opened successfully! Account Number: " + accountNumber);
    }

    private static void deposit() {
        Account account = selectAccount();
        if (account != null) {
            System.out.print("Enter deposit amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            account.deposit(amount);
            System.out.println("Deposit successful!");
        }
    }

    private static void withdraw() {
        Account account = selectAccount();
        if (account != null) {
            System.out.print("Enter withdrawal amount: ");
            double amount = sc.nextDouble();
            sc.nextLine();
            if (account.withdraw(amount)) {
                System.out.println("Withdrawal successful!");
            } else {
                System.out.println("Insufficient Balance!");
            }
        }
    }

    private static void generateStatement() {
        Account account = selectAccount();
        if (account != null) {
            account.printStatement();
        }
    }

    private static void calculateInterest() {
        Account account = selectAccount();
        if (account != null && account.getAccountType().equalsIgnoreCase("savings")) {
            account.addMonthlyInterest();
            System.out.println("Interest added successfully!");
        } else {
            System.out.println("Interest calculation only available for savings accounts.");
        }
    }

    private static void checkBalance() {
        Account account = selectAccount();
        if (account != null) {
            System.out.println("Current Balance: ₹" + account.getBalance());
        }
    }

    private static void logout() {
        System.out.println("Logged out successfully.");
        loggedInUser = null;
    }

    private static Account selectAccount() {
        if (loggedInUser.getAccounts().isEmpty()) {
            System.out.println("No accounts found! Open an account first.");
            return null;
        }
        System.out.println("Select Account:");
        for (int i = 0; i < loggedInUser.getAccounts().size(); i++) {
            System.out.println((i + 1) + ". Account Number: " + loggedInUser.getAccounts().get(i).getAccountNumber());
        }
        System.out.print("Choose an account: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice > 0 && choice <= loggedInUser.getAccounts().size()) {
            return loggedInUser.getAccounts().get(choice - 1);
        } else {
            System.out.println("Invalid choice!");
            return null;
        }
    }
}
