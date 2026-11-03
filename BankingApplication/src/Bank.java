import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Central banking system that owns and manages all accounts.
 * Encapsulates the account storage (a Map) and exposes only
 * well-defined operations to the outside world (Main / UI layer).
 */
public class Bank {

    private final Map<Integer, Account> accounts;
    private int nextAccountNumber;

    public Bank() {
        this.accounts = new LinkedHashMap<>();
        this.nextAccountNumber = 1001; // starting account number
    }

    /** Creates a new account and returns its freshly generated account number. */
    public int createAccount(String type, String holderName, String phoneNumber, double openingBalance) {
        int accNo = nextAccountNumber++;
        Account account;
        if (type.equalsIgnoreCase("S")) {
            account = new SavingsAccount(accNo, holderName, phoneNumber, openingBalance);
        } else {
            account = new CurrentAccount(accNo, holderName, phoneNumber, openingBalance);
        }
        accounts.put(accNo, account);
        return accNo;
    }

    public Account getAccount(int accountNumber) throws AccountNotFoundException {
        Account acc = accounts.get(accountNumber);
        if (acc == null || !acc.isActive()) {
            throw new AccountNotFoundException("No active account found with number: " + accountNumber);
        }
        return acc;
    }

    public void deposit(int accountNumber, double amount) throws AccountNotFoundException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
        getAccount(accountNumber).deposit(amount);
    }

    public void withdraw(int accountNumber, double amount)
            throws AccountNotFoundException, InsufficientBalanceException {
        getAccount(accountNumber).withdraw(amount);
    }

    public double checkBalance(int accountNumber) throws AccountNotFoundException {
        return getAccount(accountNumber).getBalance();
    }

    public void closeAccount(int accountNumber) throws AccountNotFoundException {
        getAccount(accountNumber).deactivate();
    }

    public void transfer(int fromAccNo, int toAccNo, double amount)
            throws AccountNotFoundException, InsufficientBalanceException {
        Account from = getAccount(fromAccNo);
        Account to = getAccount(toAccNo);
        from.withdraw(amount);   // rolls back automatically: if this throws, nothing changed
        to.deposit(amount);
    }

    public Collection<Account> getAllAccounts() {
        return accounts.values();
    }

    public boolean hasAccounts() {
        return !accounts.isEmpty();
    }
}
