import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class representing a generic bank account.
 *
 * Demonstrates:
 *  - Abstraction  : withdraw() and getAccountType() are left abstract so
 *                   every concrete account type defines its own rules.
 *  - Encapsulation: all fields are private; access is only through
 *                   getters or controlled methods such as deposit()/withdraw().
 */
public abstract class Account {

    private static final DateTimeFormatter TS_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final int accountNumber;
    private final String accountHolderName;
    private String phoneNumber;
    private double balance;
    private final List<String> transactionHistory;
    private final LocalDateTime createdOn;
    private boolean active;

    protected Account(int accountNumber, String accountHolderName,
                       String phoneNumber, double openingBalance) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.phoneNumber = phoneNumber;
        this.balance = openingBalance;
        this.transactionHistory = new ArrayList<>();
        this.createdOn = LocalDateTime.now();
        this.active = true;
        log("Account opened with initial balance Rs. " + String.format("%.2f", openingBalance));
    }

    /* ---------- Abstract methods: polymorphic behaviour ---------- */

    /**
     * Each account type enforces its own withdrawal rule
     * (e.g. Savings keeps a minimum balance, Current allows overdraft).
     */
    public abstract void withdraw(double amount) throws InsufficientBalanceException;

    /** Returns a human-readable label for the account type. */
    public abstract String getAccountType();

    /* ---------- Common (shared) behaviour ---------- */

    public void deposit(double amount) {
        balance += amount;
        log("Deposited Rs. " + String.format("%.2f", amount));
    }

    public void displayDetails() {
        System.out.println("---------------------------------------------");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Holder Name    : " + accountHolderName);
        System.out.println("Phone Number   : " + phoneNumber);
        System.out.println("Account Type   : " + getAccountType());
        System.out.println("Balance        : Rs. " + String.format("%.2f", balance));
        System.out.println("Status         : " + (active ? "ACTIVE" : "CLOSED"));
        System.out.println("Opened On      : " + createdOn.format(TS_FORMAT));
        System.out.println("---------------------------------------------");
    }

    public void printTransactionHistory() {
        System.out.println("Transaction history for Account #" + accountNumber + ":");
        if (transactionHistory.isEmpty()) {
            System.out.println("  No transactions yet.");
        } else {
            for (String entry : transactionHistory) {
                System.out.println("  " + entry);
            }
        }
    }

    protected void log(String message) {
        transactionHistory.add("[" + LocalDateTime.now().format(TS_FORMAT) + "] " + message);
    }

    /* ---------- Getters (encapsulated access) ---------- */

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
        log("Account closed.");
    }
}
