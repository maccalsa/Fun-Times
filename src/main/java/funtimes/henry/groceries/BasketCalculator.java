package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Discount;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class BasketCalculator {

    private final List<Discount> discountList;

    public CheckoutBasket calculateBasket(ShoppingBasket basket) {
        CheckoutBasket checkoutBasket = new CheckoutBasket();

        // If basket is empty returning an empty checkout basket
        if (basket == null || basket.getShoppingBasketItems().isEmpty()) {
            return checkoutBasket;
        }

        // Expand Shopping basket to Checkout Basket
        basket.getShoppingBasketItems().entrySet().forEach(x->
                checkoutBasket.addCheckoutItem(x.getKey(), x.getValue())
        );

        return checkoutBasket;
    }
}
