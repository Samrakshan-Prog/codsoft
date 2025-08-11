import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {

    private HashMap<String, Double> exchangeRates;
    private Scanner scanner;

    public CurrencyConverter() {
        this.scanner = new Scanner(System.in);
        initializeExchangeRates();
    }

    private void initializeExchangeRates() {
        exchangeRates = new HashMap<>();

        exchangeRates.put("USD_EUR", 0.93);
        exchangeRates.put("USD_GBP", 0.79);
        exchangeRates.put("EUR_USD", 1.07);
        exchangeRates.put("EUR_GBP", 0.85);
        exchangeRates.put("GBP_USD", 1.26);
        exchangeRates.put("GBP_EUR", 1.18);
    }

    private void displayAvailableCurrencies() {
        System.out.println("Available currencies: USD, EUR, GBP");
    }

    private void performConversion() {
        System.out.println("\n--- Currency Conversion ---");
        displayAvailableCurrencies();

        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.next().toUpperCase();
        
        System.out.print("Enter target currency (e.g., EUR): ");
        String targetCurrency = scanner.next().toUpperCase();
        
        String rateKey = baseCurrency + "_" + targetCurrency;
        
        if (exchangeRates.containsKey(rateKey)) {
            System.out.print("Enter amount to convert: ");
            try {
                double amount = scanner.nextDouble();
                if (amount > 0) {
                    double rate = exchangeRates.get(rateKey);
                    double convertedAmount = amount * rate;
                    System.out.printf("%.2f %s is equal to %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
                } else {
                    System.out.println("Invalid amount. Please enter a positive number.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid amount.");
                scanner.next();
            }
        } else {
            System.out.println("Conversion rate not found for " + baseCurrency + " to " + targetCurrency + ".");
        }
    }

    public void run() {
        String choice;
        do {
            performConversion();
            System.out.print("\nDo you want to perform another conversion? (yes/no): ");
            choice = scanner.next();
        } while (choice.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you for using the currency converter. Goodbye!");
        scanner.close();
    }

    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        converter.run();
    }
}
