package funtimes.henry.groceries;

import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

public class BasketCalculatorTest {

    @Test
    public void calculate_empty_basket() {
        // given
        BasketCalculator calculator = new BasketCalculator();

        // when
        CheckoutBasket checkoutBasket = calculator.calculateBasket(null);

        // then
        assertNotNull(checkoutBasket);
    }

}