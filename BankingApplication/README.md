# Console-Based Banking Application (Java, OOP)

A simple menu-driven banking system built in core Java to demonstrate
Object-Oriented Programming principles.

## Features
1. Create Account (Savings or Current)
2. Deposit
3. Withdraw
4. Balance Inquiry
5. Transfer Funds between accounts
6. Display All Accounts
7. View Transaction History
8. Close Account
9. Exit

## OOP Concepts Used
| Concept        | Where it's used |
|-----------------|------------------|
| **Abstraction**    | `Account` is an abstract class; `withdraw()` and `getAccountType()` have no implementation there. |
| **Encapsulation**  | All fields in `Account` are `private`; accessed only via getters/setters and controlled methods like `deposit()`. |
| **Inheritance**    | `SavingsAccount` and `CurrentAccount` both extend `Account`. |
| **Polymorphism**   | Each subclass overrides `withdraw()` with its own rule (min. balance vs. overdraft), called polymorphically through the `Account` reference. |
| **Exception Handling** | Custom checked exceptions `InsufficientBalanceException` and `AccountNotFoundException` keep error handling clean and specific. |

## Project Structure
```
BankingApplication/
├── src/
│   ├── Account.java                     (abstract base class)
│   ├── SavingsAccount.java              (extends Account)
│   ├── CurrentAccount.java              (extends Account)
│   ├── Bank.java                        (manages all accounts)
│   ├── InsufficientBalanceException.java
│   ├── AccountNotFoundException.java
│   └── Main.java                        (console menu / entry point)
└── README.md
```

## How to Compile & Run

Requires Java JDK 11 or above.

```bash
cd BankingApplication
javac -d bin src/*.java
java -cp bin Main
```

## Business Rules
- **Savings Account**: must always maintain a minimum balance of **Rs. 1000**. Earns 4% annual interest (calculable via `calculateAnnualInterest()`).
- **Current Account**: no minimum balance, but allows an overdraft of up to **Rs. 5000**.
- Minimum opening balance for any new account: **Rs. 500**.
- Every account keeps a timestamped transaction history (deposits, withdrawals, opening, closing).

## Possible Extensions
- Persist accounts to a file or database so data survives a restart.
- Add login/PIN authentication per account.
- Add a `FixedDepositAccount` subclass to further showcase polymorphism.
- Export statements to CSV/PDF.
