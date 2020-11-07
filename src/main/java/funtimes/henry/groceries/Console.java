package funtimes.henry.groceries;

import funtimes.henry.groceries.basket.BasketCalculator;
import funtimes.henry.groceries.basket.CheckoutBasket;
import funtimes.henry.groceries.basket.ShoppingBasket;
import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Product;


import java.math.BigDecimal;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Console {

    private Scanner scanner = new Scanner(System.in);

    private final Database database;
    private ShoppingBasket shoppingBasket;
    private BasketCalculator basketCalculator;

    private DateHolder dateHolder;

    public Console(Database database) {
        this.database = database;
        shoppingBasket = new ShoppingBasket();
        basketCalculator = new BasketCalculator(database.getDiscounts());
        dateHolder = new DateHolder();
    }

    public void run() {
        boolean exit = false;
        int selection = 0;

        do {
            printMenu();
            selection = getNumericSelection("Select from the menu:", 1, 6);
            switch (selection) {
                case 1:
                    displayBasket();
                    break;
                case 2:
                    addToBasket();
                    break;
                case 3:
                    changeDate();
                    break;
                case 4:
                    System.out.println("BASKET CLEARED");
                    System.out.println("--------------");
                    shoppingBasket.clear();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
            }
        } while (!exit);
    }

    /**
     *
     */
    private void printMenu() {

        System.out.printf("MAIN MENU.... The date is: %s\n", dateHolder.getDateString());
        System.out.println("---------");
        System.out.println("1) Display Basket");
        System.out.println("2) Add to Basket");
        System.out.println("3) Change Date");
        System.out.println("4) Clear Basket");
        System.out.println("5) Quit");
    }

    /**
     *
     * @param prompt
     * @param from
     * @param to
     * @return
     */
    private int getNumericSelection(String prompt, int from, int to) {
        while (true) {
            System.out.println(prompt);
            try {
                scanner = new Scanner(System.in);
                int selection = scanner.nextInt();
                if (selection >= from && selection <= to) return selection;
            } catch (Exception e) {

            }
        }
    }


    /**
     *
     */
    private void addToBasket() {
        System.out.println("ADD TO BASKET");
        System.out.println("---------");
        database.getProducts().forEach((product)->
                System.out.printf("%s) %-10s\t%-10s\t£%-10s\n",
                        database.getProducts().indexOf(product) + 1, product.getName(), product.getUnit(),
                        product.getCost()));

        int value = getNumericSelection("Select item :", 1, database.getProducts().size());
        int amount = getNumericSelection("Select quantity (1-10):", 1, 10);

        Product product = database.getProducts().get(value - 1);

        shoppingBasket.addToBasket(product, amount);
    }

    /**
     *
     */
    private void changeDate() {
        System.out.println("CHANGE DATE");
        System.out.println("-----------");

        while (true) {
            System.out.println("Enter date in format yyyy-mm-dd, or x to return");
            try {
                scanner = new Scanner(System.in);
                String value = scanner.next();
                if (value.equalsIgnoreCase("x")) {
                    return;
                }
                dateHolder.setCurrentDate(value);
                return;
            } catch (InputMismatchException | ParseException e) {
                // catch error and try again
            }
        }
    }

    /**
     *
     */
    private void displayBasket() {

        CheckoutBasket checkoutBasket = basketCalculator.calculateBasket(shoppingBasket, dateHolder.getCurrentDate());
        System.out.println("DISPLAY BASKET");
        System.out.println("--------------");
        checkoutBasket.getCheckoutItems().forEach(item -> {
            System.out.printf("%-10s\t%-10s\t%-10s\t%s\n", item.getProduct().getName(), item.getProduct().getUnit(),
                    item.getItemPrice(), (item.getPercentageDiscount() == BigDecimal.ZERO) ? "" :
                            "-" + BigDecimal.ONE.subtract(item.getPercentageDiscount())
                                    .multiply(BigDecimal.valueOf(100)) + "%");
        });
        System.out.printf("\t\t\t\t\t\t-----\n", checkoutBasket.getTotal());
        System.out.printf("\t\t\t\t\t\t£%s\n\n", checkoutBasket.getTotal());
    }
}
