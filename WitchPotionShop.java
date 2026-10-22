import java.util.ArrayList;
import java.util.Scanner;

public class WitchPotionShop {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Potion> potions = new ArrayList<>();
    static int gold = 200;// Starting gold for the shop
    static int totalSales = 0;

    public static void main(String[] args) {
        // Add some potions to the shop so it has something to sell
        potions.add(new Potion("Healing Elixir", 15, 10));
        potions.add(new Potion("Veritaserum", 30, 10));
        potions.add(new Potion("Elixir of Focus", 20, 15));

        boolean running = true;
        // Display welcome message
        
        System.out.println("-----------------------------------");
        System.out.println("Welcome to the Witch's Potion Shop!");
        System.out.println("-----------------------------------");

        // Main loop for the shop
        while (running) {
            System.out.println("\nGold: " + gold);// Display current gold
            System.out.println("\n1. View Potions");// Display for viewing potions
            System.out.println("2. Sell a Potion");// Display for selling potions
            System.out.println("3. View Shop Statistics");// Display for viewing statistics
            System.out.println("4. Exit");// Display for exiting the shop
            System.out.print("Choose an option: ");

            String choice = input.nextLine();
            // Handle user input and call appropriate methods
            switch (choice) {
                case "1" -> viewPotions();
                case "2" -> sellPotion();
                case "3" -> viewStatistics();
                case "4" -> {
                    running = false;
                    System.out.println("Thank you for visiting the Witch's Potion Shop!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
    }
    input.close();
}
// View available potions in the shop
public static void viewPotions() {
    System.out.println("\n--Available Potions: --");

    for (int i = 0; i < potions.size(); i++) {
        Potion potion = potions.get(i);

                    System.out.println((i + 1) + ". " + potion.name
                    + " - " + potion.price + " gold"
                    + " (Stock: " + potion.stock + ")");
    }

}
// Sell a potion to a customer
public static void sellPotion() {
    viewPotions();

    System.out.print("Enter the number of the potion to sell: ");
    String answer = input.nextLine();

    try {
        int number = Integer.parseInt(answer);

            if (number < 1 || number > potions.size()) {
                System.out.println("That potion does not exist.");
                return;
            }

            Potion selected = potions.get(number - 1);

            if (selected.stock <= 0) {
                System.out.println("That potion is out of stock.");
            } else {
                selected.stock--;
                gold += selected.price;
                totalSales++;

                System.out.println("You sold " + selected.name + "!");
                System.out.println("You earned " + selected.price + " gold.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
}
// View shop statistics
public static void viewStatistics() {
    System.out.println("\n--Shop Statistics: --");
    System.out.println("Gold earned and available: " + gold);
    System.out.println("Potions sold: " + totalSales);

    int totalPotions = 0;

    for (Potion potion : potions) {
        totalPotions += potion.stock;
    }

    System.out.println("Potions remaining: " + totalPotions);
}
}
// Potions so now I don't have error messages
class Potion {
    String name;
    int price;
    int stock;

    public Potion(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
}
