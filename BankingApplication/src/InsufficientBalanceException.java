/**
 * Custom checked exception thrown when a withdrawal or transfer
 * cannot be completed because the account does not have enough funds
 * (taking into account minimum balance / overdraft rules).
 */
public class InsufficientBalanceException extends Exception {

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
