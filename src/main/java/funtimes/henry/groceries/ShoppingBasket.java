package funtimes.henry.groceries;

import funtimes.henry.groceries.data.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingBasket {

    private List<ShoppingBasketItem> shoppingBasketItems = new ArrayList<>();

    @Data
    public static class ShoppingBasketItem {
        private Product product;
        private int quantity;
    }


}
