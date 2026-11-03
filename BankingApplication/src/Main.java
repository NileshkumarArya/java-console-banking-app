import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Entry point: console-based menu that lets the user create accounts,
 * deposit, withdraw, check balance, transfer funds, view statements,
 * and close accounts.
 */
public class Main {

    private static final Bank bank = new Bank();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=====================================================");
        System.out.println("     WELCOME TO JAVA CONSOLE BANKING APPLICATION");
        System.out.println("=====================================================");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> checkBalance();
                case 5 -> transferFunds();
                case 6 -> displayAllAccounts();
                case 7 -> viewTransactionHistory();
                case 8 -> closeAccount();
                case 9 -> {
                    System.out.println("Thank you for using the bank. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please select 1-9.");
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n--------------- MAIN MENU ---------------");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Balance Inquiry");
        System.out.println("5. Transfer Funds");
        System.out.println("6. Display All Accounts");
        System.out.println("7. View Transaction History");
        System.out.println("8. Close Account");
        System.out.println("9. Exit");
        System.out.println("------------------------------------------");
    }

    private static void createAccount() {
        System.out.print("Enter account type (S for Savings / C for Current): ");
        String type = sc.next().trim();
        sc.nextLine();
        System.out.print("Enter account holder name: ");
        String name = sc.nextLine().trim();
        System.out.print("Enter phone number: ");
        String phone = sc.nextLine().trim();
        double openingBalance = readDouble("Enter opening balance (min Rs. 500): ");

        if (openingBalance < 500) {
            System.out.println("Opening balance must be at least Rs. 500. Account not created.");
            return;
        }

        int accNo = bank.createAccount(type, name, phone, openingBalance);
        System.out.println("Account created successfully! Your account number is: " + accNo);
    }

    private static void deposit() {
        int accNo = readInt("Enter account number: ");
        double amount = readDouble("Enter amount to deposit: ");
        try {
            bank.deposit(accNo, amount);
            System.out.println("Deposit successful. New balance: Rs. "
                    + String.format("%.2f", bank.checkBalance(accNo)));
        } catch (AccountNotFoundException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void withdraw() {
        int accNo = readInt("Enter account number: ");
        double amount = readDouble("Enter amount to withdraw: ");
        try {
            bank.withdraw(accNo, amount);
            System.out.println("Withdrawal successful. New balance: Rs. "
                    + String.format("%.2f", bank.checkBalance(accNo)));
        } catch (AccountNotFoundException | InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void checkBalance() {
        int accNo = readInt("Enter account number: ");
        try {
            double balance = bank.checkBalance(accNo);
            System.out.println("Current balance: Rs. " + String.format("%.2f", balance));
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void transferFunds() {
        int fromAcc = readInt("Enter source account number: ");
        int toAcc = readInt("Enter destination account number: ");
        double amount = readDouble("Enter amount to transfer: ");
        try {
            bank.transfer(fromAcc, toAcc, amount);
            System.out.println("Transfer successful!");
        } catch (AccountNotFoundException | InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayAllAccounts() {
        Collection<Account> all = bank.getAllAccounts();
        if (!bank.hasAccounts()) {
            System.out.println("No accounts have been created yet.");
            return;
        }
        System.out.println("\nList of all accounts:");
        for (Account acc : all) {
            acc.displayDetails();
        }
    }

    private static void viewTransactionHistory() {
        int accNo = readInt("Enter account number: ");
        try {
            bank.getAccount(accNo).printTransactionHistory();
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void closeAccount() {
        int accNo = readInt("Enter account number to close: ");
        try {
            bank.closeAccount(accNo);
            System.out.println("Account #" + accNo + " has been closed.");
        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /* ---------- Small helpers for safe console input ---------- */

    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount.");
            }
        }
    }
}
