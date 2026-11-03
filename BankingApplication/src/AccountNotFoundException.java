/**
 * Custom checked exception thrown when an operation refers to
 * an account number that does not exist in the bank's records.
 */
public class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String message) {
        super(message);
    }
}
