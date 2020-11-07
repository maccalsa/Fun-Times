package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Discount;
import funtimes.henry.groceries.data.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static funtimes.henry.groceries.data.Product.BREAD;
import static funtimes.henry.groceries.data.Product.SOUP;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class BasketCalculatorTest {

    private static List<Product> products;
    private static List<Discount> discounts;

    @BeforeClass
    public static void beforeTests() {
        Database database =new Database();
        products = database.getProducts();
        discounts = database.getDiscounts();
    }

    @Test
    public void calculate_null_basket() {
        // given
        BasketCalculator calculator = new BasketCalculator(Collections.emptyList());

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(new ShoppingBasket());

        // then
        assertNotNull(checkoutBasket);
        assertTrue(checkoutBasket.getCheckoutItems().isEmpty());
    }

    @Test
    public void calculate_empty_basket() {
        // given
        BasketCalculator calculator = new BasketCalculator(Collections.emptyList());

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(new ShoppingBasket());

        // then
        assertNotNull(checkoutBasket);
        assertTrue(checkoutBasket.getCheckoutItems().isEmpty());
    }

    @Test
    public void calculate_basket_one_item_no_discounts() {
        // given
        Product soup = products.get(SOUP);
        ShoppingBasket shoppingBasket = new ShoppingBasket();
        shoppingBasket.addToBasket(soup, 2);

        BasketCalculator calculator = new BasketCalculator(Collections.emptyList());

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(shoppingBasket);

        // then
        assertNotNull(checkoutBasket);
        assertFalse(checkoutBasket.getCheckoutItems().isEmpty());
        assertEquals(2, checkoutBasket.getCheckoutItems().size());

    }
    /**
     * Price a basket containing: 3 tins of soup and 2 loaves of bread, bought today,
     * Expected total cost = 3.15;
     */
    @Test
    public void calculate_basket_Test1() {
        // given
        Product soup = products.get(SOUP);
        Product bread = products.get(BREAD);

        BasketCalculator calculator = new BasketCalculator(discounts);

        ShoppingBasket basket = new ShoppingBasket();
        basket.addToBasket(soup, 3);
        basket.addToBasket(bread, 2);

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(basket);

        // then
        assertNotNull(checkoutBasket);
        assertEquals(5, checkoutBasket.getCheckoutItems().size());
        assertEquals(BigDecimal.valueOf(3.15), checkoutBasket.getTotal());

    }


}