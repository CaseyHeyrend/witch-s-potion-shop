import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;// Import necessary classes for managing the list of potions
import java.util.Scanner;// Import necessary classes for user input and managing the list of potions

// This is the main class for the Witch's Potion Shop application. It manages the shop's inventory, sales, and user interactions.
public class WitchPotionShop {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Potion> potions = new ArrayList<>();
    static ArrayList<Ingredient> ingredients = new ArrayList<>();// ingredients list without it will casue errors
    static int gold = 200;// Starting gold for the shop
    static int totalPotionSales = 0;
    static int totalIngredientSales = 0;

    public static void main(String[] args) {
        boolean inventoryLoaded = loadInventory();// Attempt to load inventory from a CSV file also check if there is extra }
        if (!inventoryLoaded) {
            // Add some potions to the shop so it has something to sell
            potions.add(new Potion("Healing Elixir", 15, 10));// Add a healing potion with price 15 and stock 10
            potions.add(new Potion("Veritaserum", 30, 10));// Add a truth serum potion with price 30 and stock 10
            potions.add(new Potion("Elixir of Focus", 20, 15));// Add a focus potion with price 20 and stock 15
        
            ingredients.add(new Ingredient("Mandrake Root", 5, 20));// Add an ingredient with price 5 and quantity 20
            ingredients.add(new Ingredient("Dittany", 10, 15));// Add an ingredient with price 10 and quantity 15
            ingredients.add(new Ingredient("Pixie Dust", 25, 10));// Add an ingredient with price 25 and quantity 10
        }

        boolean running = true;
        // Display welcome message
        
        System.out.println("-----------------------------------");
        System.out.println("Welcome to the Witch's Potion Shop!");
        System.out.println("-----------------------------------");

        // Main loop for the shop
        while (running) {
            System.out.println("\nGold: " + gold);// Display current gold
            System.out.println("\n1. View Inventory");// Display for viewing inventory which includes potions and ingredients
            System.out.println("2. Sell Product");// Display for selling a product
            System.out.println("3. Add Product");// Display for adding a product
            System.out.println("4. View Shop Statistics");// Display for viewing statistics
            System.out.println("5. Exit");// Display for exiting the shop
            System.out.print("Choose an option: ");

            String choice = input.nextLine();// Get user input for menu choice
            // Handle user input and call appropriate methods
            switch (choice) {
                case "1" -> viewInventory();
                case "2" -> sellProduct();
                case "3" -> addProduct();
                case "4" -> viewStatistics();
                case "5" -> {
                    saveInventory();
                    saveStatistics();
                    running = false;
                    System.out.println("Thank you for visiting the Witch's Potion Shop!");
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
    }
    input.close();

}
// View the shop's inventory, including potions and ingredients
public static void viewInventory() {
    viewPotions();
    viewIngredients();   
}
// View available potions in the shop
public static void viewPotions() {
    System.out.println("\n-- Available Potions: --");

    for (int i = 0; i < potions.size(); i++) {
        Potion potion = potions.get(i);

                    System.out.println((i + 1) + ". " + potion.name
                    + " - " + potion.price + " gold"
                    + " (Stock: " + potion.stock + ")");
    }

}
// View available ingredients in the shop
public static void viewIngredients() {
    System.out.println("\n-- Available Ingredients: --");

    for (int i = 0; i < ingredients.size(); i++) {
        Ingredient ingredient = ingredients.get(i);

                    System.out.println((i + 1) + ". " + ingredient.name
                    + " - " + ingredient.price + " gold"
                    + " (Quantity: " + ingredient.quantity + ")");
    }
}
// Sell a product (potion or ingredient) to a customer
public static void sellProduct() {
    System.out.println("\nWould you like to sell a potion or an ingredient?");
    System.out.println("1. Potion");
    System.out.println("2. Ingredient");
    System.out.print("Choose an option: ");

    String choice = input.nextLine();

    switch (choice) {
        case "1" -> sellPotion();
        case "2" -> sellIngredient();
        default -> System.out.println("Invalid option. Please try again.");
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
            } 
            System.out.println("How many would you like to sell? ");
            int amountToSell = Integer.parseInt(input.nextLine());

            if (amountToSell <= 0) {
            System.out.println("Please enter a positive quantity. ");
            return;
            }

            if (amountToSell > selected.stock) {
                System.out.println("Not enough stock available.");
                return;
            }
                selected.stock-= amountToSell;
                gold += selected.price * amountToSell;
                totalPotionSales+= amountToSell;

                System.out.println("You sold " + amountToSell + " " + selected.name + "!");
                System.out.println("You earned " + (selected.price * amountToSell) + " gold.");
            }

         catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
}
public static void sellIngredient() {
    viewIngredients();

    System.out.print("Enter the number of the ingredient to sell: ");
    String answer = input.nextLine();

    try {
        int number = Integer.parseInt(answer);

            if (number < 1 || number > ingredients.size()) {
                System.out.println("That ingredient does not exist.");
                return;
            }

            Ingredient selected = ingredients.get(number - 1);

            if (selected.quantity <= 0) {
                System.out.println("That ingredient is out of stock.");
            }
            System.out.println("How many would you like to sell? ");
            int amountToSell = Integer.parseInt(input.nextLine());

            if (amountToSell <= 0) {
            System.out.println("Please enter a positive quantity.");
            return;
            }

            if (amountToSell > selected.quantity) {
                System.out.println("Not enough stock available.");
                return;
            }
                selected.quantity-= amountToSell;
                gold += selected.price * amountToSell;
                totalIngredientSales++;

                System.out.println("You sold " + amountToSell + " " + selected.name + "!");
                System.out.println("You earned " + (selected.price * amountToSell) + " gold.");
            

        } catch (NumberFormatException e) {
            System.out.println("Please enter a number.");
        }
}
// Add a new potion or ingredient to the shop
public static void addProduct() {
    System.out.println("\n-- Add Product --");
    System.out.println("1. Potion");
    System.out.println("2. Ingredient");
    System.out.print("Choose an option: ");

    String choice = input.nextLine();

    if (!choice.equals("1") && !choice.equals("2")) {
        System.out.println("Invalid option.");
        return;
    }

    System.out.print("Enter product name: ");
    String name = input.nextLine().trim();

    if (name.isEmpty() || name.contains(",")) {
        System.out.println("Enter a valid name without commas.Try again.");
        return;
    }

    try {
        System.out.print("Enter product price: ");
        int price = Integer.parseInt(input.nextLine());

        if (price < 0) {
            System.out.println("Price cannot be negative.");
            return;
        }

        if (choice.equals("1")) {
            // Add potion using stock
            System.out.print("Enter potion stock: ");
            int stock = Integer.parseInt(input.nextLine());

            if (stock < 0) {
                System.out.println("Stock cannot be negative.");
                return;
            }

            potions.add(new Potion(name, price, stock));
            System.out.println("Potion added successfully!");

        } else {
            // Add ingredient using quantity
            System.out.print("Enter ingredient quantity: ");
            int quantity = Integer.parseInt(input.nextLine());

            if (quantity < 0) {
                System.out.println("Quantity cannot be negative.");
                return;
            }

            ingredients.add(new Ingredient(name, price, quantity));
            System.out.println("Ingredient added successfully!");
        }

    } catch (NumberFormatException e) {
        System.out.println("Please enter a valid number.");
    }
}
// View shop statistics
public static void viewStatistics() {
    System.out.println("\n-- Shop Statistics: --");
    System.out.println("Gold earned and available: " + gold);
    System.out.println("Potions sold: " + totalPotionSales);
    System.out.println("Ingredients sold: " + totalIngredientSales);

    int totalPotions = 0;
    int totalIngredients = 0;

    for (Potion potion : potions) {
        totalPotions += potion.stock;
    }

    for (Ingredient ingredient : ingredients) {
        totalIngredients += ingredient.quantity;
    }

    System.out.println("Potions remaining: " + totalPotions);
    System.out.println("Ingredients remaining: " + totalIngredients);
}

 // Stretch Challenge: Load shop inventory from a CSV file
public static boolean loadInventory() {
    try (BufferedReader reader =
            new BufferedReader(new FileReader("Inventory.csv"))) {

        String line = reader.readLine(); // Skip the header

        if (line == null) {
            System.out.println("Inventory file is empty.");
            return false;
        }

        potions.clear();
        ingredients.clear();

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",", -1);

            if (data.length != 4) {
                System.out.println("Invalid inventory row: " + line);
                return false;
            }

            String type = data[0];
            String name = data[1];
            int price = Integer.parseInt(data[2]);
            int amount = Integer.parseInt(data[3]);

            if (type.equals("Potion")) {
                potions.add(new Potion(name, price, amount));
            } else if (type.equals("Ingredient")) {
                ingredients.add(new Ingredient(name, price, amount));
            }
        }

        System.out.println("Inventory loaded successfully!");
        return true;

    } 
    catch (IOException | NumberFormatException e) {
        System.out.println("Could not load inventory: " + e.getMessage());
        return false;
    }

}

