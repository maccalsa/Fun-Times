package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Discount;
import funtimes.henry.groceries.data.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static funtimes.henry.groceries.data.Product.SOUP;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ShoppingBasketTest {

    private static List<Product> products;
    private static List<Discount> discounts;

    @BeforeClass
    public static void beforeTests() {
        Database database =new Database();
        products = database.getProducts();
        discounts = database.getDiscounts();
    }

    @Test
    public void testAddToBasket() {
        // given
        ShoppingBasket basket = new ShoppingBasket();
        Product soup = products.get(SOUP);

        // when
        basket.addToBasket(soup, 2);
        // then
        assertEquals(Integer.valueOf(2), basket.getShoppingBasketItems().get(soup));

        // when
        basket.addToBasket(soup, 2);
        // then
        assertEquals(Integer.valueOf(4), basket.getShoppingBasketItems().get(soup));

    }

    @Test
    public void testCgotlear() {
        // given
        ShoppingBasket basket = new ShoppingBasket();
        Product soup = products.get(SOUP);
        basket.addToBasket(soup, 2);

        // when
        basket.clear();

        // then
        assertTrue(basket.getShoppingBasketItems().isEmpty());
    }

}