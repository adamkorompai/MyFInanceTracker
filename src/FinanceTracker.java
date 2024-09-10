import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Transaction {
    private String description;
    private double amount;
    private boolean isExpense;

    public Transaction(String description, double amount, boolean isExpense) {
        this.description = description;
        this.amount = amount;
        this.isExpense = isExpense;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public boolean isExpense() { return isExpense; }

    @Override
    public String toString() {
        return (isExpense ? "Expense: " : "Income: ") + description + " - $" + amount;
    }
}

public class FinanceTracker {
    private List<Transaction> transactions;

    public FinanceTracker() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(int index) {
        if (index >= 0 && index < transactions.size()) {
            transactions.remove(index);
        }
    }

    public void listTransactions() {
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(i + ": " + transactions.get(i));
        }
    }

    public double calculateBalance() {
        double balance = 0;
        for (Transaction t : transactions) {
            if (t.isExpense()) {
                balance -= t.getAmount();
            } else {
                balance += t.getAmount();
            }
        }
        return balance;
    }

    public static void main(String[] args) {
        FinanceTracker tracker = new FinanceTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add transaction");
            System.out.println("2. Remove transaction");
            System.out.println("3. List transactions");
            System.out.println("4. Show balance");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    System.out.print("Is this an expense? (true/false): ");
                    boolean isExpense = scanner.nextBoolean();
                    tracker.addTransaction(new Transaction(description, amount, isExpense));
                    break;
                case 2:
                    System.out.print("Enter index of transaction to remove: ");
                    int index = scanner.nextInt();
                    tracker.removeTransaction(index);
                    break;
                case 3:
                    tracker.listTransactions();
                    break;
                case 4:
                    System.out.println("Current balance: $" + tracker.calculateBalance());
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}