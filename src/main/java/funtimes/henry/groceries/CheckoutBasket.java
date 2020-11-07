package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Product;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class CheckoutBasket {

    private List<CheckoutItem> checkoutItems = new ArrayList<>();

    /**
     *
     */
    @RequiredArgsConstructor
    @Data
    public static class CheckoutItem {
        private final Product product;
        private BigDecimal percentageDiscount = BigDecimal.ZERO;

        public BigDecimal getItemPrice() {
            BigDecimal cost = product.getCost();
            BigDecimal discount = percentageDiscount;
            return discount != BigDecimal.ZERO ? discount.multiply(cost).setScale(2) : cost;
        }
    }


    /**
     * Convert a Product a quantity to lines of count quantity of product
     * @param product
     * @param quantity
     */
    public void addCheckoutItem(Product product, Integer quantity) {
        IntStream.range(0, quantity).forEach(x-> checkoutItems.add(new CheckoutItem(product)));
    }

    /**
     *
     * @param triggerProduct
     * @return
     */
    public List<CheckoutItem> findItemsWithoutDiscount(Product triggerProduct) {
        return checkoutItems.stream()
                .filter(item -> item.getProduct().equals(triggerProduct)
                        && item.getPercentageDiscount() == BigDecimal.ZERO)
                .collect(Collectors.toList());
    }

    /**
     *
     * @return
     */
    public BigDecimal getTotal() {
        return checkoutItems.stream().map(CheckoutItem::getItemPrice)
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).setScale(2);

    }
}
