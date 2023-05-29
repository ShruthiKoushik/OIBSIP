import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class User {
    private String userId;
    private String userPin;
    private double accountBalance;
    private List<String> transactionHistory;

    public User(String userId, String userPin, double accountBalance) {
        this.userId = userId;
        this.userPin = userPin;
        this.accountBalance = accountBalance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String enteredPin) {
        return userPin.equals(enteredPin);
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= accountBalance) {
            accountBalance -= amount;
            transactionHistory.add("Withdraw: " + amount);
            System.out.println("Successfully withdrawn: " + amount);
        } else {
            System.out.println("Invalid withdrawal amount!");
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            accountBalance += amount;
            transactionHistory.add("Deposit: " + amount);
            System.out.println("Successfully deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    public void transfer(User recipient, double amount) {
        if (amount > 0 && amount <= accountBalance) {
            accountBalance -= amount;
            recipient.accountBalance += amount;
            transactionHistory.add("Transfer: " + amount + " to " + recipient.getUserId());
            System.out.println("Successfully transferred: " + amount + " to " + recipient.getUserId());
        } else {
            System.out.println("Invalid transfer amount!");
        }
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

class ATM {
    private List<User> users;
    private User currentUser;
    private Scanner scanner;

    public ATM() {
        users = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void registerUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        if (isUserIdTaken(userId)) {
            System.out.println("User ID is already taken. Please choose a different one.");
            return;
        }

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        User user = new User(userId, pin, balance);
        users.add(user);
        System.out.println("User registered successfully!");
    }

    public void login() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        if (authenticateUser(userId, pin)) {
            showMenu();
            int choice = scanner.nextInt();

            while (choice != 5) {
                performOperation(choice);
                showMenu();
                choice = scanner.nextInt();
            }

            System.out.println("Thank you for using the ATM!");
        } else {
            System.out.println("Invalid User ID or PIN!");
        }
    }

    private boolean isUserIdTaken(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    private boolean authenticateUser(String userId, String pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.validatePin(pin)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    private void showMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. View Account Balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private void performOperation(int choice) {
        double amount;

        switch (choice) {
            case 1:
                System.out.println("Account Balance: " + currentUser.getAccountBalance());
                break;
            case 2:
                System.out.print("Enter withdrawal amount: ");
                amount = scanner.nextDouble();
                currentUser.withdraw(amount);
                break;
            case 3:
                System.out.print("Enter deposit amount: ");
                amount = scanner.nextDouble();
                currentUser.deposit(amount);
                break;
            case 4:
                System.out.print("Enter recipient's User ID: ");
                String recipientId = scanner.next();
                scanner.nextLine(); // Consume the newline character
                User recipient = findUserById(recipientId);
                if (recipient != null) {
                    System.out.print("Enter transfer amount: ");
                    amount = scanner.nextDouble();
                    currentUser.transfer(recipient, amount);
                } else {
                    System.out.println("Recipient User ID not found!");
                }
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    private User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nWelcome to the ATM!");
            System.out.println("1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    atm.registerUser();
                    break;
                case 2:
                    atm.login();
                    break;
                case 3:
                    System.out.println("Thank you for using the ATM!");
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }
}
