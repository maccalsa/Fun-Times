package funtimes.henry.groceries.basket;

import funtimes.henry.groceries.data.Product;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class ShoppingBasket {

    private Map<Product, Integer> shoppingBasketItems = new HashMap<>();

    public void addToBasket(Product product, final int amount) {
        Integer value = shoppingBasketItems.putIfAbsent(product, amount);
        Optional.ofNullable(value).ifPresent(x-> shoppingBasketItems.put(product, value + amount));
    }
    public void clear() {
        shoppingBasketItems.clear();
    }



}
