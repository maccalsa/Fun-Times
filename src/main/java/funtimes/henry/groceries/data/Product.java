package funtimes.henry.groceries.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
public class Product {

    public static final int SOUP = 0;
    public static final int BREAD = 1;
    public static final int MILK = 2;
    public static final int APPLES = 3;

    private String name;
    private String unit;
    private BigDecimal cost;
}
