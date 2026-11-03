/**
 * Savings account: earns interest, but must always keep a minimum balance.
 * Demonstrates inheritance (extends Account) and polymorphism
 * (provides its own version of withdraw()).
 */
public class SavingsAccount extends Account {

    private static final double MIN_BALANCE = 1000.0;
    private static final double INTEREST_RATE = 4.0; // percent per annum

    public SavingsAccount(int accountNumber, String accountHolderName,
                           String phoneNumber, double openingBalance) {
        super(accountNumber, accountHolderName, phoneNumber, openingBalance);
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new InsufficientBalanceException("Withdrawal amount must be positive.");
        }
        if (getBalance() - amount < MIN_BALANCE) {
            throw new InsufficientBalanceException(
                    "Withdrawal denied: Savings account must maintain a minimum balance of Rs. "
                            + String.format("%.2f", MIN_BALANCE));
        }
        setBalance(getBalance() - amount);
        log("Withdrew Rs. " + String.format("%.2f", amount));
    }

    @Override
    public String getAccountType() {
        return "Savings Account";
    }

    public double calculateAnnualInterest() {
        return getBalance() * INTEREST_RATE / 100.0;
    }

    public static double getMinBalance() {
        return MIN_BALANCE;
    }
}