// Stretch Challenge: Save shop statistics to a file
public static void saveStatistics() {
    try (FileWriter writer = new FileWriter("Statistics.csv")) {
        writer.write("Gold earned and available: " + gold + "\n");
        writer.write("Potions sold: " + totalPotionSales + "\n");
        writer.write("Ingredients sold: " + totalIngredientSales + "\n");

        int totalPotions = 0;
        int totalIngredients = 0;

        for (Potion potion : potions) {
            totalPotions += potion.stock;
        }

        for (Ingredient ingredient : ingredients) {
            totalIngredients += ingredient.quantity;
        }

        writer.write("Potions remaining: " + totalPotions + "\n");
        writer.write("Ingredients remaining: " + totalIngredients + "\n");

        System.out.println("Shop statistics saved to Statistics.csv");
    } catch (IOException e) {
        System.out.println("An error occurred while saving the shop statistics.");
    }
}
// Stretch Challenge: Save shop inventory to a file
public static void saveInventory(){
    try (
        FileWriter writer = new FileWriter("Inventory.csv")) {
            writer.write("Type,Name,Price,Stock/Quantity\n");

            for (Potion potion : potions) {
                writer.write("Potion," + potion.name + "," + potion.price + "," + potion.stock + "\n");
            }
            for (Ingredient ingredient : ingredients) {
                writer.write("Ingredient," + ingredient.name + "," + ingredient.price + "," + ingredient.quantity + "\n");
            }
        System.out.println("Inventory saved to Inventory.csv");
    } catch (IOException e) {
        System.out.println("An error occurred while saving the inventory.");  
    }
}

}
// This is outside of the shop class so that it can separtely define the Potion class. 
// This is necessary because the Potion class is used in the WitchPotionShop class to represent the different.
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

class Ingredient {
    String name;
    int price;
    int quantity;

    public Ingredient(String name, int price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
}

