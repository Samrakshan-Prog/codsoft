import java.util.Scanner;
class BankAccount {
    private double balance;
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposit successful. Current balance: $" + String.format("%.2f", balance));
        } else {
            System.out.println("Invalid deposit amount. Please enter a positive number.");
        }
    }
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawal successful. Current balance: $" + String.format("%.2f", balance));
            return true;
        } else if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Please enter a positive number.");
            return false;
        } else {
            System.out.println("Insufficient balance. Withdrawal failed.");
            return false;
        }
    }
}
public class ATMInterface {
    private BankAccount userAccount;
    private Scanner scanner;
    public ATMInterface(BankAccount account) {
        this.userAccount = account;
        this.scanner = new Scanner(System.in);
    }
    private void displayMenu() {
        System.out.println("\n--- ATM Menu ---");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.print("Please choose an option: ");
    }
    public void run() {
        int choice;
        while (true) {
            displayMenu();
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        checkBalance();
                        break;
                    case 2:
                        deposit();
                        break;
                    case 3:
                        withdraw();
                        break;
                    case 4:
                        System.out.println("Thank you for using the ATM. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid option. Please choose a number from 1 to 4.");
                        break;
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }
    private void checkBalance() {
        System.out.println("Your current balance is: $" + String.format("%.2f", userAccount.getBalance()));
    }
    private void deposit() {
        System.out.print("Enter amount to deposit: $");
        try {
            double amount = scanner.nextDouble();
            userAccount.deposit(amount);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.next();
        }
    }
    private void withdraw() {
        System.out.print("Enter amount to withdraw: $");
        try {
            double amount = scanner.nextDouble();
            userAccount.withdraw(amount);
        } catch (java.util.InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            scanner.next();
        }
    }
    public static void main(String[] args) {
        BankAccount myAccount = new BankAccount(1000.00);
        ATMInterface myAtm = new ATMInterface(myAccount);
        myAtm.run();
    }
}
