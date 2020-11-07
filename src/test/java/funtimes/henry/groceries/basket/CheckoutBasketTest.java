package funtimes.henry.groceries.basket;

import funtimes.henry.groceries.basket.CheckoutBasket;
import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Test
    public void findItemsWithoutDiscount() {
        // given
        CheckoutBasket basket = new CheckoutBasket();
        Product soup = products.get(SOUP);

        CheckoutBasket.CheckoutItem item1 = new CheckoutBasket.CheckoutItem(soup);
        item1.setPercentageDiscount(BigDecimal.ONE);

        CheckoutBasket.CheckoutItem item2 = new CheckoutBasket.CheckoutItem(soup);
        item2.setPercentageDiscount(BigDecimal.ZERO);

        CheckoutBasket.CheckoutItem item3 = new CheckoutBasket.CheckoutItem(soup);
        item3.setPercentageDiscount(BigDecimal.ZERO);

        CheckoutBasket.CheckoutItem item4 = new CheckoutBasket.CheckoutItem(soup);
        item4.setPercentageDiscount(BigDecimal.ONE);

        // when
        basket.setCheckoutItems(Stream.of(item1, item2, item3, item4).collect(Collectors.toList()));

        // then
        assertEquals(2,basket.findItemsWithoutDiscount(soup).size());
    }
}