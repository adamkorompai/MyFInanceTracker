import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.util.*;

class Transaction {
    private String description;
    private double amount;
    private boolean isExpense;
    private String category;

    public Transaction(String description, double amount, boolean isExpense, String category) {
        this.description = description;
        this.amount = amount;
        this.isExpense = isExpense;
        this.category = category;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public boolean isExpense() { return isExpense; }
    public String getCategory() { return category; }

    @Override
    public String toString() {
        return (isExpense ? "Expense: " : "Income: ") + description + " - $" + amount + " (" + category + ")";
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

    public void showExpensePieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Transaction t : transactions) {
            if (t.isExpense()) {
                categoryTotals.put(t.getCategory(),
                        categoryTotals.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
            }
        }

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Expense Distribution by Category",
                dataset,
                true,
                true,
                false
        );

        ChartFrame frame = new ChartFrame("Expense Pie Chart", chart);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FinanceTracker tracker = new FinanceTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add transaction");
            System.out.println("2. Remove transaction");
            System.out.println("3. List transactions");
            System.out.println("4. Show balance");
            System.out.println("5. Show expense pie chart");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Is this an expense? (true/false): ");
                    boolean isExpense = scanner.nextBoolean();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    tracker.addTransaction(new Transaction(description, amount, isExpense, category));
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
                    tracker.showExpensePieChart();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}