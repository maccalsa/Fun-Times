package testing.cucumber;


import funtimes.henry.groceries.basket.BasketCalculator;
import funtimes.henry.groceries.basket.CheckoutBasket;
import funtimes.henry.groceries.DateHolder;
import funtimes.henry.groceries.basket.ShoppingBasket;
import funtimes.henry.groceries.data.Database;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;

import static funtimes.henry.groceries.data.Product.APPLES;
import static funtimes.henry.groceries.data.Product.BREAD;
import static funtimes.henry.groceries.data.Product.MILK;
import static funtimes.henry.groceries.data.Product.SOUP;
import static junit.framework.TestCase.assertEquals;


public class StepDefinitions {

    private DateHolder dateHolder;
    private ShoppingBasket basket;
    private Database database;
    private CheckoutBasket checkoutBasket;
    private BasketCalculator basketCalculator;

    public StepDefinitions() {
        database = new Database();
        basketCalculator = new BasketCalculator(database.getDiscounts());
        basket = new ShoppingBasket();
        dateHolder = new DateHolder();
    }

    @Given("^the date is today$")
    public void the_date_is_today() throws Exception {
        dateHolder.setCurrentDate(LocalDate.now());
    }

    @Given("^the date is today plus (\\d+) days$")
    public void the_date_is_today_plus_days(int arg1) throws Exception {
        dateHolder.setCurrentDate(LocalDate.now().plusDays(arg1));
    }


    @Given("^the shopping basket is empty$")
    public void the_shopping_basket_is_empty() throws Exception {
        basket.clear();
    }

    @Given("^(\\d+) bottles of milk are added to basket$")
    public void bottles_of_milk_are_added_to_basket(int arg1) throws Exception {
        basket.addToBasket(database.getProducts().get(MILK), arg1);
    }

    @Given("^(\\d+) single apples are added to basket$")
    public void single_apples_are_added_to_basket(int arg1) throws Exception {
        basket.addToBasket(database.getProducts().get(APPLES), arg1);
    }

    @Given("^(\\d+) tins of soup are added to basket$")
    public void tins_of_soup_are_added_to_basket(int arg1) throws Exception {
        basket.addToBasket(database.getProducts().get(SOUP), arg1);
    }

    @Given("^(\\d+) loaves of bread are added to basket$")
    public void loaves_of_bread_are_added_to_basket(int arg1) throws Exception {
        basket.addToBasket(database.getProducts().get(BREAD), arg1);
    }

    @When("^when the basket is calculated$")
    public void when_the_basket_is_calculated() throws Exception {
        checkoutBasket = basketCalculator.calculateBasket(basket, dateHolder.getCurrentDate());
    }

    @Then("^the total cost is £([\\d\\.]+)$")
    public void the_total_cost_is_£(String value) throws Exception {
        assertEquals(value, checkoutBasket.getTotal().toString());
    }

}
