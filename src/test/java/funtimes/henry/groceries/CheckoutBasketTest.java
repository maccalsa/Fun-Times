package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static funtimes.henry.groceries.data.Product.SOUP;
import static junit.framework.TestCase.assertEquals;

public class CheckoutBasketTest {

    private static List<Product> products;

    @BeforeClass
    public static void beforeTests() {
        Database database = new Database();
        products = database.getProducts();
    }

    @Test
    public void addCheckoutItem() {
        // given
        CheckoutBasket basket = new CheckoutBasket();
        Product soup = products.get(SOUP);

        // when
        basket.addCheckoutItem(soup, 5);

        // then
        assertEquals(5, basket.getCheckoutItems().size());
    }
}