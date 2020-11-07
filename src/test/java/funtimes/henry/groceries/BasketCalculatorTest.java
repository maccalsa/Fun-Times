package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Discount;
import funtimes.henry.groceries.data.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;

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
        BasketCalculator calculator = new BasketCalculator(discounts);

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(new ShoppingBasket());

        // then
        assertNotNull(checkoutBasket);
    }

    @Test
    public void calculate_empty_basket() {
        // given
        BasketCalculator calculator = new BasketCalculator(discounts);

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(new ShoppingBasket());

        // then
        assertNotNull(checkoutBasket);
    }

}