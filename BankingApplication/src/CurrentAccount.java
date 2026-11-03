/**
 * Current account: meant for frequent transactions, allows a limited
 * overdraft instead of enforcing a minimum balance.
 * Demonstrates inheritance (extends Account) and polymorphism
 * (its own version of withdraw()).
 */
public class CurrentAccount extends Account {

    private static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(int accountNumber, String accountHolderName,
                           String phoneNumber, double openingBalance) {
        super(accountNumber, accountHolderName, phoneNumber, openingBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new InsufficientBalanceException("Withdrawal amount must be positive.");
        }
        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            throw new InsufficientBalanceException(
                    "Withdrawal denied: exceeds overdraft limit of Rs. "
                            + String.format("%.2f", OVERDRAFT_LIMIT));
        }
        setBalance(getBalance() - amount);
        log("Withdrew Rs. " + String.format("%.2f", amount));
    }

    @Override
    public String getAccountType() {
        return "Current Account";
    }

    public static double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
}
