package funtimes.henry.groceries.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Builder
public class Product {

    private String name;
    private String unit;
    private BigDecimal cost;
}
