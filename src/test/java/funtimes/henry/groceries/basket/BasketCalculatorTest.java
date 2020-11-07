package funtimes.henry.groceries.basket;

import funtimes.henry.groceries.basket.BasketCalculator;
import funtimes.henry.groceries.basket.CheckoutBasket;
import funtimes.henry.groceries.basket.ShoppingBasket;
import funtimes.henry.groceries.data.Database;
import funtimes.henry.groceries.data.Discount;
import funtimes.henry.groceries.data.Product;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static funtimes.henry.groceries.data.Product.APPLES;
import static funtimes.henry.groceries.data.Product.BREAD;
import static funtimes.henry.groceries.data.Product.MILK;
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
        CheckoutBasket checkoutBasket = calculator.calculateBasket(new ShoppingBasket(), null);

        // then
        assertNotNull(checkoutBasket);
        assertTrue(checkoutBasket.getCheckoutItems().isEmpty());
    }

    @Test
    public void calculate_empty_basket() {
        // given
        BasketCalculator calculator = new BasketCalculator(Collections.emptyList());

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(new ShoppingBasket(), null);

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
        CheckoutBasket checkoutBasket = calculator.calculateBasket(shoppingBasket, null);

        // then
        assertNotNull(checkoutBasket);
        assertFalse(checkoutBasket.getCheckoutItems().isEmpty());
        assertEquals(2, checkoutBasket.getCheckoutItems().size());

    }

    @Test
    public void activeDiscounts() {
        LocalDateTime now = LocalDateTime.now();

        // Active from yesterday-1 to yesterday
        Discount discount1 = Discount.builder()
                .description("1")
                .validFrom(now.minusDays(2).toLocalDate())
                .validTo(now.minusDays(1).toLocalDate()).build();

        // Active from yesterday to today
        Discount discount2 = Discount.builder()
                .description("2")
                .validFrom(now.minusDays(1).toLocalDate())
                .validTo(now.toLocalDate()).build();

        // Active from today to tomorrow
        Discount discount3 = Discount.builder()
                .description("3")
                .validFrom(now.toLocalDate())
                .validTo(now.toLocalDate().plusDays(1)).build();

        // Active from tomorrow to tomorrow + 1
        Discount discount4 = Discount.builder()
                .description("4")
                .validFrom(now.toLocalDate().plusDays(1))
                .validTo(now.toLocalDate().plusDays(2)).build();

        BasketCalculator calculator =
                new BasketCalculator(Stream.of(discount1, discount2, discount3, discount4).collect(Collectors.toList()));


        // 3 days ago
        List<Discount> discounts = calculator.activeDiscounts(now.minusDays(3).toLocalDate());
        assertEquals(0, discounts.size());

        // 2 days ago
        discounts = calculator.activeDiscounts(now.minusDays(2).toLocalDate());
        assertEquals(1, discounts.size());
        assertEquals("1", discounts.get(0).getDescription());

        // yesterday
        discounts = calculator.activeDiscounts(now.minusDays(1).toLocalDate());
        assertEquals(2, discounts.size());
        assertEquals("1", discounts.get(0).getDescription());
        assertEquals("2", discounts.get(1).getDescription());

        // today
        discounts = calculator.activeDiscounts(now.toLocalDate());
        assertEquals(2, discounts.size());
        assertEquals("2", discounts.get(0).getDescription());
        assertEquals("3", discounts.get(1).getDescription());

        // tomorrow
        discounts = calculator.activeDiscounts(now.toLocalDate().plusDays(1));
        assertEquals(2, discounts.size());
        assertEquals("3", discounts.get(0).getDescription());
        assertEquals("4", discounts.get(1).getDescription());

        // tomorrow + 1
        discounts = calculator.activeDiscounts(now.toLocalDate().plusDays(2));
        assertEquals(1, discounts.size());
        assertEquals("4", discounts.get(0).getDescription());

        // tomorrow + 2
        discounts = calculator.activeDiscounts(now.toLocalDate().plusDays(3));
        assertEquals(0, discounts.size());
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
        CheckoutBasket checkoutBasket = calculator.calculateBasket(basket, LocalDate.now());

        // then
        assertNotNull(checkoutBasket);
        assertEquals(5, checkoutBasket.getCheckoutItems().size());
        assertEquals(BigDecimal.valueOf(3.15), checkoutBasket.getTotal());

    }


    /**
     * Price a basket containing: 6 apples and a bottle of milk, bought today, Expected total cost =
     * 1.90;
     */
    @Test
    public void calculate_basket_Test2() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Product apples = products.get(APPLES);
        Product milk = products.get(MILK);

        BasketCalculator calculator = new BasketCalculator(discounts);

        ShoppingBasket basket = new ShoppingBasket();
        basket.addToBasket(apples, 6);
        basket.addToBasket(milk, 1);

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(basket, now.toLocalDate());

        assertNotNull(checkoutBasket);
        assertEquals(7, checkoutBasket.getCheckoutItems().size());
        assertEquals(BigDecimal.valueOf(1.9).setScale(2), checkoutBasket.getTotal());

    }

    /**
     * Price a basket containing: 6 apples and a bottle of milk, bought in 5 days time, Expected total
     * cost = 1.84;
     */
    @Test
    public void calculate_basket_Test3() {
        // given
        LocalDateTime now = LocalDateTime.now().plusDays(5);
        Product apples = products.get(APPLES);
        Product milk = products.get(MILK);

        BasketCalculator calculator = new BasketCalculator(discounts);

        ShoppingBasket basket = new ShoppingBasket();
        basket.addToBasket(apples, 6);
        basket.addToBasket(milk, 1);

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(basket, now.toLocalDate());

        assertNotNull(checkoutBasket);
        assertEquals(7, checkoutBasket.getCheckoutItems().size());
        assertEquals(BigDecimal.valueOf(1.84), checkoutBasket.getTotal());

    }

    /**
     * Price a basket containing: 3 apples, 2 tins of soup and a loaf of bread, bought in 5 days time,
     * Expected total cost = 1.97.
     */
    @Test
    public void calculate_basket_Test4() {
        // given
        LocalDateTime now = LocalDateTime.now().plusDays(5);
        Product soup = products.get(SOUP);
        Product apples = products.get(APPLES);
        Product bread = products.get(BREAD);

        BasketCalculator calculator = new BasketCalculator(discounts);

        ShoppingBasket basket = new ShoppingBasket();
        basket.addToBasket(soup, 2);
        basket.addToBasket(apples, 3);
        basket.addToBasket(bread, 1);

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(basket, now.toLocalDate());

        assertNotNull(checkoutBasket);
        assertEquals(6, checkoutBasket.getCheckoutItems().size());
        assertEquals(BigDecimal.valueOf(1.97), checkoutBasket.getTotal());

    }


}