package bank;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Operation {
    private int balance;
    private Scanner scanner = new Scanner(System.in);

    Operation(int initialBalance) {
        this.balance = initialBalance;
    }

    int getBalance() {
        return balance;
    }

    void setBalance(int newBalance) {
        this.balance = newBalance;
    }

    void display() {
    	System.out.println("Enter your choice(eg: Enter '1' for Money Withdrawal):");
        System.out.println("1. Money withdrawal\n2. Money deposit\n3. Change pin\n4. Check balance\n");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                performWithdrawal();
                break;

            case 2:
                performDeposit();
                break;

            case 3:
                changePin();
                break;

            case 4:
                checkBalance();
                break;

            default:
                System.out.println("Invalid choice");
        }
    }

    private void performWithdrawal() {
        System.out.println("Enter the amount to withdraw:");
        int amount = scanner.nextInt();
        if (amount > balance) {
            System.out.println("Insufficient funds");
        } else {
            balance -= amount;
            System.out.println(amount + " amount has been withdrawn");
            System.out.println("Remaining balance: " + balance);
        }
    }

    private void performDeposit() {
        System.out.println("Enter the amount to be deposited:");
        int depositAmount = scanner.nextInt();
        balance += depositAmount;
        System.out.println(depositAmount + " have been deposited");
        System.out.println("Remaining balance: " + balance);
    }
    
    //Change pin
    private void changePin() {
        System.out.println("Enter new pin:");
        int newPin = scanner.nextInt();
        System.out.println(" New pin has been updated");
    }
    
    //Check balance
    private void checkBalance() {
        System.out.println("Balance: " + balance);
    }

    void closeScanner() {
        scanner.close();
    }
}

class BankUser {
    private String userName;
    private String bankName;
    private int balance;
    private String password;

    BankUser(String userName, String bankName, int initialBalance, String password) {
        this.userName = userName;
        this.bankName = bankName;
        this.balance = initialBalance;
        this.password = password;
    }

    String getUserName() {
        return userName;
    }

    int getBalance() {
        return balance;
    }

    String getPassword() {
        return password;
    }
}

public class Banking {
    private static final String INCORRECT_PASSWORD_MESSAGE = "Incorrect password!!";
    private static final String USER_NOT_FOUND_MESSAGE = "User not found!!";
    private static final String EXIT_MESSAGE = "Exiting...";

    private static Operation bankOperation;
    private static Scanner scanner = new Scanner(System.in);
    private static Map<String, BankUser> userMap = createSampleUserData();

    public static void main(String[] args) {
        bankOperation = new Operation(0);

        System.out.println("Welcome to the Banking System!");

        while (true) {
            System.out.println("\nEnter your user name (or type 'exit' to exit, 'create' to create a new account):");
            String userName = scanner.next();

            if (userName.equalsIgnoreCase("exit")) {
                System.out.println(EXIT_MESSAGE);
                break;
            } else if (userName.equalsIgnoreCase("create")) {
                createAccount();
                continue;
            }

            BankUser currentUser = userMap.get(userName);

            if (currentUser == null) {
                System.out.println(USER_NOT_FOUND_MESSAGE);
                continue;
            }

            System.out.println("Enter your password:");
            String enteredPassword = scanner.next();

            if (!enteredPassword.equals(currentUser.getPassword())) {
                System.out.println(INCORRECT_PASSWORD_MESSAGE);
                continue;
            }

            bankOperation = new Operation(currentUser.getBalance());
            bankOperation.display();

            System.out.println("\nThank you for using the Banking System!");
        }

        bankOperation.closeScanner();
    }

    private static Map<String, BankUser> createSampleUserData() {
        Map<String, BankUser> userMap = new HashMap<>();
        userMap.put("Munazza", new BankUser("Munazza Begam", "SBI", 100000, "2003"));
        userMap.put("Swathi", new BankUser("Swathi Prabhu", "Union", 150000, "#123"));
        userMap.put("Kavya", new BankUser("Kavya G", "Axis", 120000, "123456789"));
        userMap.put("Afham", new BankUser("Afham Beig", "Kotak", 80000, "270703"));
        return userMap;
    }
    
    //create account
    private static void createAccount() {
        System.out.println("Enter your name:");
        String userName = scanner.next();

        System.out.println("Enter your bank name:");
        String bankName = scanner.next();
        
        int initialBalance = 0;

        String password;
        String confirmPassword;

        do {
            System.out.println("Create a password:");
            password = scanner.next();

            System.out.println("Confirm password:");
            confirmPassword = scanner.next();

            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match. Please try again.");
            }
        } while (!password.equals(confirmPassword));

        BankUser newUser = new BankUser(userName, bankName, initialBalance, password);
        userMap.put(userName, newUser);

        System.out.println("Account created successfully!");

    }
}