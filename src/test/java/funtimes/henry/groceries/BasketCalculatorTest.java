package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Discount;
import funtimes.henry.groceries.data.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

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

}