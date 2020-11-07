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

        if (discountList != null) {
            // Apply any discounts to the checkout basket
            for (Discount discount : discountList) {

                // Check that the shopping basket actually contained the trigger and target products for this discount,
                // if it does, does they quantity trigger this discount
                Integer triggerQuantityInBasket = basket.getShoppingBasketItems().get(discount.getTriggerProduct());
                Integer targetQuantityInBasket = basket.getShoppingBasketItems().get(discount.getTargetProduct());
                if (triggerQuantityInBasket != null && targetQuantityInBasket != null
                        && triggerQuantityInBasket >= discount.getTriggerQuantity()) {

                    // how many times can we apply the discount
                    int howManyTimes = triggerQuantityInBasket / discount.getTriggerQuantity();

                    List<CheckoutBasket.CheckoutItem> checkoutItem =
                            checkoutBasket.findItemsWithoutDiscount(discount.getTargetProduct());

                    for (int i = 0; i < howManyTimes; i++) {
                        checkoutItem.get(i).setPercentageDiscount(discount.getDiscountPercentage());
                    }
                }
            }
        }

        return checkoutBasket;
    }
}
